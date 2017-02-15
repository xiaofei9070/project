package com.text.utils.video;

import java.io.IOException;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Speak {
	 public static void main(String[] args) throws IOException {  
         
	        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");  
	        
	        Dispatch sapo = sap.getObject();  
	        try {  
	  
	            // 音量 0-100  
	            sap.setProperty("Volume", new Variant(100));  
	            // 语音朗读速度 -10 到 +10  
	            sap.setProperty("Rate", new Variant(2));  
	  
	  
	            // 执行朗读  
	            Dispatch.call(sapo, "Speak", new Variant("1 2 3 4 5 6"));  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            sapo.safeRelease();  
	            sap.safeRelease();  
	        }  
	        }  
}
