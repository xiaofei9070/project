package com.text.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class BaiduCurlUtil {
	
	public static String post(String url,String[] params){
		PrintWriter out = null;
		BufferedReader in = null;
		String result="";  
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.setRequestProperty("Host","data.zz.baidu.com");  
            conn.setRequestProperty("User-Agent", "curl/7.12.1");  
            conn.setRequestProperty("Content-Length", "83");  
            conn.setRequestProperty("Content-Type", "text/plain"); 
            
            conn.setDoInput(true);
            conn.setDoOutput(true);
            
            out = new PrintWriter(conn.getOutputStream());
            String param = "";
            for(String s:params){
            	param += s + "\n";
            }
            out.print(param.trim());
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line=in.readLine()) != null){
            	result += line;
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{  
                if(out != null){  
                    out.close();  
                }  
                if(in!= null){  
                    in.close();  
                }  
                   
            }catch(IOException ex){  
                ex.printStackTrace();  
            }
		}
		return result;
	}
	
	public static void main(String[] args) {
		String url = " http://data.zz.baidu.com/urls?site=m.facesteel.com&token=SaOzd8YnYC2jhGHa";
		String[] params = {"http://m.facesteel.com/js","http://m.facesteel.com/shopPC/mall"};
		System.out.println(post(url, params));
	}
	
}
