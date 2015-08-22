package com.openteach.cloud.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Assert;

/**
 * 
 * @author sihai
 *
 */
public abstract class RSAUtils {

	/** 密钥大小 */
	private static final int KEY_SIZE = 1024;
	
	/** 安全服务提供者 */
	private static final Provider PROVIDER = new BouncyCastleProvider();
	
	/**
	 * 
	 * @param publicKey
	 * @return
	 */
	public static final String getBase64Modulus(RSAPublicKey publicKey) {
		String base64 = Base64.encodeBase64String(publicKey.getModulus().toByteArray());
		return StringUtils.replace(base64, "\r\n", "");

	}
	
	/**
	 * 
	 * @param publicKey
	 * @return
	 */
	public static final String getBase64Exponent(RSAPublicKey publicKey) {
		String base64 = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());
		return StringUtils.replace(base64, "\r\n", "");
	}
	
	/**
	 * 生成密钥对
	 * 
	 * @return 密钥对
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", PROVIDER);
			keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param publicKey
	 *            公钥
	 * @param data
	 *            数据
	 * @return 加密后的数据
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) {
		Assert.notNull(publicKey);
		Assert.notNull(data);
		try {
			Cipher cipher = Cipher.getInstance("RSA", PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param publicKey
	 *            公钥
	 * @param text
	 *            字符串
	 * 
	 * @return Base64编码字符串
	 */
	public static String encrypt(PublicKey publicKey, String text) {
		Assert.notNull(publicKey);
		Assert.notNull(text);
		byte[] data = encrypt(publicKey, text.getBytes());
		return data != null ? Base64.encodeBase64String(data) : null;
	}

	/**
	 * 解密
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            数据
	 * @return 解密后的数据
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		Assert.notNull(privateKey);
		Assert.notNull(data);
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param privateKey
	 *            私钥
	 * @param text
	 *            Base64编码字符串
	 * @return 解密后的数据
	 */
	public static String decrypt(PrivateKey privateKey, String text) {
		Assert.notNull(privateKey);
		Assert.notNull(text);
		byte[] data = decrypt(privateKey, Base64.decodeBase64(text));
		return data != null ? new String(data) : null;
	}
	
	/**
	 * 将私钥转换成string
	 * @param privateKey
	 * @return
	 */
	public static String toString(RSAPrivateKey privateKey) {
		StringBuilder sb = new StringBuilder();
		sb.append(privateKey.getModulus());
		sb.append("\n");
		sb.append(privateKey.getPrivateExponent());
		return sb.toString();
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public static RSAPrivateKey fromString(String text) {
		try {
			String[] a = text.split("\n");
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(new BigInteger(a[0]), new BigInteger(a[1]));  
	        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException  e) {
			throw new RuntimeException(e);
		}
	}
}
