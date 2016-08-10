package com.text.utils;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUntil {
	
	/**
	 * 指定大小 缩放 按照比例
	 * @date 2016年7月20日 下午2:35:51
	 * @param srcFile
	 * @param toFile
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void size(String srcFile,String toFile,int width,int height) throws IOException{
		Thumbnails.of(srcFile).size(width, height).toFile(toFile);
	}
	
	/**
	 * 指定比例缩放 
	 * @date 2016年7月20日 下午2:36:02
	 * @param srcFile
	 * @param toFile
	 * @param f
	 * @throws IOException
	 */
	public static void scale(String srcFile,String toFile,float f) throws IOException{
		Thumbnails.of(srcFile).scale(f).toFile(toFile);
	}
	
	/**
	 * 指定大小不按照比例缩放
	 * @date 2016年7月20日 下午2:37:57
	 * @param srcFile
	 * @param toFile
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void sizeFalse(String srcFile,String toFile,int width,int height) throws IOException{
		Thumbnails.of(srcFile).size(width, height).keepAspectRatio(false).toFile(toFile);
	}
	
	/**
	 * 旋转 并按比例缩放
	 * @date 2016年7月20日 下午2:39:47
	 * @param srcFile
	 * @param toFile
	 * @param width
	 * @param height
	 * @param rotate
	 * @throws IOException
	 */
	public static void rotate(String srcFile,String toFile,int width,int height,int rotate) throws IOException{
		Thumbnails.of(srcFile).size(width, height).rotate(rotate).toFile(toFile);
	}
	
	/**
	 * 旋转
	 * @date 2016年7月20日 下午2:45:34
	 * @param srcFile
	 * @param toFile
	 * @param rotate
	 * @throws IOException
	 */
	public static void rotate(String srcFile,String toFile,int rotate) throws IOException{
		Thumbnails.of(srcFile).scale(1f).rotate(rotate).toFile(toFile);
	}
	
	/**
	 * 水印 按比例缩放
	 * watermark(位置 , 水印图, 透明度)
	 * @date 2016年7月20日 下午2:46:45
	 * @param srcFile
	 * @param toFile
	 * @param width
	 * @param height
	 * @param rotate
	 * @throws IOException 
	 */
	public static void watermark(String srcFile,String toFile,int width,int height,Positions position,String watermarkImage,float filter) throws IOException{
		Thumbnails.of(srcFile).size(width, height)
								.watermark(position, ImageIO.read(new File(watermarkImage)), filter)
								.outputQuality(0.8f)
								.toFile(toFile);
	}
	
	/**
	 * 水印
	 * watermark(位置 , 水印图, 透明度)
	 * @date 2016年7月20日 下午2:46:45
	 * @param srcFile
	 * @param toFile
	 * @param width
	 * @param height
	 * @param rotate
	 * @throws IOException 
	 */
	public static void watermark(String srcFile,String toFile,Positions position,String watermarkImage,float filter) throws IOException{
		Thumbnails.of(srcFile).scale(1f)
								.watermark(position, ImageIO.read(new File(watermarkImage)), filter)
								.outputQuality(0.8f)
								.toFile(toFile);
	}
	
	/**
	 * 在指定位置裁剪
	 * @date 2016年7月20日 下午3:20:49
	 * @param srcFile
	 * @param toFile
	 * @param position
	 * @param regWidth
	 * @param regHeight
	 * @throws IOException
	 */
	public static void sourceRegion(String srcFile,String toFile,Positions position,int regWidth,int regHeight) throws IOException{
		Thumbnails.of(srcFile).sourceRegion(position,regWidth,regHeight)
								.size(regWidth,regHeight)
								.keepAspectRatio(false)
								.toFile(toFile);
	}
	
	/**
	 * 按坐标 裁剪
	 * @date 2016年7月20日 下午3:21:07
	 * @param srcFile
	 * @param toFile
	 * @param position
	 * @param regWidth
	 * @param regHeight
	 * @throws IOException
	 */
	public static void sourceRegion(String srcFile,String toFile,int x,int y,int width,int height) throws IOException{
		Thumbnails.of(srcFile).sourceRegion(x,y,width,height)
								.size(width,height) 
								.keepAspectRatio(false)
								.toFile(toFile);
	}
	
	/**
	 * 修改 图片格式 并等比例缩放
	 * @date 2016年7月20日 下午3:44:21
	 * @param srcFile
	 * @param toFile
	 * @param type
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void outputFormat(String srcFile,String toFile,String type,int width,int height) throws IOException{
		Thumbnails.of(srcFile).size(width, height).outputFormat(type).toFile(toFile);
	}
	
	/**
	 * 修改 图片格式
	 * @date 2016年7月20日 下午3:45:02
	 * @param srcFile
	 * @param toFile
	 * @param type
	 * @throws IOException
	 */
	public static void outputFormat(String srcFile,String toFile,String type) throws IOException{
		Thumbnails.of(srcFile).scale(1f).outputFormat(type).toFile(toFile);
	}
	
	/**
	 * 转为流对象
	 * @date 2016年7月20日 下午3:50:50
	 * @param srcFile
	 * @param toFile
	 * @return
	 * @throws IOException
	 */
	public static OutputStream toOutputStream(String srcFile,String toFile) throws IOException{
		OutputStream os = new FileOutputStream(toFile);
		Thumbnails.of(srcFile).scale(1f).toOutputStream(os);
		return os;
	}
	
	
	public static boolean validateImage(File file){
		if(!file.exists())return false;
		Image img = null;
		try {
			img = ImageIO.read(file);
			if(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0){
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally{
			img = null;
		}
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(System.currentTimeMillis());
		size("G:\\upload_dir\\upload\\facesteel\\20160713Dir\\007f968a345d4fcd8e5e031b033d323ckcV8Yx.jpg", "G:\\upload_dir\\upload\\facesteel\\20160713Dir\\1.jpg", 1000, 1000);
		System.out.println(System.currentTimeMillis());
		
		System.out.println(System.currentTimeMillis());
		scale("G:\\upload_dir\\upload\\facesteel\\20160713Dir\\007f968a345d4fcd8e5e031b033d323ckcV8Yx.jpg", "G:\\upload_dir\\upload\\facesteel\\20160713Dir\\1.jpg", 1.25f);
		System.out.println(System.currentTimeMillis());

		System.out.println(System.currentTimeMillis());
		rotate("G:\\upload_dir\\upload\\facesteel\\20160713Dir\\007f968a345d4fcd8e5e031b033d323ckcV8Yx.jpg", "G:\\upload_dir\\upload\\facesteel\\20160713Dir\\1.jpg",360);
		System.out.println(System.currentTimeMillis());
		
		System.out.println(System.currentTimeMillis());
		watermark("G:\\upload_dir\\upload\\facesteel\\20160713Dir\\007f968a345d4fcd8e5e031b033d323ckcV8Yx.jpg",
				"G:\\upload_dir\\upload\\facesteel\\20160713Dir\\1.jpg", Positions.BOTTOM_CENTER, "G:\\upload_dir\\upload\\facesteel\\20160713Dir\\AppNew.png", 0.8f);
		System.out.println(System.currentTimeMillis());
		
		System.out.println(System.currentTimeMillis());
		sourceRegion("G:\\upload_dir\\upload\\facesteel\\20160713Dir\\007f968a345d4fcd8e5e031b033d323ckcV8Yx.jpg",
				"G:\\upload_dir\\upload\\facesteel\\20160713Dir\\1.jpg", 0,0,200,200);
		System.out.println(System.currentTimeMillis());
		
		System.out.println(System.currentTimeMillis());
		outputFormat("G:\\upload_dir\\upload\\facesteel\\20160713Dir\\007f968a345d4fcd8e5e031b033d323ckcV8Yx.jpg",
				"G:\\upload_dir\\upload\\facesteel\\20160713Dir\\1.png","png");
		System.out.println(System.currentTimeMillis());
		System.out.println(validateImage(new File("G:\\upload_dir\\upload\\facesteel\\商合供应链资源表7-18.xlsx")));
	}
}
