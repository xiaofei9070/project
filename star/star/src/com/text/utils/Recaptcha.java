package com.text.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Recaptcha {
	
	private static final Font font = new Font("Times New Roman",Font.PLAIN,12);
	
	private static String[] sign = {"*","÷","+","-"}; 
	
	public static void main(String[] args) throws Exception{
		Random random = new Random();
		BufferedImage image = new BufferedImage(100, 20, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(font);
		g.setColor(Color.white);
		g.fillRect(0, 0, 100, 20);
		g.setColor(Color.RED);
		g.drawString("123456", 10, 16);
		g.setColor(Color.GRAY);
		g.drawLine(100, 2, 10, 15);
		g.dispose();
		File file = new File("E:\\e.jpg");
		FileInputStream is = new FileInputStream(file);
		FileOutputStream os = new FileOutputStream(new File("E:\\d.jpg"));
		byte[] b = new byte[8];
		int i = -1;
		while ((i = is.read(b)) != -1) {
			os.write(b);
		}
		os.close();
		is.close();
		
		/*ImageIO.write(image, "JPEG", os);*/
		ScriptEngine engineByName = new ScriptEngineManager().getEngineByName("JavaScript");
		System.out.println(System.currentTimeMillis());
		System.out.println(engineByName.eval("5%5"));;
		System.out.println(System.currentTimeMillis());
	}
	
}
