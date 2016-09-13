package com.liangmayong.reform.interfaces;

import android.content.Context;

import com.liangmayong.reform.ReformParameter;
import com.liangmayong.reform.ReformResponse;
import com.liangmayong.reform.errors.ReformError;

import java.util.Map;

/**
 * ReformInterceptor
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface ReformInterceptor {

    /**
     * base url
     *
     * @return base url
     */
    String getBaseUrl();

    /**
     * destroy
     *
     * @param context context
     */
    void destroy(Context context);

    /**
     * getCommonHeaders
     *
     * @return common headers
     */
    Map<String, String> getCommonHeaders();

    /**
     * getCommonParams
     *
     * @return common params
     */
    Map<String, String> getCommonParams();

    /**
     * enqueue
     *
     * @param context   context
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     */
    void enqueue(Context context, String url, ReformParameter parameter, OnReformListener listener);

    /**
     * execute
     *
     * @param context   context
     * @param url       url
     * @param parameter parameter
     * @return Response
     * @throws ReformError e
     */
    ReformResponse execute(Context context, String url, ReformParameter parameter) throws ReformError;

    /**
     * getCache
     *
     * @param context   context
     * @param url       url
     * @param parameter parameter
     * @return Response
     * @throws ReformError e
     */
    ReformResponse getCache(Context context, String url, ReformParameter parameter) throws ReformError;
}
