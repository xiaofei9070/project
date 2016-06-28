package com.text.utils.security;

import java.io.UnsupportedEncodingException;

/**
 * 双向加密 并解密
 * @author Administrator
 *
 */
public class Base64Utils {
	
	static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="  
	            .toCharArray();  
    static private byte[] codes = new byte[256];  
    static {  
        for (int i = 0; i < 256; i++)  
            codes[i] = -1;  
        for (int i = 'A'; i <= 'Z'; i++)  
            codes[i] = (byte) (i - 'A');  
        for (int i = 'a'; i <= 'z'; i++)  
            codes[i] = (byte) (26 + i - 'a');  
        for (int i = '0'; i <= '9'; i++)  
            codes[i] = (byte) (52 + i - '0');  
        codes['+'] = 64;  
        codes['/'] = 63;  
    } 
	/** 
     * 加密
	 * @throws UnsupportedEncodingException 
     */  
    public static String encode(byte[] data) throws UnsupportedEncodingException {
        char[] out = new char[((data.length + 2) / 3) * 4];  
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {  
            boolean quad = false;  
            boolean trip = false;  
            int val = (0xFF & (int) data[i]);  
            val <<= 8;  
            if ((i + 1) < data.length) {  
                val |= (0xFF & (int) data[i + 1]);  
                trip = true;  
            }  
            val <<= 8;  
            if ((i + 2) < data.length) {  
                val |= (0xFF & (int) data[i + 2]);  
                quad = true;  
            }  
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];  
            val >>= 6;  
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];  
            val >>= 6;  
            out[index + 1] = alphabet[val & 0x3F];  
            val >>= 6;  
            out[index + 0] = alphabet[val & 0x3F];  
        }  
        return new String(out); 
    }  
    /** 
     * 解密
     * @throws UnsupportedEncodingException 
     */  
    public static byte[] decode(String str) throws UnsupportedEncodingException { 
    	char[] data = str.toCharArray();
        int len = ((data.length + 3) / 4) * 3;  
        if (data.length > 0 && data[data.length - 1] == '=')  
            --len;  
        if (data.length > 1 && data[data.length - 2] == '=')  
            --len;  
        byte[] out = new byte[len];  
        int shift = 0;  
        int accum = 0;  
        int index = 0;  
        for (int ix = 0; ix < data.length; ix++) {  
            int value = codes[data[ix] & 0xFF];  
            if (value >= 0) {  
                accum <<= 6;  
                shift += 6;  
                accum |= value;  
                if (shift >= 8) {  
                    shift -= 8;  
                    out[index++] = (byte) ((accum >> shift) & 0xff);  
                }  
            }  
        }  
        if (index != out.length)  
            throw new Error("miscalculated data length!");  
        
        return out;  
    }  
    
    
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws UnsupportedEncodingException {
    	 // 加密成base64  
        String strSrc = "达到";  
        String strOut = Base64Utils.encode(strSrc.getBytes("utf-8"));  
        System.out.println(strOut);  
        byte[] strOut2 = Base64Utils.decode(strOut);  
        System.out.println(new String(strOut2,"utf-8")); 
        try {
			System.out.println(new String(new sun.misc.BASE64Decoder().decodeBuffer(strOut)));
			System.out.println((byte) '+');
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
