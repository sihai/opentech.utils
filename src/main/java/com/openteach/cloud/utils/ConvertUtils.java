package com.openteach.cloud.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 
 * @author sihai
 *
 */
public abstract class ConvertUtils {
	
	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static Boolean asBoolean(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == Boolean.class) {
			return (Boolean)v;
		} else if(c == String.class) {
			if(Boolean.TRUE.toString().equals(v)) {
				return true;
			} else if(Boolean.FALSE.toString().equals(v)) {
				return false;
			} else {
				throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to boolean", v, v.getClass().getName()));
			}
		} else {
			throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to boolean", v, v.getClass().getName()));
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static Short asShort(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == Short.class) {
			return (Short)v;
		} else if(c == String.class) {
			try {
				return Short.valueOf((String)v);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to short", v, v.getClass().getName()));
			}
		} else {
			throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to short", v, v.getClass().getName()));
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static Integer asInt(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == Integer.class) {
			return (Integer)v;
		} else if(c == String.class) {
			try {
				return Integer.valueOf((String)v);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to int", v, v.getClass().getName()));
			}
		} else {
			throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to int", v, v.getClass().getName()));
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static Long asLong(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == Short.class) {
			return Long.valueOf((Short)v);
		} else if(c == Integer.class) {
			return Long.valueOf((Integer)v);
		} else if(c == Long.class) {
			return (Long)v;
		} else if(c == String.class) {
			try {
				return Long.valueOf((String)v);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to long", v, v.getClass().getName()));
			}
		} else {
			throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to long", v, v.getClass().getName()));
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static Float asFloat(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == Float.class) {
			return (Float)v;
		} else if(c == Double.class) {
			return Float.valueOf(((Double)v).floatValue());
		} else if(c == String.class) {
			try {
				return Float.valueOf((String)v);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to float", v, v.getClass().getName()));
			}
		} else {
			throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to float", v, v.getClass().getName()));
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static Double asDouble(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == Float.class) {
			return Double.valueOf(((Float)v).doubleValue());
		} else if(c == Double.class) {
			return (Double)v;
		} else if(c == String.class) {
			try {
				return Double.valueOf((String)v);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to double", v, v.getClass().getName()));
			}
		} else {
			throw new IllegalArgumentException(String.format("%s(type:%s) can not convert to double", v, v.getClass().getName()));
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static BigDecimal asBigDecimal(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == Float.class) {
			return new BigDecimal((Float)v);
		} else if(c == Double.class) {
			return new BigDecimal((Double)v);
		} else if(c == BigDecimal.class) {
			return (BigDecimal)v;
		} else if(c == String.class) {
			return BigDecimal.valueOf(Double.valueOf((String)v));
		} else {
			return BigDecimal.valueOf(asDouble(v));
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static String asString(Object v) {
		if(null == v) {
			return null;
		}
		Class c = v.getClass();
		if(c == String.class) {
			return (String)v;
		} else {
			return String.valueOf(v);
		}
	}
	
	/**
	 * 
	 * @param v
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date asDate(Object v, String format) throws ParseException {
		String s = asString(v);
		if(StringUtils.isBlank(s)) {
			return null;
		}
		return DateUtils.parseDate(s, new String[] {format});
	}
	
	/**
	 * 
	 * @param v
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date asDate(Object v) throws ParseException {
		String s = asString(v);
		if(StringUtils.isBlank(s)) {
			return null;
		}
		return DateUtils.parseDate(s, DATE_PATTERNS);
	}
	
	/**
	 * 
	 * @param v
	 * @param type
	 * @return
	 */
	public static Object as(Object v, Class<?> clazz) {
		if(boolean.class == clazz || Boolean.class == clazz) {
			return asBoolean(v);
		} else if(short.class == clazz || Short.class == clazz) {
			return asShort(v);
		} else if(int.class == clazz || Integer.class == clazz) {
			return asInt(v);
		} else if(long.class == clazz || Long.class == clazz) {
			return asLong(v);
		} else if(float.class == clazz || Float.class == clazz) {
			return asFloat(v);
		} else if(double.class == clazz || Double.class == clazz) {
			return asDouble(v);
		} else if(BigDecimal.class == clazz) {
			return asBigDecimal(v);
		} else if(Date.class == clazz) {
			try {
				return asDate(v);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Wrong date format", e);
			}
		} else if (String.class == clazz) {
			return asString(v);
		} else if(Enum.class.isAssignableFrom(clazz)) {
			try {
				return clazz.getMethod("valueOf", new Class[] {String.class}).invoke(null, v);
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("Unsupported enum, that has none valueOf(String) method", e);
			} catch (InvocationTargetException e) {
				throw new IllegalArgumentException("Unsupported enum, that has none valueOf(String) method", e);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Unsupported enum, that has none valueOf(String) method", e);
			}
		}
		throw new IllegalArgumentException("Unsupported type:" + clazz.getName());
	}
}
