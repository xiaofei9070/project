package com.text.utils.security;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestMain {
	private String publicKey;
	private String privateKey;
	@Before
	public void setUp() throws Exception{
		Map<String, Object> keyMap = RSACoder.initKey();
		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
	}
	
	@org.junit.Test
	public void test() throws Exception{
		//公钥加密  私钥解密
		String inputStr = "啊大大大";
		byte[] data = inputStr.getBytes();
		byte[] encrypyByPublic = RSACoder.encrypyByPublic(data, publicKey);
		byte[] decrptyByPrivateKey = RSACoder.decrptyByPrivateKey(encrypyByPublic, privateKey);
		System.out.println(new String(decrptyByPrivateKey));
	}
	
	@Test
	public void pTest() throws Exception{
		//私钥加密  公钥解密 
		String inputStr = "ada";
		byte[] data = inputStr.getBytes();
		byte[] encrptyByPrivate = RSACoder.encrptyByPrivate(data, privateKey);
		byte[] decrptyByPublicKey = RSACoder.decrptyByPublicKey(encrptyByPrivate, publicKey);
		System.out.println(new String(decrptyByPublicKey));
		
		// 签名
		String sign = RSACoder.sign(encrptyByPrivate, privateKey);
		System.out.println(sign);
		
		boolean verify = RSACoder.verify(encrptyByPrivate, publicKey, sign);
		System.out.println(verify);
		
	}
}
