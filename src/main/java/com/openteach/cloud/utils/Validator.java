package com.openteach.cloud.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author sihai
 *
 */
public abstract class Validator {

	/**
	 * 
	 */
	private static final Pattern EMAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	/**
	 * 
	 */
	private static final Pattern MOBILE = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isEmail(String v) {
		return StringUtils.isBlank(v) ? false : EMAIL.matcher(v).matches();
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isMobile(String v) {
		return StringUtils.isBlank(v) ? false : MOBILE.matcher(v).matches();
	}
}
