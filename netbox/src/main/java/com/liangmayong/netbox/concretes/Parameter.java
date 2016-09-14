package com.liangmayong.netbox.concretes;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/14.
 */
public class Parameter {
    // method
    private Method mMethod = Method.GET;
    // url
    private String mUrl = "";
    // params
    private Map<String, String> mParams = null;
    // headers
    private Map<String, String> mHeaders = null;

    public Parameter(Method mMethod, String mUrl, Map<String, String> mParams, Map<String, String> mHeaders) {
        this.mMethod = mMethod;
        this.mUrl = mUrl;
        this.mParams = mParams;
        this.mHeaders = mHeaders;
    }

    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        this.mMethod = method;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public void setParams(Map<String, String> params) {
        this.mParams = params;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
    }
}
