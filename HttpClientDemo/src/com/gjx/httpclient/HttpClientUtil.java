package com.gjx.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by 600033 on 2018/6/26.
 */
public class HttpClientUtil {
	static HttpClientPoolConfig httpClientPoolConfig;
	static {
		httpClientPoolConfig = new HttpClientPoolConfig();
	}

    public <T> T syncGetHttp(String url,Map<String,String> params,Map<String,String> headers, Class<T> clazz) {
        try {
            StringBuilder urlPairs = new StringBuilder("?");
            CloseableHttpClient closeableHttpClient = httpClientPoolConfig.getHttpClient();
            if(!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://"+url;
            }

            if(params != null && params.size()>0) {
                for (Map.Entry entry: params.entrySet()) {
                    urlPairs.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                if(urlPairs.toString().length()>1) {
                    urlPairs.deleteCharAt(urlPairs.toString().length()-1);
                }
            }
            url += urlPairs.toString();
            HttpGet httpGet = new HttpGet(url);
            if(headers != null) {
                headers.forEach((K,V)->{
                    httpGet.setHeader(K,V);
                });
            }
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            String resultStr = EntityUtils.toString(httpEntity,"utf-8");
            if(response.getStatusLine().getStatusCode() <200 || response.getStatusLine().getStatusCode()>300) {
                throw new Exception("接口请求失败，状态码:"+response.getStatusLine().getStatusCode()+"\n"+resultStr);
            }
            EntityUtils.consume(httpEntity);
            System.out.println("请求结果：\n"+resultStr);
            return JSON.parseObject(resultStr,clazz);
//            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
