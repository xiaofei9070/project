package com.text.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.text.utils.security.Coder;

/**
 * 文件 工具类 
 * 方便 操作 .txt .json .html .jsp 等想关文本文件
 * 主要用了bufferedReader OutputStreamWriter
 * 编码 utf-8
 * @author liuxiaofei
 * @date 2016年5月14日
 * @version V1.0
 */
public class FileOpUtil {
	
	/**
	 * 文件写入 write filename
	 * @date 2016年5月14日 下午12:55:00
	 * @param fileName 文件路径 + 文件名称
	 * @param content 写入内容
	 */
	public static boolean writeInFile(String fileName,String content){
		boolean flag = false;
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(fileName);
			OutputStreamWriter writer = new OutputStreamWriter(outStream, "utf-8");
			writer.write(content);
			writer.close();
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			close(outStream);
		}
		return flag;
	}
	/**
	 * 往文件中追加内容
	 * @date 2016年5月14日 下午2:34:22
	 * @param fileName 文件路径 + 文件名
	 * @param content 内容
	 * @return 追加成功 true
	 */
	public static boolean writeAppend(String fileName,String content){
		boolean flag = false;
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(fileName, true);
			OutputStreamWriter outWriter = new OutputStreamWriter(outStream,"utf-8");
			outWriter.write(content);
			outWriter.close();
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			close(outStream);
		}
		
		return flag;
	}
	
	/**
	 * 读取文件 read fileName
	 * @date 2016年5月14日 下午1:08:25
	 * @param fileName 文件路径 + 文件名称
	 * @return 文件内容
	 */
	public static StringBuffer readOutFile(String fileName){
		BufferedReader reader = null;
		StringBuffer buff = new StringBuffer();
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(fileName), "utf-8");
			reader = new BufferedReader(in);
			String str;
			while((str = reader.readLine()) != null){
				buff.append(str + "\n");
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(reader);
		}
		return buff;
	}
	
	/**
	 * 删除文件
	 * @date 2016年5月14日 下午1:14:05
	 * @param fileName 文件路径 + 文件名称
	 * @return 删除成功（true） 失败（false）
	 */
	public static boolean delFile(String fileName){
		File file = new File(fileName);
		if(file.delete()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 设置是否可写
	 * @date 2016年5月14日 下午2:58:13
	 * @param fileName 文件路径 + 文件名
	 * @param writeOnly 【true(可写) false(不可写)】
	 * @return boolean true（可写） false (不可写)
	 */
	public static boolean yesOrNoWrite(String fileName,boolean writeOnly){
		File file = new File(fileName);
		if(writeOnly){
			file.setWritable(true);
		}else{
			file.setWritable(false);
		}
		return file.canWrite();
	}
	/**
	 * 设置只读
	 * @date 2016年5月14日 下午3:17:30
	 * @param fileName 文件路径 + 文件名
	 * @return boolean 
	 */
	public static boolean readOnly(String fileName){
		File file = new File(fileName);
		file.setReadOnly();
		return file.canWrite();
	}
	
	/**
	 * 文件复制
	 * @date 2016年5月14日 下午2:22:17
	 * @param fileName 原文件
	 * @param newFile 新文件地址
	 * @return 复制成功 ture
	 */
	public static boolean copyFile(String fileName,String newFile){
		InputStream is = null;
		OutputStream os = null;
		boolean flag = false;
		try {
			is = new FileInputStream(new File(fileName));
			os = new FileOutputStream(new File(newFile));
			byte[] b = new byte[1024];
			int i = -1;
			while((i = is.read(b)) != -1){
				os.write(b,0,i);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			close(os);
			close(is);
		}
		return flag;
	}
	
	/**
	 * 文件转化为 base64加密
	 * @date 2016年6月17日 下午1:27:19
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception{
		FileInputStream fis = null;
		byte[] b = null;
		try {
			File file = new File(path);
			fis = new FileInputStream(file);
			b = new byte[(int) file.length()];
			fis.read(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fis);
		}
		return Coder.encryptBase64(b);
	}
	
	/**
	 * 解密 并保存 文件
	 * @date 2016年6月17日 下午1:40:50
	 * @param base64
	 * @param targetFile
	 */
	public static void decptryBase64File(String base64,String targetFile){
		FileOutputStream fos = null;
		try {
			File file = new File(targetFile);
			fos = new FileOutputStream(file);
			byte[] b = Coder.decoderBase64(base64);
			fos.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fos);
		}
	}
	
	/**
	 * 将加密文件 直接保存至文件
	 * @date 2016年6月17日 下午1:43:26
	 * @param base64
	 * @param targetFile
	 */
	public static void toFile(String base64, String targetFile){
		FileOutputStream fos = null;
		try {
			File file = new File(targetFile);
			fos = new FileOutputStream(file);
			byte[] b = base64.getBytes();
			fos.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fos);
		}
	}
	
	/**
	 * 压缩
	 * @date 2016年6月17日 下午2:19:52
	 * @param targetFile
	 * @param fileName
	 */
	public static void doZip(String targetFile,String[] fileName){
		ZipOutputStream zos = null;
		byte[] buffer = new byte[1024];
		try {
			zos = new ZipOutputStream(new FileOutputStream(targetFile));
			File parent = new File(fileName[0]);
			if(!parent.isDirectory()){
				for(String file : fileName){
					File f = new File(file);
					FileInputStream fis = new FileInputStream(f);
					zos.putNextEntry(new ZipEntry(f.getName()));
					int len;
					while((len = fis.read(buffer)) != -1){
						zos.write(buffer,0,len);
					}
					zos.closeEntry();
					fis.close();
				}
			}else{
				File[] listFiles = parent.listFiles();
				for(File file : listFiles){
					FileInputStream fis = new FileInputStream(file);
					zos.putNextEntry(new ZipEntry(file.getName()));
					int len;
					while((len = fis.read(buffer)) != -1){
						zos.write(buffer, 0, len);
					}
					zos.closeEntry();
					fis.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(zos);
		}
		
	}
	
	
	/**
	 * 关闭流
	 * @date 2016年5月25日 上午11:07:27
	 * @param ca
	 */
	private static void close(Closeable ca){
		try {
			if(ca != null){
				ca.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		String str = encodeBase64File("D:\\Documents\\Downloads\\280\\280.jpg");
		decptryBase64File(str, "D:\\Documents\\Downloads\\280\\281.jpg");
		String[] s = {"D:\\Documents\\Downloads\\280"};
		doZip("D:\\Documents\\Downloads\\280.zip", s);
	}
}
