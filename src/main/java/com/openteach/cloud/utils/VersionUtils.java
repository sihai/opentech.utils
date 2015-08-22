package com.openteach.cloud.utils;

import java.util.regex.Pattern;

/**
 * <b>版本工具类
 * <p>
 * 格式  xx.xx.xx
 * @author sihai
 *
 */
public class VersionUtils {

	private static final Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)$");
	
	/**
	 * 
	 * @param version
	 * @return
	 */
	public static boolean isValidate(String version) {
		return pattern.matcher(version).matches();
	}
	
	/**
	 * 
	 * @param version
	 * @return
	 */
	public static long asLong(String version) {
		if(!isValidate(version)) {
			throw new IllegalArgumentException("Wrong version format");
		}
		String[] a = version.split("\\.");
		return (Long.valueOf(a[0]) << 16) + (Long.valueOf(a[1]) << 8) + Long.valueOf(a[2]);
	}
	
	/**
	 * 
	 * @param version
	 * @return
	 */
	public static String asString(long version) {
		version &= 0xFFFFFF;
		long v0 = (version & 0xFF0000) >> 16;
		long v1 = (version & 0x00FF00) >> 8;
		long v2 = version & 0x0000FF;
		return String.format("%d.%d.%d", v0, v1, v2);
	}
	
	public static void main(String[] args) {
		System.out.println(VersionUtils.isValidate("0.0.1"));
		System.out.println(VersionUtils.isValidate("0.1.0"));
		System.out.println(VersionUtils.isValidate("1.2.3"));
		System.out.println(VersionUtils.isValidate("17.2.3"));
		
		System.out.println(VersionUtils.asLong("17.2.3"));
		
		System.out.println(VersionUtils.asString(VersionUtils.asLong("17.2.3")));
	}
}
