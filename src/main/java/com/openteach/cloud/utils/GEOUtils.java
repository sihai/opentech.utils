package com.openteach.cloud.utils;

/**
 * 
 * @author sihai
 *
 */
public class GEOUtils {

	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double d(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 
	 * @param longitude0
	 * @param latitude0
	 * @param longitude1
	 * @param latitude1
	 * @return
	 */
	public static final double distance(double longitude0, double latitude0, double longitude1, double latitude1) {
		return d(longitude0, latitude0, longitude1, latitude1);
	}
}
