package com.text.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP {
	
	/**
	 * 压缩
	 * @date 2016年8月10日 下午2:52:00
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String compress(String str) throws UnsupportedEncodingException, IOException{
		if(null == str || str.length() <= 0)return null;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		
		gzip.write(str.getBytes("utf-8"));
		gzip.close();
		return out.toString("ISO-8859-1");
	}
	
	/**
	 * 解压
	 * @date 2016年8月10日 下午2:59:05
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String unCompress(String str) throws IOException{
		if(null == str || str.length() <= 0)return null;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		ByteArrayInputStream input = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
		
		GZIPInputStream gzip = new GZIPInputStream(input);
		
		byte[] buff = new byte[256];
		int n = 0;
		while((n = gzip.read(buff)) >= 0){
			out.write(buff, 0, n);
		}
		gzip.close();
		return out.toString("utf-8");
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		String com = compress("规格gzipgzip过多  大大大大");
		System.out.println(com.length());
		System.out.println(unCompress(com));
	}
	
}
