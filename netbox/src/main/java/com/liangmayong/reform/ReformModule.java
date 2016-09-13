package com.liangmayong.reform;

import android.content.Context;

import com.liangmayong.reform.errors.ReformError;
import com.liangmayong.reform.errors.ReformUnkownError;
import com.liangmayong.reform.interfaces.OnReformListener;
import com.liangmayong.reform.interfaces.ReformConverter;
import com.liangmayong.reform.interfaces.ReformInterceptor;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * ReformModule
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformModule {

    public ReformModule() {
    }

    // reform
    private Reform reform;
    // reformConverter
    private ReformConverter converter;

    /**
     * setConverter
     *
     * @param converter converter
     */
    private final void setConverter(ReformConverter converter) {
        this.converter = converter;
    }

    /**
     * setReform
     *
     * @param reform reform
     */
    private final void setReform(Reform reform) {
        this.reform = reform;
    }

    /**
     * getConverter
     *
     * @return converter
     */
    private ReformConverter getConverter() {
        return converter;
    }

    /**
     * getReform
     *
     * @return reform
     */
    private Reform getReform() {
        return reform;
    }

    /**
     * enqueue
     *
     * @param context   context
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     */
    protected final void enqueue(Context context, String url, ReformParameter parameter, final OnReformListener listener) {
        if (getReform() != null) {
            getReform().enqueue(context, getConverter(), parseUrl(url), parameter, listener);
            return;
        }
        listener.onFailure(new ReformUnkownError("reform is null"));
    }

    /**
     * execute
     *
     * @param context   context
     * @param url       url
     * @param parameter parameter
     * @return response
     * @throws ReformError error
     */
    protected final ReformResponse execute(Context context, String url, ReformParameter parameter) throws ReformError {
        if (getReform() != null) {
            return getReform().execute(context, getConverter(), parseUrl(url), parameter);
        }
        throw new ReformUnkownError("reform is null");
    }

    /**
     * getCache
     *
     * @param context   context
     * @param url       url
     * @param parameter parameter
     * @return response
     * @throws ReformError error
     */
    protected final ReformResponse getCache(Context context, String url, ReformParameter parameter) throws ReformError {
        if (getReform() != null) {
            return getReform().getCache(context, getConverter(), parseUrl(url), parameter);
        }
        throw new ReformUnkownError("reform is null");
    }

    /**
     * parseUrl
     *
     * @param url url
     * @return url
     */
    private String parseUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://")) {
            return url;
        } else if (url.startsWith("/")) {
            return getUrlHost(getReform().getBaseUrl()) + url;
        } else if (url.startsWith("./")) {
            String baseUrl = getReform().getBaseUrl();
            int sunStart = 1;
            if (baseUrl.endsWith("/")) {
                sunStart = 2;
            }
            return baseUrl + url.substring(sunStart);
        } else {
            return getReform().getBaseUrl() + url;
        }
    }

    /**
     * getUrlHost
     *
     * @param url url
     * @return url
     */
    private String getUrlHost(String url) {
        try {
            String httpName = "";
            int indexStart = url.indexOf("://");
            if (indexStart > 0) {
                httpName = url.substring(0, indexStart) + "://";
            }
            return httpName + new URL(url).getHost();
        } catch (MalformedURLException e) {
        }
        return url;
    }

    /**
     * destroy
     *
     * @param context context
     */
    public void destroy(Context context) {
        if (getReform() != null) {
            getReform().destroy(context);
        }
    }


    /**
     * getInterceptorType
     *
     * @return interceptorType
     */
    protected Class<? extends ReformInterceptor> getInterceptorType() {
        return null;
    }

    /**
     * getConverterType
     *
     * @return converterType
     */
    protected Class<? extends ReformConverter> getConverterType() {
        return null;
    }
}