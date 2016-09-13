package com.liangmayong.netbox.defualts;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.UnkownError;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetboxInterceptor implements NetboxInterceptor<Response> {

    @Override
    public void destroy(Context context) {
    }

    @Override
    public void execRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetboxListener<Response> listener) {
        listener.onFailure(new UnkownError("Interceptor uninitialized"));
    }

    @Override
    public Response execSyncRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError {
        throw new UnkownError("Interceptor uninitialized");
    }

    @Override
    public Response execCacheRequest(Context context, Class<? extends NetboxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError {
        throw new UnkownError("Interceptor uninitialized");
    }
}
