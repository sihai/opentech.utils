package com.openteach.cloud.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author sihai
 *
 */
public class TaobaoUtils {

	public static final String TAOBAO_DETAIL_URL_PREFIX = "http://item.taobao.com/item.htm?id=";
	public static final String TMALL_DETAIL_URL_PREFIX = "http://detail.tmall.com/item.htm?id=";
	
	public static final String NAME = "name";
	public static final String PRICES = "prices";
	public static final String LOGO_URLS = "logoURLs";
	public static final String INTRODUCTION = "introduction";
	
	public static final String TMALL_PRICE_START = "\"price\":\"";
	
	public static final String TAOBAO_PRICE_START = "\"price\" : \"";
	
	public static final String PRICE_END = "\"";
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static final Object getItemDesc4Phone(String id) throws IOException {
		StringBuilder sb = new StringBuilder("http://hws.alicdn.com/cache/mtop.wdetail.getItemDescx/4.1/?data=");
		sb.append(URLEncoder.encode(String.format("{\"item_num_id\":\"%s\"}", id), "utf-8"));
		JSONObject j = JSON.parseObject(HttpClientUtils.sync(sb.toString()));
		JSONArray ja = j.getJSONObject("data").getJSONArray("images");
		sb.delete(0, sb.length());
		
		if(null != ja && ja.size() > 0) {
			List<String> images = new ArrayList<String>(ja.size());
			
			for(Object o : ja) {
				images.add(o.toString());
			}
			
			return images;
		} else {
		
			ja = j.getJSONObject("data").getJSONArray("pages");
			if(null == ja || 0 == ja.size()) {
				return "";
			}
			
			String str = null;
			Document document = null;
			Elements es = null;
			for(Object o : ja) {
				str = o.toString();
				document = Jsoup.parse(str, "utf-8");
				es = document.select("txt");
				if(null != es && es.size() > 0) {
					for(Element e : es) {
						sb.append("<p>").append(e.text()).append("</p>").append("<br/>");
					}
				}
				es = document.select("img");
				if(null != es && es.size() > 0) {
					for(Element e : es) {
						sb.append("<img src=\"").append(e.text()).append("\"></img>");
					}
				}
			}
			return sb.toString();
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static final Map<String, Object> getTaobaoItemInfo4URI(URI uri) throws IOException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(HttpClientUtils.HTTP_HEADER_REFERER, "http://www.taobao.com");
		String html = HttpClientUtils.sync(uri, headers);
		//write2File("/Users/sihai/Documents/taobao.html", html);
		Document doc = Jsoup.parse(html);
		Elements es = doc.select("ul#J_UlThumb img");
		List<String> logoURLs = new ArrayList<String>(es.size());
		for(Element e :es) {
			logoURLs.add(clear(e.attr("data-src")));
		}
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put(LOGO_URLS, logoURLs);
		map.put(NAME, doc.select(".tb-main-title").get(0).attr("data-title"));
		double[] prices = getTaobaoPrice(html);
		if(null != prices) {
			map.put(PRICES, prices);
		}
		map.put(INTRODUCTION, getItemDesc4Phone(URLUtils.getParameter(uri, "id")));
		return map;
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static final Map<String, Object> getTmallItemInfo4URI(URI uri) throws IOException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(HttpClientUtils.HTTP_HEADER_REFERER, "http://www.tmall.com");
		String html = HttpClientUtils.sync(uri, headers);
		write2File("/Users/sihai/Documents/tmall.html", html);
		Document doc = Jsoup.parse(html);
		Elements es = doc.select("ul#J_UlThumb img");
		List<String> logoURLs = new ArrayList<String>(es.size());
		for(Element e :es) {
			logoURLs.add(clear(e.attr("src")));
		}
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put(LOGO_URLS, logoURLs);
		es = doc.select("div.tb-detail-hd > h1 > a");
		if(es.size() == 0) {
			es = doc.select("div.tb-detail-hd > h1");
		}
		map.put(NAME, es.get(0).text());
		double[] prices = getTmallPrice(html);
		if(null != prices) {
			map.put(PRICES, prices);
		}
		map.put(INTRODUCTION, getItemDesc4Phone(URLUtils.getParameter(uri, "id")));
		return map;
	}
	
	/**
	 * 
	 * @param url
	 */
	private static String clear(String url) {
		String suffix = FileUtils.getSuffix(url);
		if(StringUtils.isBlank(suffix)) {
			return null;
		}
		return url.substring(0, url.indexOf("." + suffix)) + "." + suffix;
	}
	
	/**
	 * 
	 * @param html
	 * @return
	 */
	private static double[] getTaobaoPrice(String html) {
		return getPrices(html, TAOBAO_PRICE_START, PRICE_END);
	}
	
	private static double[] getPrices(String html, String start, String end) {
		double c = 0.0;
		double min = Double.MAX_VALUE;
		double max = 0.0;
		int index0 = html.indexOf(start);
		if(-1 == index0) {
			return null;
		}
		int index1 = html.indexOf(end, index0 + start.length());
		while(-1 != index0 && -1 != index1) {
			c = Double.valueOf(html.substring(index0 + start.length(), index1));
			if(c > max) {
				max = c;
			}
			if(c < min) {
				min = c;
			}
			index0 = html.indexOf(start, index1);
			index1 = html.indexOf(end, index0 + start.length());
		}
		return Double.MAX_VALUE != min && 0.0 != max ? new double[]{min, max} : null;
	}
	
	/**
	 * 
	 * @param html
	 * @return
	 */
	private static double[] getTmallPrice(String html) {
		return getPrices(html, TMALL_PRICE_START, PRICE_END);
	}
	
	/**
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static final void write2File(String fileName, String content) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(fileName));
			writer.write(content);
			writer.flush();
		} finally {
			if(null != writer) {
				writer.close();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getTaobaoItemInfo4URI(new URI("http://item.taobao.com/item.htm?id=41086247331")));
		System.out.println(getTmallItemInfo4URI(new URI("http://detail.tmall.com/item.htm?id=41652766800")));
	}
}
