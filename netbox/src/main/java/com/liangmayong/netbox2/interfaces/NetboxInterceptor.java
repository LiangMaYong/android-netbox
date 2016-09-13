package com.liangmayong.netbox2.interfaces;

import android.content.Context;

import com.liangmayong.netbox2.concretes.Method;
import com.liangmayong.netbox2.response.Response;
import com.liangmayong.netbox2.throwables.NetboxError;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface NetboxInterceptor {

    /**
     * destroy
     *
     * @param context context
     */
    void destroyRequest(Context context);

    void execRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetboxListener listener);

    Response syncRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError;

}
