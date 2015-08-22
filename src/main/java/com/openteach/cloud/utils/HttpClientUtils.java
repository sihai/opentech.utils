package com.openteach.cloud.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author sihai
 *
 */
public class HttpClientUtils {
	
	public static final String HTTP_HEADER_REFERER = "referer";

	private static HttpClient httpclient;
	
	static {
		
		try {
			final TrustManager[] trustAllCerts = new TrustManager[] {
		        new X509TrustManager() {
		          @Override
		          public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		          }
	
		          @Override
		          public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		          }
	
		          @Override
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		          }
		        }
		    };
	
		    // Install the all-trusting trust manager
		    final SSLContext sslContext = SSLContext.getInstance("SSL");
		    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			
			HttpClientBuilder hcb = HttpClientBuilder.create();
			hcb.setSslcontext(sslContext);
			httpclient = hcb.build();
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static final String sync(String strURL) throws IOException {
		try {
			URI url = new URI(strURL);
			return sync(url);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static final String sync(URI url) throws IOException {
		return sync(url, Collections.EMPTY_MAP);
	}
	
	/**
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws IOException
	 */
	public static final String sync(URI url, Map<String, String> headers) throws IOException {
		HttpGet request = new HttpGet(url);
		if(null != headers && !headers.isEmpty()) {
			for(Map.Entry<String, String> e : headers.entrySet()) {
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		HttpResponse response = httpclient.execute(request);
		return EntityUtils.toString(response.getEntity());
	}
}
