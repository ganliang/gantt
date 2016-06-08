package com.gfkd.imp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hslf.usermodel.HSLFAutoShape;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextBox;
import org.apache.poi.sl.usermodel.Line;
import org.apache.poi.sl.usermodel.PlaceableShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFConnectorShape;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.junit.Test;

/**
 * 幻灯片解析
 * 
 * @author ganliang 格式 HSLF ppt, XSLF pptx
 */
public class PowerPointUtil {

	@SuppressWarnings({ "unused", "rawtypes" })
	public static void parsePPTPowerPoint(InputStream in) {
		try {
			HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(in));
			for (HSLFSlide slide : ppt.getSlides()) {
				for (HSLFShape sh : slide.getShapes()) {
					String name = sh.getShapeName();

					java.awt.Rectangle anchor = sh.getAnchor();

					if (sh instanceof Line) {
						Line line = (Line) sh;
						System.out.println(line.toString());
					} else if (sh instanceof HSLFAutoShape) {
						HSLFAutoShape shape = (HSLFAutoShape) sh;
					} else if (sh instanceof HSLFTextBox) {
						HSLFTextBox shape = (HSLFTextBox) sh;
					} else if (sh instanceof HSLFPictureShape) {
						HSLFPictureShape shape = (HSLFPictureShape) sh;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public static void parsePPTXPowerPoint(InputStream in) {
		try {
			XMLSlideShow ppt = new XMLSlideShow(in);

			for (XSLFSlide slide : ppt.getSlides()) {
		        for (XSLFShape sh : slide.getShapes()) {
		            // name of the shape
		            String name = sh.getShapeName();
                    System.out.println(name);
                    
		            // shapes's anchor which defines the position of this shape in the slide
		            if (sh instanceof PlaceableShape) {
		                java.awt.geom.Rectangle2D anchor = ((PlaceableShape)sh).getAnchor();
		            }

		            if (sh instanceof XSLFConnectorShape) {
		                XSLFConnectorShape line = (XSLFConnectorShape) sh;
		                // work with Line
		            } else if (sh instanceof XSLFTextShape) {
		                XSLFTextShape shape = (XSLFTextShape) sh;
		                // work with a shape that can hold text
		            } else if (sh instanceof XSLFPictureShape) {
		                XSLFPictureShape shape = (XSLFPictureShape) sh;
		                // work with Picture
		            }
		        }
		    }
			
			ppt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void parsePPTPowerPoint() {
		try {
			FileInputStream in = new FileInputStream("c:/1.ppt");
			parsePPTPowerPoint();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void parsePPTXPowerPoint() {
		try {
			FileInputStream in = new FileInputStream("c:/1.pptx");
			parsePPTXPowerPoint(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
