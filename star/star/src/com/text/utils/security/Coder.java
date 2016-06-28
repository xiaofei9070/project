package com.text.utils.security;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

/**
 * 单向加密
 * @author liuxiaofei
 * @date 2016年5月25日
 * @version V1.0
 */
public class Coder {
	
	/**
	 * 解密
	 */
	@SuppressWarnings("restriction")
	public static byte[] decoderBase64(String str) throws Exception{
		return new BASE64Decoder().decodeBuffer(str);
	}
	/**
	 * 加密
	 * @date 2016年6月3日 上午11:01:55
	 * @param b
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static String encryptBase64(byte[] b) throws Exception{
		return new BASE64Encoder().encodeBuffer(b);
	}
	
	/**
	 * md5加密
	 * @date 2016年5月25日 下午4:51:54
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encryptMd5(String str) throws Exception{
		MessageDigest mdg = MessageDigest.getInstance("MD5");
		mdg.update(str.getBytes());
		return new BigInteger(mdg.digest()).toString(16);
	}
	
	/**
	 * sha 加密
	 * @date 2016年5月25日 下午5:09:52
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encryptSha(String str) throws Exception{
		MessageDigest mdg = MessageDigest.getInstance("SHA");
		mdg.update(str.getBytes());
		return new BigInteger(mdg.digest()).toString(32);
	}
	
	/**
	 * 初始化 HMAC 密钥
	 * @date 2016年5月25日 下午5:12:50
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] initMacKey() throws NoSuchAlgorithmException{
		KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
		SecretKey sec = kg.generateKey();
		return sec.getEncoded();
	}
	/**
	 * HMAC 加密
	 * @date 2016年5月25日 下午5:16:52
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static byte[] encryptHMac(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException{
		SecretKey sk = new SecretKeySpec(initMacKey(), "HmacMD5");
		Mac mac = Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		return mac.doFinal(data);
	}
	
	public static void main(String[] args) throws Exception {
		String data = encryptBase64("刘亦菲".getBytes());
		System.out.println(new String(decoderBase64(data)));
	}
}
