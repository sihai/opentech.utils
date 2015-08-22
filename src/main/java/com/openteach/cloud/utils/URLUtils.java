package com.openteach.cloud.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

/**
 * 
 * @author sihai
 *
 */
public class URLUtils {

	/**
	 * 
	 * @param strURL
	 * @param parameter
	 * @return
	 */
	public static String getParameter(String strURI, String parameter) {

		if (StringUtils.isBlank(strURI)) {
			throw new IllegalArgumentException();
		}
		try {
			URI uri = new URI(strURI);
			return getParameter(uri, parameter);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param parameter
	 * @return
	 */
	public static String getParameter(URI uri, String parameter) {
		String queryString = uri.getQuery();
		if (StringUtil.isBlank(queryString)) {
			return null;
		}
		String[] kvs = queryString.split("&");
		String[] kv = null;
		for (String s : kvs) {
			if (StringUtil.isBlank(s)) {
				continue;
			} else {
				kv = s.split("=");
				if (kv.length == 2) {
					if (kv[0].equals(parameter)) {
						return kv[1];
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		return encode(value, "utf-8");
	}
	
	/**
	 * 
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String encode(String value, String charset) {
		try {
			return URLEncoder.encode(value, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		return decode(value, "utf-8");
	}
	
	/**
	 * 
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String decode(String value, String charset) {
		try {
			return URLDecoder.decode(value, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
