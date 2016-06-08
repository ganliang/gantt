package com.gfkd.imp.util;

import java.awt.geom.Rectangle2D;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.Test;

/**
 * 解析padf文件
 * 
 * @author ganliang
 *
 */
public class PdfUtil {

	/**
	 * 解析pdf文件
	 * 
	 * @param in
	 *            文件流
	 */
	public Object parsePDF(InputStream in) {
		String parseContent = "";
		try {
			System.out.println("PDF属性..................................");
			PDDocument document = PDDocument.load(in);
			printMetadata(document);

			System.out.println("\nPDF标签..................................");
			PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
			if (outline != null) {
				printBookmark(outline, "");
			} else {
				System.out.println("This document does not contain any bookmarks");
			}

			// System.out.println("\nPDF超链接..................................");
			// printURLS(document);

			System.out.println("\nPDF内容.................................");
			printContent(document);

			// System.out.println("\nPDF字段.................................");
			// printFields(document);

			// System.out.println("\nPDF打印.................................");
			// print(document);

			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parseContent;
	}

	/**
	 * print the documents data
	 * 
	 * @param document
	 * @throws IOException
	 */
	public void printMetadata(PDDocument document) throws IOException {
		PDDocumentInformation info = document.getDocumentInformation();
		PDDocumentCatalog cat = document.getDocumentCatalog();
		PDMetadata metadata = cat.getMetadata();
		System.out.println("Page Count=" + document.getNumberOfPages());
		System.out.println("Title=" + info.getTitle());
		System.out.println("Author=" + info.getAuthor());
		System.out.println("Subject=" + info.getSubject());
		System.out.println("Keywords=" + info.getKeywords());
		System.out.println("Creator=" + info.getCreator());
		System.out.println("Producer=" + info.getProducer());
		System.out.println("Creation Date=" + formatDate(info.getCreationDate()));
		System.out.println("Modification Date=" + formatDate(info.getModificationDate()));
		System.out.println("Trapped=" + info.getTrapped());
		if (metadata != null) {
			String string = new String(metadata.toByteArray(), "ISO-8859-1");
			System.out.println("Metadata=" + string);
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日历日期
	 * @return
	 */
	private String formatDate(Calendar date) {
		String retval = null;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			retval = formatter.format(date.getTime());
		}
		return retval;
	}

	/**
	 * 打印pdf的书签
	 * 
	 * @param bookmark
	 * @param indentation
	 * @throws IOException
	 */
	public void printBookmark(PDOutlineNode bookmark, String indentation) throws IOException {
		PDOutlineItem current = bookmark.getFirstChild();
		while (current != null) {
			System.out.println(indentation + current.getTitle());
			printBookmark(current, indentation + "    ");
			current = current.getNextSibling();
		}
	}

	/**
	 * 打印超链接
	 * 
	 * @param doc
	 * @throws IOException
	 */
	public void printURLS(PDDocument doc) throws IOException {
		int pageNum = 0;
		for (PDPage page : doc.getPages()) {

			pageNum++;
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			List<PDAnnotation> annotations = page.getAnnotations();
			// first setup text extraction regions
			for (int j = 0; j < annotations.size(); j++) {
				PDAnnotation annot = annotations.get(j);
				if (annot instanceof PDAnnotationLink) {
					PDAnnotationLink link = (PDAnnotationLink) annot;
					PDRectangle rect = link.getRectangle();
					// need to reposition link rectangle to match text space
					float x = rect.getLowerLeftX();
					float y = rect.getUpperRightY();
					float width = rect.getWidth();
					float height = rect.getHeight();
					int rotation = page.getRotation();
					if (rotation == 0) {
						PDRectangle pageSize = page.getMediaBox();
						y = pageSize.getHeight() - y;
					} else if (rotation == 90) {
						// do nothing
					}

					Rectangle2D.Float awtRect = new Rectangle2D.Float(x, y, width, height);
					stripper.addRegion("" + j, awtRect);
				}
			}

			stripper.extractRegions(page);

			for (int j = 0; j < annotations.size(); j++) {
				PDAnnotation annot = annotations.get(j);
				if (annot instanceof PDAnnotationLink) {
					PDAnnotationLink link = (PDAnnotationLink) annot;
					PDAction action = link.getAction();
					String urlText = stripper.getTextForRegion("" + j);
					if (action instanceof PDActionURI) {
						PDActionURI uri = (PDActionURI) action;
						System.out.println("Page " + pageNum + ":'" + urlText + "'=" + uri.getURI());
					}
				}
			}
		}
	}

	/**
	 * 输出文件内容
	 * @param document
	 * @return 
	 * @throws IOException
	 */
	private String printContent(PDDocument document) throws IOException {
		PDPageTree pages = document.getPages();
		for (PDPage pdPage : pages) {
			InputStream contents = pdPage.getContents();
			if (contents != null) {
				byte[] b = new byte[contents.available()];
				contents.read(b);
				contents.close();
				// System.out.println(new String(b));
			}
		}

		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(document);
		System.out.println(text);

		return text;
	}

	/**
	 * 输出pdf字段
	 * 
	 * @param pdfDocument
	 * @throws IOException
	 */
	public void printFields(PDDocument pdfDocument) throws IOException {
		PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		List<PDField> fields = acroForm.getFields();

		System.out.println(fields.size() + " top-level fields were found on the form");

		for (PDField field : fields) {
			processField(field, "|--", field.getPartialName());
		}
	}

	private void processField(PDField field, String sLevel, String sParent) throws IOException {
		String partialName = field.getPartialName();

		if (field instanceof PDNonTerminalField) {
			if (!sParent.equals(field.getPartialName())) {
				if (partialName != null) {
					sParent = sParent + "." + partialName;
				}
			}
			System.out.println(sLevel + sParent);

			for (PDField child : ((PDNonTerminalField) field).getChildren()) {
				processField(child, "|  " + sLevel, sParent);
			}
		} else {
			String fieldValue = field.getValueAsString();
			StringBuilder outputString = new StringBuilder(sLevel);
			outputString.append(sParent);
			if (partialName != null) {
				outputString.append(".").append(partialName);
			}
			outputString.append(" = ").append(fieldValue);
			outputString.append(",  type=").append(field.getClass().getName());
			System.out.println(outputString);
		}
	}

	/**
	 * 打印pdf文档
	 * @param document
	 * @throws IOException
	 * @throws PrinterException
	 */
	public void print(PDDocument document) {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(document));
			job.print();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testParsePDF() {
		try {
			// FileInputStream in = new FileInputStream("c:/Received.pdf");
			FileInputStream in = new FileInputStream("c:/Ajax+lucene0802.pdf");
			parsePDF(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
