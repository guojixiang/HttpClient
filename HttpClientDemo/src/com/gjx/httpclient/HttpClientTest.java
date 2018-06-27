package com.gjx.httpclient;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class HttpClientTest {

	public static void main(String[] args) {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
//		String url = "http://localhost:8081/postTest";
		String url = "https://useroauth.systoon.com/oauth/authorize";
//		Map<String,String> params = new HashMap<>();
		Map<String,String> params = new HashMap<>();
		params.put("response_type", "code");
		Map<String,String> headers = new HashMap<>();
//		headers.put("Content-Type", "application/json;");
		Class clazz = Result.class;
		Result result = (Result) httpClientUtil.syncGetHttp(url, params, headers, clazz);
//		Result result = (Result) httpClientUtil.syncPostHttp(url, params, null, headers, clazz);
		System.out.println("ÇëÇó½á¹û£º\n"+ JSON.toJSONString(result));
	}

}
