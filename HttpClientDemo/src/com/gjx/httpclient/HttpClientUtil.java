package com.gjx.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 600033 on 2018/6/26.
 */
public class HttpClientUtil {
	
	private static final String CHARSET = "UTF-8";
	static HttpClientPoolConfig httpClientPoolConfig;
	static {
		httpClientPoolConfig = new HttpClientPoolConfig();
	}

	public <T> T syncGetHttp(String url, Map<String, String> params, Map<String, String> headers, Class<T> clazz) {
		try {

			CloseableHttpClient closeableHttpClient = httpClientPoolConfig.getHttpClient();
			url = buildUriParameter(url, params);
			HttpGet httpGet = new HttpGet(url);
			if (headers != null) {
				headers.forEach((K, V) -> {
					httpGet.setHeader(K, V);
				});
			}
			CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			String resultStr = EntityUtils.toString(httpEntity, CHARSET);
			if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 300) {
				throw new Exception("接口请求失败，状态码:" + response.getStatusLine().getStatusCode() + "\n" + resultStr);
			}
			EntityUtils.consume(httpEntity);
			System.out.println("请求结果：\n" + resultStr);
			return JSON.parseObject(resultStr, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T syncPostHttp(String url,Map<String,String> uriParams,Map<String,Object> body,Map<String,String> headers ,Class<T> clazz) {
		try {
			CloseableHttpClient closeableHttpClient = httpClientPoolConfig.getHttpClient();
			HttpPost httpPost = new HttpPost(url);
			if(body != null) {
				StringEntity stringEntity = new StringEntity(JSON.toJSONString(body));
				httpPost.setEntity(stringEntity);
			} else if(uriParams != null && uriParams.size()>0) {
				List<NameValuePair> paramArray = new ArrayList<>();
				for (Map.Entry<String, String> entry : uriParams.entrySet()) {
					paramArray.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(paramArray,CHARSET));
			}
			if(headers != null && headers.size() > 0) {
				for(Map.Entry<String, String> entry: headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String resultStr = EntityUtils.toString(httpEntity,CHARSET);
			if(httpResponse.getStatusLine().getStatusCode() < 200 || httpResponse.getStatusLine().getStatusCode()>300) {
				throw new Exception("接口请求失败，状态码:" + httpResponse.getStatusLine().getStatusCode() + "\n" + resultStr);
			}
			return JSON.parseObject(resultStr,clazz);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String buildUriParameter(String url, Map<String, String> params) {
		StringBuilder urlPairs = new StringBuilder("?");
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "http://" + url;
		}
		if (params != null && params.size() > 0) {
			for (Map.Entry entry : params.entrySet()) {
				urlPairs.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			if (urlPairs.toString().length() > 1) {
				urlPairs.deleteCharAt(urlPairs.toString().length() - 1);
			}
		}
		url += urlPairs.toString();
		return url;
	}

}
