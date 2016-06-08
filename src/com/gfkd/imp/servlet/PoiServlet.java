package com.gfkd.imp.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gfkd.imp.util.ImportFileUtil;
import com.google.gson.Gson;

@WebServlet(urlPatterns = { "/poiServlet" })
public class PoiServlet extends HttpServlet {

	private static final long serialVersionUID = -7624588518411658264L;

	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uploadFileName="";
		Object responseBody="";
		String json="";
		Gson gson=new Gson();
		FileItemFactory fileItemFactory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);

		try {
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				//上传文件的普通字段
                if(fileItem.isFormField()){
                	String fieldName = fileItem.getFieldName();
                	if("uploadFileName".equalsIgnoreCase(fieldName)){
                		uploadFileName=fileItem.getString(uploadFileName);
                		System.out.println(uploadFileName);
                	}
                }else{
                	String fileName = fileItem.getName();
                	InputStream inputStream = fileItem.getInputStream();
                	responseBody = new ImportFileUtil().importFile(fileName, inputStream);
                	inputStream.close();
                	
                	if(responseBody instanceof String){
                		responseBody = responseBody.toString().replace("\n\r", "");
                		responseBody = responseBody.toString().replace("\r", "");
                		responseBody = responseBody.toString().replace("\n", "");
            			json = "{'success':true,'msg':'解析结果【"+responseBody+"】'}";
            		}else if(responseBody instanceof List){
            			json = "{'success':true,'msg':'解析结果【"+gson.toJson(responseBody)+"】'}";
            		}else{
            			json = "{'success':true}";
            		}
                }
			}
		} catch (FileUploadException e) {
			json = "{'success':false}";
			e.printStackTrace();
		}
		
		System.out.println(json);
		response.getWriter().print(json);
	}
}
