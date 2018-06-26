package com.gjx.httpclient;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * httpClientPool ¡¨Ω”≥ÿ¿‡≈‰÷√
 * 
 * @author 600033
 *
 */
public class HttpClientPoolConfig {
	
	static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
	static {
		LayeredConnectionSocketFactory ssLFactory = null;
		try {
			ssLFactory = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", ssLFactory)
                .register("http", new PlainConnectionSocketFactory())
                .build();
		poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		poolingHttpClientConnectionManager.setMaxTotal(20);
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(5);
	}

	public CloseableHttpClient getHttpClient() {

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
				.setDefaultRequestConfig(requestConfig).build();
		return httpClient;

	}

}
