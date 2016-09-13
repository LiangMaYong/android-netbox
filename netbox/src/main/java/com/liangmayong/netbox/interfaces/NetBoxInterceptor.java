package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface NetboxInterceptor<T extends Response> {

    /**
     * destroy
     *
     * @param context context
     */
    void destroy(Context context);

    void execRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetboxListener<T> listener);

    T execSyncRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError;

    T execCacheRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError;
}
