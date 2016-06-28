package com.text.utils.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * DSA 组件
 * @author liuxiaofei
 * @date 2016年6月8日
 * @version V1.0
 */
public class DSACoder extends Coder{
	
	public static final String ALGORITHM = "DSA";
	
	private static final int KEY_SIZE = 1024;
	
	private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";
	
	private static final String PUBLIC_KEY = "DSAPublicKey";
	
	private static final String PRIVATE_KEY = "DSAPrivateKey";
	
	/**
	 * 用私钥 对信息 生成 签名
	 * @date 2016年6月8日 下午1:20:34
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data,String privateKey) throws Exception {
		byte[] keyBytes = decoderBase64(privateKey);
		
		PKCS8EncodedKeySpec pkKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkKeySpec);
		
		Signature signture = Signature.getInstance(keyFactory.getAlgorithm());
		signture.initSign(priKey);
		signture.update(data);
		return encryptBase64(signture.sign());
	}
	
	/**
	 * 校验数字签名
	 * @date 2016年6月8日 下午1:21:51
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 */
	public static boolean verify(byte[] data,String publicKey,String sign) throws Exception{
		byte[] keyBytes = decoderBase64(publicKey);
		
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(decoderBase64(sign));
	}
	/**
	 * 生成密钥
	 * @date 2016年6月8日 下午2:56:31
	 * @param seed 种子
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> initKey(String seed) throws Exception{
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
		
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(seed.getBytes());
		keygen.initialize(KEY_SIZE,secureRandom);
		KeyPair keys = keygen.generateKeyPair();
		
		DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();
		
		Map<String, Object> map = new HashMap<String, Object>(2);  
        map.put(PUBLIC_KEY, publicKey);  
        map.put(PRIVATE_KEY, privateKey);
        return map;
	}
	/**
	 * 生成默认密钥
	 * @date 2016年6月8日 下午2:59:34
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> initKey() throws Exception {
		return initKey(DEFAULT_SEED);
	}
	/**
	 * 取得 私钥
	 * @date 2016年6月8日 下午3:01:17
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String,Object> map) throws Exception {
		Key key = (Key) map.get(PRIVATE_KEY);
		return encryptBase64(key.getEncoded());
	}
	
	/**
	 * 取得公钥
	 * @date 2016年6月8日 下午3:05:46
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String,Object> map) throws Exception {
		Key key = (Key) map.get(PUBLIC_KEY);
		return encryptBase64(key.getEncoded());
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> map = initKey();
		String pubKey = getPublicKey(map);
		System.out.println("公钥：" + pubKey);
		String priKey = getPrivateKey(map);
		System.out.println("私钥：" + priKey);
		
		String inputData = "adad";
		String sign = sign(inputData.getBytes(), priKey);
		System.out.println(verify(inputData.getBytes(), pubKey, sign));
	}
	
}
