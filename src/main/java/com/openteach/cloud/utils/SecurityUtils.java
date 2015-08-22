package com.openteach.cloud.utils;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * 
 * @author sihai
 *
 */
public abstract class SecurityUtils {

	/** "私钥"参数名称 */
	private static final String PRIVATE_KEY_ATTRIBUTE_NAME = "privatekey";
	
	/**
	 * 
	 * @param value
	 * @param request
	 * @return
	 */
	public static String ecrypt(String value, HttpServletRequest request) {
		//
		return null;
	}
	
	/**
	 * 解密
	 * @param value
	 * @param request
	 * @return
	 */
	public static String decrypt(String value, RSAPrivateKey privateKey) {
		Assert.notNull(privateKey);
		if (StringUtils.isNotEmpty(value)) {
			return RSAUtils.decrypt(privateKey, value);
		}
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static final KeyPair generateKey(HttpServletRequest request) {
		Assert.notNull(request);
		return RSAUtils.generateKeyPair();
	}
	
	/**
	 * 
	 * @param request
	 */
	public static final void removePrivateKey(HttpServletRequest request) {
		Assert.notNull(request);
		HttpSession session = request.getSession();
		session.removeAttribute(PRIVATE_KEY_ATTRIBUTE_NAME);
	}
}
