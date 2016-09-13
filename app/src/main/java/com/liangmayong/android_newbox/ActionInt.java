package com.liangmayong.android_newbox;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.defualts.DefualtNetboxInterceptor;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class ActionInt extends DefualtNetboxInterceptor {


    @Override
    public void execRequest(Context context, final Class<? extends NetboxConverter> converterType, final Method method, final String url, final Map<String, String> params, final Map<String, String> headers, final OnNetboxListener<Response> listener) {
        Response response = new Response(converterType);
        response.setBody(url + "," + params.toString() + "," + headers.toString() + "," + method.name());
        listener.onResponse(response);
    }

    @Override
    public Response execSyncRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError {
        Response response = new Response(converterType);
        response.setBody(url + "," + params.toString() + "," + headers.toString() + "," + method.name());
        return response;
    }

    @Override
    public Response execCacheRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError {
        Response response = new Response(converterType);
        response.setBody(url + "," + params.toString() + "," + headers.toString() + "," + method.name());
        return response;
    }
}
