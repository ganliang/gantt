package com.gfkd.imp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 解析xml
 * @author ganliang
 *
 */
public class XmlUtil {

	/**
	 * 解析xml
	 * @param in
	 * @throws DocumentException 
	 */
	@SuppressWarnings("rawtypes")
	public static void parseXML(InputStream in){
		try {
			SAXReader reader=new SAXReader();
			Document document = reader.read(in);
			//获取根节点
			Element rootElement = document.getRootElement();
			Map map=null;
			iteratorXML(rootElement,map);
			
			String asXML = document.asXML();
			System.out.println(asXML);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 递归遍历xml
	 * @param element 节点
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public static void iteratorXML(Element element,Map map){
		Map currentMap=null;
		//元素的名称
		String elementName = element.getName();
		//根节点录入
		if(map==null){
			map=new HashMap();
			map.put(elementName, elementName);
			//录入元素的属性
			List<Attribute> attributes = element.attributes();
			for (Attribute attr : attributes) {
				String attrName = attr.getName();
				String attrValue = attr.getValue();
				map.put(attrName, attrValue);
			} 
			currentMap=map;
		}else{
			Object object = map.get(elementName);
			//如果该元素名称没有被加入到map中 则加入
			if(object==null){
				
			}else{
				
			}
		}
		
		List<Element> elements = element.elements();
		if(elements.size()>0){
			for (Element el : elements) {
				iteratorXML(el, map);
			}
		}else{
			String textTrim = element.getTextTrim();
			System.out.println(textTrim);
		}
	}
	
	@Test
	public void parseXML(){
		try {
			FileInputStream in = new FileInputStream("c:/web.xml");
			parseXML(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
