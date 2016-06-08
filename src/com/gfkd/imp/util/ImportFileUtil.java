package com.gfkd.imp.util;

import java.io.IOException;
import java.io.InputStream;

public class ImportFileUtil {

	/**
	 * 文件导入
	 * @param fileName文件名称
	 * @param in文件的字节流
	 * @throws IOException
	 */
	public Object importFile(String fileName, InputStream in) throws IOException {
		if (fileName == null || "".equals(fileName)) {
			throw new IllegalArgumentException("文件类型不能为空");
		}
		if (in == null) {
			throw new IllegalArgumentException("文件内容不能为空");
		}
		Object parseContent = null;
		String fileType = null;
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf != -1) {
			fileType = fileName.substring(lastIndexOf + 1, fileName.length());
		}
		if (fileType != null) {
			switch (fileType) {
			// xls 格式的Excel
			case "xls":
				parseContent = ExcelUtil.parseXLSExcel(in);
				break;
			// xlsx 格式的Excel
			case "xlsx":
				parseContent=ExcelUtil.parseXLSXExcel(in);
				break;
			// doc 格式的Word
			case "doc":
				parseContent=WordUtil.parseDOCWord(in);
				break;
			// docx 格式的Word
			case "docx":
				parseContent=WordUtil.parseDOCXWord(in);
				break;
			//xml 格式解析
			case "xml":
				break;
			// txt 文档解析
			case "txt":
				parseContent=parseTxt(in);
				break;
			//解析pdf文件
			case "pdf":
				parseContent=new PdfUtil().parsePDF(in);
				break;
			default:
				parseContent="不支持该格式的文件解析";
				break;
			}
		}else{
			parseContent="不支持该格式的文件解析";
		}
		return parseContent;
	}

	/**
	 * 解析文本内容
	 * @param in 文本字节流
	 * @return
	 * @throws IOException
	 */
	private String parseTxt(InputStream in) throws IOException {
		byte[] b = new byte[in.available()];
		in.read(b);
		return new String(b);
	}
}
