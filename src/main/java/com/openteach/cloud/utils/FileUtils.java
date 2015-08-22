package com.openteach.cloud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author sihai
 *
 */
public class FileUtils {
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static final String getFileName(String path) {
		if (StringUtils.isBlank(path)) {
			return "";
		}

		int index = path.lastIndexOf(File.separator);

		if (index == -1) {
			return "";
		}

		return path.substring(index + 1);
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static final String getSuffix(String path) {
		if (StringUtils.isBlank(path)) {
			return "";
		}

		int index = path.lastIndexOf(".");

		if (index == -1) {
			return "";
		}

		return path.substring(index + 1).toLowerCase();
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static final String removeSuffix(String path) {
		String suffix = getSuffix(path);
		if (StringUtils.isBlank(suffix)) {
			return path;
		} else {
			return path.substring(0, path.indexOf(suffix) - 1);
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static final String read4String(String fileName)
			throws FileNotFoundException, IOException {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
}
