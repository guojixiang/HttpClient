package com.gjx.httpclient;

import java.util.HashMap;
import java.util.Map;


public class Result {

    private String code;

    private String message;

    private Map<String,Object> returdata;

    public Result(){}

    public Result setCode(String code) {
        this.code = code;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result setData(Map<String, Object> data) {
        this.returdata = data;
        return this;
    }

    public Result setData(String key,Object data) {
        Map<String,Object> map = new HashMap<String, Object>(1);
        map.put(key,data);
        this.returdata = map;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getReturdata() {
        return returdata;
    }

    public void setReturdata(Map<String, Object> returdata) {
        this.returdata = returdata;
    }
}
