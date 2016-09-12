package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface NetBoxInterceptor<T extends Response> {

    /**
     * destroy
     *
     * @param context context
     */
    void destroy(Context context);

    void asynchronismExecRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetBoxListener<T> listener);

    T synchronizationExecRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetBoxError;
}
