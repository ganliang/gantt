package com.gfkd.imp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

/**
 * word解析
 * @author ganliang
 *格式 HWPF Word DOC;XWPF Word DOCX
 */
public class WordUtil {

	/**
	 * 使用HWPF解析doc格式的word文档
	 * @param in 文件流
	 * @return
	 */
	public static String parseDOCWord(InputStream in){
		String text=null;
		try {
			HWPFDocument document = new HWPFDocument(in);
            
			//一次性获取全部的段落
			WordExtractor extractor = new WordExtractor(document);
			text = extractor.getText();
			System.out.println(text);
			
			//一次次的获取paragraphs
			Range range = document.getRange();
			System.out.println(range.getStartOffset());
			System.out.println(range.getEndOffset());
			
			for(int i=0;i<range.numParagraphs();i++){
				Paragraph paragraph = range.getParagraph(i);
				String tparagraphText = paragraph.text();
				System.out.print(tparagraphText+" ");
			}
			extractor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * 使用HWPF解析docx格式的word文档
	 * @param in 文件流
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String parseDOCXWord(InputStream in){
		String text=null;
		try {
			XWPFDocument document = new XWPFDocument(in);
			
			XWPFWordExtractor extractor=new XWPFWordExtractor(document);
			
			text = extractor.getText();
			System.out.println(text);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	@Test
	public void parseDOCWord(){
		try {
			FileInputStream in=new FileInputStream("c:/1.doc");
			parseDOCWord(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void parseDOCXWord(){
		try {
			FileInputStream in=new FileInputStream("c:/gantt探索.docx");
			parseDOCXWord(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
