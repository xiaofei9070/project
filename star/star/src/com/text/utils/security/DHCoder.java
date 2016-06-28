package com.text.utils.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/***
 * DH 安全组件
 * @author liuxiaofei
 * @date 2016年6月8日
 * @version V1.0
 */
public class DHCoder extends Coder{
	public static final String ALGORIHTM = "DH";
	
	private static final int KEY_SIZE = 1024;
	
	public static final String SECRET_ALGORITHM = "DES";
	
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";
	
	/**
	 * 初始化 服务端密钥
	 * @date 2016年6月8日 上午10:36:28
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> initKey() throws Exception{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORIHTM);
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair= keyPairGenerator.generateKeyPair();
		
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
		Map<String,Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PRIVATE_KEY, privateKey);
		keyMap.put(PUBLIC_KEY, publicKey);
		return keyMap;
	}
	/**
	 * 初始化 客户端密钥
	 * @date 2016年6月8日 上午10:50:02
	 * @param key 服务端公钥
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> initKey(String key) throws Exception{
		//解析 服务端公钥
		byte[] keyBytes = decoderBase64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORIHTM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		DHParameterSpec dhParamSpec = ((DHPublicKey)pubKey).getParams();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
		keyPairGenerator.initialize(dhParamSpec);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
		
		Map<String,Object> keyMap = new HashMap<String,Object>();
		keyMap.put(PRIVATE_KEY, privateKey);
		keyMap.put(PUBLIC_KEY, publicKey);
		return keyMap;
	}
	/**
	 * 加密
	 * @date 2016年6月8日 上午10:51:42
	 * @param data
	 * @param publicKey 服务端公钥
	 * @param privateKey 客户端私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrpty(byte[] data,String publicKey,String privateKey) throws Exception{
		SecretKey secretKey = getSecretKey(publicKey, privateKey);
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * @date 2016年6月8日 上午11:04:42
	 * @param data
	 * @param publicKey 客户端 公钥
	 * @param privateKey 客户端 私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrpty(byte[] data,String publicKey,String privateKey) throws Exception{
		SecretKey secretKey = getSecretKey(publicKey, privateKey);
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 搭建密钥 
	 * @date 2016年6月8日 上午11:00:03
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	private static SecretKey getSecretKey(String publicKey,String privateKey) throws Exception{
		byte[] pubKeyBytes = decoderBase64(publicKey);
		
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORIHTM);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKeyBytes);
		PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
		
		byte[] priKeyBytes = decoderBase64(privateKey);
		
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
		
		return keyAgree.generateSecret(SECRET_ALGORITHM);
	}
	/**
	 * 取得私钥
	 * @date 2016年6月8日 上午11:07:18
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
		Key privateKey = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBase64(privateKey.getEncoded());
	}
	
	/**
	 * 取得公钥
	 * @date 2016年6月8日 上午11:08:36
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String,Object> keyMap) throws Exception{
		Key publicKey = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBase64(publicKey.getEncoded());
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,Object> keyMap = initKey();
		String publicKey = getPublicKey(keyMap);
		Map<String,Object> clentMap = initKey(publicKey);
		
		String inputData = "大大大";
		//加密 服务端公钥 客户端私钥 加密
		byte[] encrpty = encrpty(inputData.getBytes(), publicKey, getPrivateKey(clentMap));
		//解密  服务端 公钥 客户端私钥解密
		byte[] decrpty = decrpty(encrpty, publicKey, getPrivateKey(clentMap));
		System.out.println(new String(decrpty));
		
		// 加密 客户端公钥 服务端私钥 
		byte[] encrpty2 = encrpty(inputData.getBytes(), getPublicKey(clentMap), getPrivateKey(keyMap));
		// 解密 服务端公钥 客户端私钥
		byte[] decrpty2 = decrpty(encrpty2, publicKey, getPrivateKey(clentMap));
		System.out.println(new String(decrpty2));
	}
}
