package com.gfkd.imp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**
 * Excel解析
 * @author ganliang
 * 格式 HSSF Excel XLS;XSSF Excel XLSX
 *
 */
public class ExcelUtil {

	/**
	 * 解析xls格式的Excel
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public static List<List<Object>> parseXLSExcel(InputStream in) throws IOException{
		List<List<Object>> data=new ArrayList<List<Object>>();
		if(in==null){
			throw new IllegalArgumentException();
		}
		//创建一个工作簿
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		Iterator<Sheet> sheetIterator = workbook.iterator();
		//遍历excel多个sheet
		while(sheetIterator.hasNext()){
			Sheet sheet = sheetIterator.next();
			Iterator<Row> rowIterator = sheet.iterator();
			//遍历一个sheet多行
			while(rowIterator.hasNext()){
				List<Object> list=new ArrayList<Object>();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				//遍历一行多列
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					int cellType = cell.getCellType();
					switch (cellType) {
					case Cell.CELL_TYPE_BOOLEAN:
						list.add(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						list.add(cell.getNumericCellValue());
						break;
					default:
						list.add(cell.getStringCellValue());
						break;
					}
				}
				data.add(list);
			}
		}
		workbook.close();
		return data;
	}
	
	/**
	 * 解析XLSX格式的Excel
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public static List<List<Object>> parseXLSXExcel(InputStream in) throws IOException{
		List<List<Object>> data=new ArrayList<List<Object>>();
		if(in==null){
			throw new IllegalArgumentException();
		}
		//创建一个xlsx工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(in);
		Iterator<Sheet> sheetIterator = workbook.iterator();
		//遍历excel多个sheet
		while(sheetIterator.hasNext()){
			Sheet sheet = sheetIterator.next();
			Iterator<Row> rowIterator = sheet.iterator();
			//遍历一个sheet多行
			while(rowIterator.hasNext()){
				List<Object> list=new ArrayList<Object>();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				//遍历一行多列
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					int cellType = cell.getCellType();
					switch (cellType) {
					case Cell.CELL_TYPE_BOOLEAN:
						list.add(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						list.add(cell.getNumericCellValue());
						break;
					default:
						list.add(cell.getStringCellValue());
						break;
					}
				}
				data.add(list);
			}
		}
		workbook.close();
		return data;
	}
	
	/**
	 * 生成xls格式的excel
	 * @param fileDest 写入excel的文件位置
	 * @param sheetName 写入excel表格的名称
	 * @param metadata 写入excel的标题头
	 * @param data 写入excel的数据
	 * @throws IOException
	 */
	public static void generateXLSExcel(String fileDest,String sheetName,List<String> metadata,List<List<Object>> data) throws IOException{
		if(fileDest==null){
			throw new IllegalArgumentException("保存excel文件不能为空！");
		}
		if(metadata==null||metadata.size()==0||data==null||data.size()==0){
			throw new IllegalArgumentException("保存excel文件不能为空！");
		}
		Workbook wb = new HSSFWorkbook();
		
		CreationHelper creationHelper = wb.getCreationHelper();
		//设置excel的样式
		CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd h:mm:ss"));
	    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
	    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    //设置字体的样式
	    Font font = wb.createFont();
	    font.setFontHeightInPoints((short)12);
	    font.setFontName("Courier New");
	    font.setItalic(true);
	    font.setStrikeout(true);
	    //cellStyle.setFont(font);
	    
	    //分页表格
	    int pageSize=1;
	    int begin=0;
	    int end=100;
	    if(data.size()%100==0){
	    	pageSize=data.size()/100;
	    }else{
	    	pageSize=data.size()/100+1;
	    }
	    for (int index = 0; index < pageSize; index++) {
	    	begin=index*100;
	    	//最后一个sheet
	    	if(pageSize-index==1){
	    		end=data.size();
	    	}else{
	    		end=(index+1)*100;
	    	}
	    	Sheet sheet = wb.createSheet(sheetName+index);
		    //设置标题头的样式
		    Header header = sheet.getHeader();
		    header.setCenter("Center Header");
		    header.setLeft("Left Header");
		    header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
		                    HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
		    //添加表头
		    Row headerRow = sheet.createRow(0);
		    for (int i = 0; i < metadata.size(); i++) {
		    	Cell createCell = headerRow.createCell(i);
		    	createCell.setCellValue(metadata.get(i));
		    	createCell.setCellStyle(cellStyle);
			}
	        //添加表格数据
		    int rowIndex=0;
	        for (int i = begin; i < end; i++) {
	        	//创建一行
	        	Row row = sheet.createRow(++rowIndex);
	        	List<Object> list=data.get(i);
	        	for (int j = 0; j < list.size(); j++) {
	        		//创建一列
	        		Cell cell = row.createCell(j);
	        		//cell.setCellStyle(cellStyle);
	        		Object obj = list.get(j);
	        		if(obj instanceof Boolean){
	        			cell.setCellValue((Boolean)obj);
	        		}else if(obj instanceof Date){
	        			cell.setCellValue((Date)obj);
	        		}else if(obj instanceof Double){
	        			cell.setCellValue((Double)obj);
	        		}else{
	        			cell.setCellValue(obj.toString());
	        		}
				}
			}
	        sheet.setDefaultColumnWidth(25);
		}
	    
        
	    FileOutputStream fileOut = new FileOutputStream(fileDest);
	    wb.write(fileOut);
	    fileOut.flush();
	    fileOut.close();
	    wb.close();
	}
	/**
	 * 生成xlsx格式的excel
	 * @param fileDest 写入excel的文件位置
	 * @param sheetName 写入excel表格的名称
	 * @param metadata 写入excel的标题头
	 * @param data 写入excel的数据
	 * @throws IOException
	 */
	public static void generateXLSXExcel(String fileDest,String sheetName,List<String> metadata,List<List<Object>> data) throws IOException{
		if(fileDest==null){
			throw new IllegalArgumentException("保存excel文件不能为空！");
		}
		if(metadata==null||metadata.size()==0||data==null||data.size()==0){
			throw new IllegalArgumentException("保存excel文件不能为空！");
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		
		CreationHelper creationHelper = wb.getCreationHelper();
		//设置excel的样式
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd h:mm:ss"));
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//设置字体的样式
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12);
		font.setFontName("Courier New");
		font.setItalic(true);
		font.setStrikeout(true);
		//cellStyle.setFont(font);
		
		//分页表格
		int pageSize=1;
		int begin=0;
		int end=100;
		if(data.size()%100==0){
			pageSize=data.size()/100;
		}else{
			pageSize=data.size()/100+1;
		}
		for (int index = 0; index < pageSize; index++) {
			begin=index*100;
			//最后一个sheet
			if(pageSize-index==1){
				end=data.size();
			}else{
				end=(index+1)*100;
			}
			Sheet sheet = wb.createSheet(sheetName+index);
			//设置标题头的样式
			Header header = sheet.getHeader();
			header.setCenter("Center Header");
			header.setLeft("Left Header");
			header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
					HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
			//添加表头
			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < metadata.size(); i++) {
				Cell createCell = headerRow.createCell(i);
				createCell.setCellValue(metadata.get(i));
				createCell.setCellStyle(cellStyle);
			}
			//添加表格数据
			int rowIndex=0;
			for (int i = begin; i < end; i++) {
				//创建一行
				Row row = sheet.createRow(++rowIndex);
				List<Object> list=data.get(i);
				for (int j = 0; j < list.size(); j++) {
					//创建一列
					Cell cell = row.createCell(j);
					//cell.setCellStyle(cellStyle);
					Object obj = list.get(j);
					if(obj instanceof Boolean){
						cell.setCellValue((Boolean)obj);
					}else if(obj instanceof Date){
						cell.setCellValue((Date)obj);
					}else if(obj instanceof Double){
						cell.setCellValue((Double)obj);
					}else{
						cell.setCellValue(obj.toString());
					}
				}
			}
			sheet.setDefaultColumnWidth(25);
		}
		
		
		FileOutputStream fileOut = new FileOutputStream(fileDest);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		wb.close();
	}
	
	@Test
	public void generateXLSExcel(){
		try {
			List<List<Object>> data=new ArrayList<List<Object>>();
			for (int i = 0; i < 1021; i++) {
				List<Object> list=new ArrayList<Object>();
				list.add("姓名"+i);
				list.add("性别"+(i%2==0?"男":"女"));
				list.add("年龄"+Integer.parseInt("23"+i));
				list.add("居住地"+i*121);
				list.add("身份证41150319900615141"+i);	
				list.add(new Date());	
				data.add(list);
			}
			List<String> metadata=new ArrayList<String>();
			metadata.add("姓名");
			metadata.add("性别");
			metadata.add("年龄");
			metadata.add("居住地");
			metadata.add("身份证");	
			metadata.add("生日");	
			
			generateXLSExcel("c:/workbook.xls", "User", metadata, data);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generateXLSXExcel(){
		try {
			List<List<Object>> data=new ArrayList<List<Object>>();
			for (int i = 0; i < 1021; i++) {
				List<Object> list=new ArrayList<Object>();
				list.add("姓名"+i);
				list.add("性别"+(i%2==0?"男":"女"));
				list.add("年龄"+Integer.parseInt("23"+i));
				list.add("居住地"+i*121);
				list.add("身份证41150319900615141"+i);	
				list.add(new Date());	
				data.add(list);
			}
			List<String> metadata=new ArrayList<String>();
			metadata.add("姓名");
			metadata.add("性别");
			metadata.add("年龄");
			metadata.add("居住地");
			metadata.add("身份证");	
			metadata.add("生日");	
			
			generateXLSXExcel("c:/workbook.xlsx", "User", metadata, data);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void parseXLSExcel(){
		try {
			FileInputStream in = new FileInputStream("c:/workbook.xls");
			List<List<Object>> parseExcel = parseXLSExcel(in);
			for (List<Object> list : parseExcel) {
				for (Object object : list) {
					System.out.print(object+"  ");
				}
				System.out.println();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void parseXLSXExcel(){
		try {
			FileInputStream in = new FileInputStream("c:/workbook.xlsx");
			List<List<Object>> parseExcel = parseXLSXExcel(in);
			for (List<Object> list : parseExcel) {
				for (Object object : list) {
					System.out.print(object+"  ");
				}
				System.out.println();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
