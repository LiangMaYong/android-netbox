package com.liangmayong.netbox.params;

import com.liangmayong.netbox.interfaces.Method;

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
    //files
    private Map<String, FileParam> mFiles = null;

    public Parameter(Method mMethod, String mUrl, Map<String, String> mParams, Map<String, String> mHeaders, Map<String, FileParam> mFiles) {
        this.mMethod = mMethod;
        this.mUrl = mUrl;
        this.mParams = mParams;
        this.mHeaders = mHeaders;
        this.mFiles = mFiles;
    }

    public Method getMethod() {
        return mMethod;
    }

    public String getUrl() {
        if (mMethod == Method.GET) {
            String url = mUrl;
            url = url.contains("?") ? new StringBuilder(url).append("&").append(getStringParams()).toString() :
                    new StringBuilder(url).append("?").append(getStringParams()).toString();
            url = url.replaceAll(" ", "-");
            return url;
        }
        return mUrl;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, FileParam> getFiles() {
        return mFiles;
    }

    private String getStringParams() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : getParams().entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
