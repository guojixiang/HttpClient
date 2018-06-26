package com.gjx.httpclient;

import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {

	public static void main(String[] args) {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String url = "https://www.baidu.com";
		Map<String,String> params = new HashMap<>();
//		params.put("pageNum", "1");
//		params.put("pageSize", "10");
		Map<String,String> headers = null;
		Class clazz = Result.class;
		Result result = (Result) httpClientUtil.syncGetHttp(url, params, headers, clazz);
	}

}
