package com.liangmayong.reform;

import android.os.Bundle;

import com.liangmayong.reform.interfaces.ReformConverter;
import com.liangmayong.reform.interfaces.ReformInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ReformParameter
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformParameter {

    /**
     * Method
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public static enum Method {
        POST, GET, PUT, DELETE, FILES;
    }

    /**
     * CacheType
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public static enum CacheType {
        NET, NET_LOCAL, LOCAL, LOCAL_NET;
    }

    //request time
    private long requestTime = 0;
    //method
    private Method method = Method.GET;
    //params
    private Map<String, String> params = null;
    //commonparams
    private Map<String, String> commonparams = null;
    //interceptorCommonParams
    private Map<String, String> interceptorCommonParams = null;
    //files
    private List<ReformFile> files = null;
    //headers
    private Map<String, String> headers = null;
    //commonheaders
    private Map<String, String> commonheaders = null;
    //interceptorCommonHeaders
    private Map<String, String> interceptorCommonHeaders = null;
    //converter
    private ReformConverter converter = null;
    //interceptor
    private ReformInterceptor interceptor = null;
    //cache enable
    private boolean cacheEnable = false;
    //priority local cache
    private boolean priorityLocalCache = false;
    //extras
    private Bundle extras = null;

    public ReformParameter() {
        params = new HashMap<String, String>();
        headers = new HashMap<String, String>();
    }


    /**
     * getConverter
     *
     * @return converter
     */
    public ReformConverter getConverter() {
        return converter;
    }

    /**
     * getInterceptor
     *
     * @return interceptor
     */
    public ReformInterceptor getInterceptor() {
        return interceptor;
    }

    /**
     * setConverter
     *
     * @param converter converter
     * @return this
     */
    public ReformParameter setConverter(ReformConverter converter) {
        this.converter = converter;
        return this;
    }

    /**
     * setInterceptor
     *
     * @param interceptor interceptor
     */
    public void setInterceptor(ReformInterceptor interceptor) {
        this.interceptor = interceptor;
    }


    /**
     * setFiles
     *
     * @param files files
     * @return this
     */
    public ReformParameter setFiles(List<ReformFile> files) {
        this.files = files;
        return this;
    }

    /**
     * getFiles
     *
     * @return files
     */
    public List<ReformFile> getFiles() {
        if (files == null) {
            files = new ArrayList<ReformFile>();
        }
        return files;
    }

    /**
     * setExtras
     *
     * @param extras extras
     * @return this
     */
    public ReformParameter setExtras(Bundle extras) {
        this.extras = extras;
        return this;
    }

    /**
     * getExtras
     *
     * @return extras
     */
    public Bundle getExtras() {
        if (extras == null) {
            extras = new Bundle();
        }
        return extras;
    }

    /**
     * addParams
     *
     * @param params params
     * @return this
     */
    public ReformParameter addParams(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * addParam
     *
     * @param key   key
     * @param value value
     * @return this
     */
    public ReformParameter addParam(String key, String value) {
        if (value == null) {
            if (this.params.containsKey(key)) {
                this.params.remove(key);
            }
        } else {
            this.params.put(key, value);
        }
        return this;
    }

    /**
     * setCommonParams
     *
     * @param commonparams commonparams
     * @return this
     */
    public ReformParameter setCommonParams(Map<String, String> commonparams) {
        this.commonparams = commonparams;
        return this;
    }

    /**
     * setHeaders
     *
     * @param headers headers
     * @return this
     */
    public ReformParameter setHeaders(Map<String, String> headers) {
        this.headers.clear();
        this.headers.putAll(headers);
        return this;
    }

    /**
     * setCommonHeaders
     *
     * @param commonheaders commonheaders
     * @return this
     */
    public ReformParameter setCommonHeaders(Map<String, String> commonheaders) {
        this.commonheaders = commonheaders;
        return this;
    }

    /**
     * setInterceptorCommonParams
     *
     * @param interceptorCommonParams interceptorCommonParams
     * @return this
     */
    private ReformParameter setInterceptorCommonParams(Map<String, String> interceptorCommonParams) {
        this.interceptorCommonParams = interceptorCommonParams;
        return this;
    }

    /**
     * setInterceptorCommonHeaders
     *
     * @param interceptorCommonHeaders interceptorCommonHeaders
     * @return this
     */
    private ReformParameter setInterceptorCommonHeaders(Map<String, String> interceptorCommonHeaders) {
        this.interceptorCommonHeaders = interceptorCommonHeaders;
        return this;
    }

    /**
     * setMethod
     *
     * @param method method
     * @return this
     */
    public ReformParameter setMethod(Method method) {
        this.method = method;
        return this;
    }

    /**
     * setCacheEnable
     *
     * @param cacheEnable cacheEnable
     * @return this
     */
    public ReformParameter setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
        return this;
    }

    /**
     * isCacheEnable
     *
     * @return cacheEnable
     */
    public boolean isCacheEnable() {
        return cacheEnable;
    }

    /**
     * getParams
     *
     * @return params
     */
    public Map<String, String> getParams() {
        Map<String, String> newparams = new HashMap<String, String>();
        if (interceptorCommonParams != null) {
            newparams.putAll(interceptorCommonParams);
        }
        if (commonparams != null) {
            newparams.putAll(commonparams);
        }
        newparams.putAll(this.params);
        return newparams;
    }

    /**
     * getHeaders
     *
     * @return headers
     */
    public Map<String, String> getHeaders() {
        Map<String, String> newheaders = new HashMap<String, String>();
        if (interceptorCommonHeaders != null) {
            newheaders.putAll(interceptorCommonHeaders);
        }
        if (commonheaders != null) {
            newheaders.putAll(commonheaders);
        }
        newheaders.putAll(this.headers);
        return newheaders;
    }

    /**
     * getMethod
     *
     * @return method
     */
    public Method getMethod() {
        return method;
    }


    /**
     * setLocalCache
     *
     * @param priorityLocalCache priorityLocalCache
     * @return this
     */
    public ReformParameter setPriorityLocalCache(boolean priorityLocalCache) {
        this.priorityLocalCache = priorityLocalCache;
        return this;
    }

    /**
     * isPriorityLocalCache
     *
     * @return priorityLocalCache
     */
    public boolean isPriorityLocalCache() {
        return priorityLocalCache;
    }

    /**
     * getRequestTime
     *
     * @return requestTime
     */
    public long getRequestTime() {
        return requestTime;
    }

    /**
     * setRequestTime
     *
     * @param requestTime requestTime
     */
    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }
}
