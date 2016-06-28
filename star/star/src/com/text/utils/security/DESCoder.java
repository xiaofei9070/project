package com.text.utils.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class DESCoder extends Coder{
	
	public static final String ALGORITHM = "DES";
	
	private static Key toKey(byte[] key) throws Exception{
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey sk = keyFactory.generateSecret(dks);
		return sk;
	}
	/**
	 * 加密
	 * @date 2016年5月27日 下午4:27:10
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,String key) throws Exception{
		Key k = toKey(decoderBase64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	/**
	 * 解密
	 * @date 2016年5月27日 下午4:27:04
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrpty(byte[] data,String key) throws Exception{
		Key k = toKey(decoderBase64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	/**
	 * 初始化密钥
	 * @date 2016年6月3日 上午11:13:56
	 * @return
	 * @throws Exception
	 */
	public static String initKey() throws Exception{
		return initKey(null);
	}
	/**
	 * 初始化密钥
	 * @date 2016年6月3日 上午11:15:45
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception{
		SecureRandom sRandom = null;
		if(seed != null){
			sRandom = new SecureRandom(decoderBase64(seed));
		}else{
			sRandom = new SecureRandom();
		}
		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(sRandom);
		return encryptBase64(kg.generateKey().getEncoded());
	}
	
	public static void main(String[] args) throws Exception {
		String key = initKey(encryptBase64("123456aaa1312313".getBytes()));
		String data = "刘亦菲ddddddddddd";
		byte[] b = data.getBytes();
		System.out.print(key);
		data = encryptBase64(encrypt(b, key));
		System.out.print(data);
		System.out.print(new String(decrpty(decoderBase64(data), key)));
	}
	
	
}
