package com.text.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipEncrypt {
	
	private static void directoryZip(ZipOutputStream out,File f,String base) throws Exception{
		if(f.isDirectory()){
			File[] f1 = f.listFiles();
			for(int i = 0; i < f1.length; i++){
				directoryZip(out, f1[i], base + "/" + f1[i].getName());
			}
		}else{
			if(base.equals("")){
				base = f.getName();
			}
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] b = new byte[2048];
			int a = 0;
			while((a = in.read(b)) != -1){
				out.write(b,0,a);
			}
			in.close();
		}
	}
	
	public static void zip(String path,String zipFile){
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
			directoryZip(zos,new File(path),"");
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void unZipFile(ZipInputStream zis,File file) throws Exception{
		ZipEntry zip = zis.getNextEntry();
		if(zip == null)return;
		String name = zip.getName();
		File f = new File(file.getAbsolutePath() + "/" + name);
		if(zip.isDirectory()){
			f.mkdirs();
			unZipFile(zis, file);
		}else{
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[2048];
			int a = 0;
			while((a = zis.read(b)) != -1){
				fos.write(b, 0, a);
			}
			fos.close();
			unZipFile(zis, file);
		}
	}
	
	public static void unZip(String directory,String zipFile){
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			File f = new File(directory);
			if(!f.exists())f.mkdirs();
			unZipFile(zis, f);
			zis.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		zip("G:\\upload_dir\\upload\\facesteel\\20160707", "G:\\upload_dir\\upload\\facesteel\\1.zip");
		unZip("G:\\upload_dir\\upload\\facesteel\\20160713Dir", "G:\\upload_dir\\upload\\facesteel\\1.zip");
	}

}
