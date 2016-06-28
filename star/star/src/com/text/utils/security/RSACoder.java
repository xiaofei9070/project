package com.text.utils.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 * @author liuxiaofei
 * @date 2016年6月3日
 * @version V1.0
 */
public abstract class RSACoder extends Coder{
	
	
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	/**
	 * 用私钥对信息生成数字签名
	 * @date 2016年6月3日 下午2:47:00
	 * @param data 加密数据 
	 * @param privateKey 私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception{
		/**解密私钥*/
		byte[] keyBytes = decoderBase64(privateKey);
		
		/**构造PKCS8EncodedKeySpec 对象*/
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		/**指定加密算法*/
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		/**取私钥对象*/
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		/**用私钥对信息生成数字签名*/
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		
		return encryptBase64(signature.sign());
	}
	/**
	 * 校验数字签名
	 * @date 2016年6月4日 下午7:48:03
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey,String sign) throws Exception{
		//解密公钥
		byte[] keyBytes = decoderBase64(publicKey);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(decoderBase64(sign));
	}
	/**
	 * 解密
	 * @date 2016年6月4日 下午8:08:04
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrptyByPrivateKey(byte[] data, String key) throws Exception{
		
		byte[] keyBytes = decoderBase64(key);
		
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	/**
	 * 公钥 解密
	 * @date 2016年6月8日 上午9:22:18
	 * @param data 需要解密数据
	 * @param key 公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrptyByPublicKey(byte[] data, String key) throws Exception{
		byte[] keyBytes = decoderBase64(key);
		
		X509EncodedKeySpec X509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey  = keyFactory.generatePublic(X509KeySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥 加密
	 * @date 2016年6月8日 上午9:26:57
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypyByPublic(byte[] data, String key) throws Exception{
		byte[] keyBytes = decoderBase64(key);
		
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥 加密
	 * @date 2016年6月8日 上午9:30:46
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrptyByPrivate(byte[] data, String key) throws Exception{
		byte[] keyBytes = decoderBase64(key);
		
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 取得 私钥
	 * @date 2016年6月8日 上午9:32:54
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBase64(key.getEncoded());
	}
	/**
	 * 取得公钥
	 * @date 2016年6月8日 上午9:33:36
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBase64(key.getEncoded());
	}
	
	/**
	 * 初始化密钥
	 * @date 2016年6月8日 上午9:38:53
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String,Object> keyMap = new HashMap<String,Object>();
		keyMap.put(PRIVATE_KEY, privateKey);
		keyMap.put(PUBLIC_KEY, publicKey);
		return keyMap;
	}
	
}
