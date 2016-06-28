package com.star.test;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class SecurityTest {
	
	
	public static void main(String[] args) throws Exception{
		KeyGenerator kGen = KeyGenerator.getInstance("AES");
		kGen.init(128,new SecureRandom("md5".getBytes("utf-8")));
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kGen.generateKey().getEncoded(), "AES"));
		
		System.out.println(new String(cipher.doFinal("md5".getBytes())));
		
	}
	
}
