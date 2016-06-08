package com.gfkd.imp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

/**
 * zip 压缩 解压缩
 * 
 * @author ganliang
 *
 */
public class ZipUtil {

	/**
	 * 将多个文件压缩到一个zip文件
	 * 
	 * @param destFile
	 *            压缩文件目的地
	 * @param files
	 *            要压缩的文件
	 * @throws IOException
	 */
	public static void zip(String destFile, List<File> files) {
		ZipOutputStream zipOutputStream = null;
		try {
			zipOutputStream = new ZipOutputStream(new FileOutputStream(destFile));
			zipOutputStream.setComment("压缩文件");
			for (File file : files) {
				ZipEntry entry = new ZipEntry(file.getName());
				entry.setSize(file.length());
				// entry.setCreationTime(FileTime.fromMillis(System.currentTimeMillis()));
				// entry.setLastModifiedTime(FileTime.fromMillis(file.lastModified()));
				entry.setTime(System.currentTimeMillis());
				zipOutputStream.putNextEntry(entry);

				FileInputStream in = new FileInputStream(file);
				byte[] b = new byte[in.available()];
				in.read(b);
				in.close();

				zipOutputStream.write(b);
				zipOutputStream.closeEntry();
			}
		} catch (FileNotFoundException e) {
			try {
				zipOutputStream.closeEntry();
				zipOutputStream.close();
				new File(destFile).deleteOnExit();// 删除压缩文件
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zipOutputStream.flush();
				zipOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将一个路径下的所有文件都进行压缩
	 * 
	 * @param dest
	 *            目的路径
	 * @param source
	 *            源目标路径
	 * @throws FileNotFoundException
	 */
	public static void zip(String dest, String source) {
		if (source == null) {
			throw new IllegalArgumentException("源路径不能为空!");
		}
		File sourceFile = new File(source);
		if (!sourceFile.exists()) {
			throw new IllegalArgumentException("源路径不匹配!");
		}
		ZipOutputStream zipOutputStream = null;
		try {
			zipOutputStream = new ZipOutputStream(new FileOutputStream(dest));
			iteratorZip(sourceFile, zipOutputStream, sourceFile.getAbsolutePath());

		} catch (FileNotFoundException e) {
			try {
				zipOutputStream.closeEntry();
				zipOutputStream.close();
				new File(dest).deleteOnExit();// 删除压缩文件
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
		} finally {
			try {
				zipOutputStream.flush();
				zipOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 迭代压缩目录
	 * 
	 * @param file
	 * @param zipOutputStream
	 * @param sourceFile
	 * @throws IOException
	 */
	public static void iteratorZip(File file, ZipOutputStream zipOutputStream, String sourceFile) throws IOException {

		zipEntry(file, zipOutputStream, sourceFile);

		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if (listFiles != null && listFiles.length > 0) {// 过滤空目录
				for (File f : listFiles) {
					iteratorZip(f, zipOutputStream, sourceFile);
				}
			}
		}
	}

	/**
	 * 将一个文件添加到压缩文件中
	 * 
	 * @param file
	 * @param zipOutputStream
	 * @param sourceFile
	 * @throws IOException
	 */
	public static void zipEntry(File file, ZipOutputStream zipOutputStream, String sourceFile) throws IOException {
		ZipEntry zipEntry = null;
		String absolutePath = file.getAbsolutePath();
		String fileName = absolutePath.replace(sourceFile, "");
		if (fileName == null || fileName.length() < 1) {
			return;
		}
		fileName = fileName.substring(1, fileName.length());
		System.out.println(fileName);
		// 如果压缩的是文件
		if (file.isDirectory()) {
			zipEntry = new ZipEntry(fileName + "/");
			zipOutputStream.putNextEntry(zipEntry);
		} else {
			zipEntry = new ZipEntry(fileName);
			zipEntry.setSize(file.length());
			zipEntry.setTime(System.currentTimeMillis());

			zipOutputStream.putNextEntry(zipEntry);

			// 将文件内容写入到压缩文件
			FileInputStream inputStream = new FileInputStream(file);
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			inputStream.close();
			zipOutputStream.write(b);
		}

		zipOutputStream.closeEntry();
	}

	@Test
	public void testZip() {
		String destFile = "c:/1.zip";
		List<File> files = new ArrayList<File>();
		files.add(new File("C:/1.png"));
		files.add(new File("C:/1.pdf"));
		files.add(new File("C:/gantt.png"));
		files.add(new File("C:/1.doc"));
		files.add(new File("C:/gantt探索.docx"));
		files.add(new File("C:/workbook.xls"));
		files.add(new File("C:/workbook.xlsx"));
		zip(destFile, files);
	}

	@Test
	public void testZip2() {
		zip("c:/zip.zip", "C:/Program Files/Git");
	}

	/**
	 * 解压缩
	 * 
	 * @param zip
	 *            压缩文件
	 * @param destFile
	 *            解压缩路径
	 */
	public static void unzip(String zip, String destFile) {
		File zipFile = new File(zip);
		if (!zipFile.exists()) {
			throw new IllegalArgumentException("解压缩文件不存在!");
		}

		try {
			ZipInputStream inputStream = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry zipEntry = null;
			ZipFile ziFile = new ZipFile(zipFile);
			while ((zipEntry = inputStream.getNextEntry()) != null) {
				String name = zipEntry.getName();
				name = name.replace("\\", "/");
				System.out.println("解压缩  " + name);
				
				//压缩的是目录
				if (name.endsWith("/")) {
					File file = new File(destFile,name);
					file.mkdirs();
				} else {
					InputStream in = ziFile.getInputStream(zipEntry);
					byte[] b = new byte[in.available()];
					in.read(b);
					in.close();

					File file = new File(destFile,name);
					if (!file.exists()) {
						file.createNewFile();
					}
					
					FileOutputStream outputStream = new FileOutputStream(new File(destFile, name));
					outputStream.write(b);
					outputStream.flush();
					outputStream.close();
				}
			}

			inputStream.close();
			ziFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUnzip() {
		unzip("c:/zip.zip", "c:/git");
	}
}
