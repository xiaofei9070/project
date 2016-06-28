package com.text.utils.security;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.Cipher;

/** 1.生成keyStroe文件 <br>
 *  keytool -genkey -validity 36000 -alias www.zlex.org -keyalg RSA -keystore d:\zlex.keystore <br>
 *  -genkey表示生成密钥 <br>
 *	-validity指定证书有效期，这里是36000天 <br>
 *	-alias指定别名，这里是www.zlex.org <br>
 *	-keyalg指定算法，这里是RSA <br>
 *	-keystore指定存储位置，这里是d:\zlex.keystore <br><br>
 * 
 *  2.生成签名证书 keytool -export -keystore d:\zlex.keystore -alias www.zlex.org -file d:\zlex.cer -rfc <br>
 *  -genkey表示生成密钥 <br>
 *	-validity指定证书有效期，这里是36000天 <br>
 *	-alias指定别名，这里是www.zlex.org <br>
 *	-keyalg指定算法，这里是RSA <br>
 *	-keystore指定存储位置，这里是d:\zlex.keystore <br><br>
 * 
 * 
 *  3.对代码进行签名 jar<br>
 *  jarsigner -storetype jks -keystore zlex.keystore -verbose tools.jar www.zlex.org<br><br>
 *  
 *  4.验证<br>
 *  jarsigner -verify -verbose -certs tools.jar  <br><br>
 *  
 *  
 * 通过证书加密 解密
 * @author liuxiaofei
 * @date 2016年6月8日
 * @version V1.0
 */
public class CertificateCoder extends Coder {
	
	public static final String KEY_STORE = "JKS";
	
	public static final String X509 = "X.509";
	
	private static KeyStore getKeyStore(String keyStorePath, String password) throws Exception {
		FileInputStream fis = new FileInputStream(keyStorePath);
		KeyStore ks = KeyStore.getInstance(KEY_STORE);
		ks.load(fis,password.toCharArray());
		fis.close();
		return ks;
	}
	
	/**
	 * 由 keyStore 获取私钥
	 * @date 2016年6月8日 下午3:27:29
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(String keyStorePath, String alias, String password)
			throws Exception{
		KeyStore ks = getKeyStore(keyStorePath, password);
		PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
		return key;
	}
	
	private static Certificate getCertificate(String certificatePath) throws Exception{
		CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
		FileInputStream fis = new FileInputStream(certificatePath);
		Certificate certificate = (Certificate) certificateFactory.generateCertificate(fis);
		fis.close();
		return certificate;
	}
	
	private static Certificate getCertificate(String keyStorePath,String alias,String password)
			throws Exception {
		KeyStore ks = getKeyStore(keyStorePath, password);
		return  ks.getCertificate(alias);
	}
	
	/**
	 * Certificate 获取公钥
	 * @date 2016年6月8日 下午3:31:18
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String certificatePath)
			throws Exception {
		Certificate cf = getCertificate(certificatePath);
		PublicKey pubKey = cf.getPublicKey();
		return pubKey;
	}
	
	/**
	 * 私钥 加密
	 * @date 2016年6月8日 下午3:40:30
	 * @param data
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrtyByPrivateKey(byte[] data,String keyStorePath, String alias,String password)
			throws Exception {
		PrivateKey priKey = getPrivateKey(keyStorePath, alias, password);
		Cipher cipher = Cipher.getInstance(priKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, priKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥解密
	 * @date 2016年6月8日 下午3:43:04
	 * @param data
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrtyByPrivateKey(byte[] data,String keyStorePath, String alias,String password) 
			throws Exception {
		PrivateKey priKey = getPrivateKey(keyStorePath, alias, password);
		Cipher cipher = Cipher.getInstance(priKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥 加密
	 * @date 2016年6月8日 下午3:45:48
	 * @param data
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrptyByPublicKey(byte[] data,String certificatePath)
			throws Exception {
		PublicKey pubKey = getPublicKey(certificatePath);
		Cipher cipher = Cipher.getInstance(pubKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥解密
	 * @date 2016年6月8日 下午3:49:04
	 * @param data
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrptyByPublicKey(byte[] data,String certificatePath)
		throws Exception {
		PublicKey pubKey = getPublicKey(certificatePath);
		Cipher cipher = Cipher.getInstance(pubKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}
	/**
	 * 验证Certificate是否过期或无效 
	 * @date 2016年6月8日 下午3:58:18
	 * @param date
	 * @param certificatePath
	 * @return
	 */
	public static boolean verifyCertificate(Date date,String certificatePath){
		boolean status = true;
		try {
			Certificate certificate = getCertificate(certificatePath);
			status = verifyCertificate(date,certificate);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}
	
	private static boolean verifyCertificate(Date date, Certificate certificate) {
		boolean status = true;
		try {
			X509Certificate x509Certificate = (X509Certificate) certificate;
			x509Certificate.checkValidity(date);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}
	/**
	 * 验证Certificate 
	 * @date 2016年6月8日 下午3:57:50
	 * @param certificatePath
	 * @return
	 */
	public static boolean verifyCertificate(String certificatePath){
		return verifyCertificate(new Date(),certificatePath);
	}
	
	/**
	 * 签名
	 * @date 2016年6月8日 下午4:08:46
	 * @param sign
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] sign, String keyStorePath, String alias,String password)
			throws Exception{
		//获取证书
		X509Certificate x509Certificate = (X509Certificate) getCertificate(keyStorePath, alias, password);
		
		KeyStore ks = getKeyStore(keyStorePath, password);
		
		PrivateKey priKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
		
		Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
		signature.initSign(priKey);
		signature.update(sign);
		return encryptBase64(signature.sign());
	}
	
	/**
	 * 验证 签名
	 * @date 2016年6月8日 下午4:12:42
	 * @param data
	 * @param sign
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data,String sign,String certificatePath) throws Exception {
		X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
		
		PublicKey publicKey = x509Certificate.getPublicKey();
		
		Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(decoderBase64(sign));
	}
	
	/**
	 *  验证Certificate 
	 * @date 2016年6月8日 下午4:17:09
	 * @param date
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 */
	public static boolean verifyCertificate(Date date,String keyStorePath,String alias,String password){
		boolean status = true;
		try {
			Certificate certificate = getCertificate(keyStorePath, alias, password);
			status = verifyCertificate(date, certificate);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}
	
	/**
	 * 验证Certificate 
	 * @date 2016年6月8日 下午4:18:23
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @return
	 */
	public static boolean verifyCertificate(String keyStorePath,String alias,String password){
		return verifyCertificate(new Date(),keyStorePath, alias, password);
	}
	
	public static void main(String[] args) throws Exception {
		String keyStorePath = "securityReource/starn1.keystore";
		String certificatePath = "securityReource/starn1.cer";
		String password = "123456";
		String alias = "www.starn1.com";
		
		String inputData = "sign";
		byte[] encrtyByPrivateKey = encrtyByPrivateKey(inputData.getBytes(), keyStorePath, alias, password);
		byte[] decrptyByPublicKey = decrptyByPublicKey(encrtyByPrivateKey, certificatePath);
		System.out.println(new String(decrptyByPublicKey));
		
		//产生签名
		String sign = sign(encrtyByPrivateKey, keyStorePath, alias, password);
		System.out.println(sign);
		//验证签名是否有效 过期
		boolean verify = verify(encrtyByPrivateKey, sign, certificatePath);
		System.out.println(verify);
	}
	
}
