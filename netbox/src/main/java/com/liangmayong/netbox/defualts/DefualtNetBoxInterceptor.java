package com.liangmayong.netbox.defualts;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.netbox.interfaces.NetBoxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetBoxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;
import com.liangmayong.netbox.throwables.UnkownError;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetBoxInterceptor implements NetBoxInterceptor<Response> {

    @Override
    public void destroy(Context context) {
    }

    @Override
    public void execRequest(Context context, Class<? extends NetBoxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetBoxListener<Response> listener) {
        listener.onFailure(new UnkownError("Interceptor uninitialized"));
    }

    @Override
    public Response execSyncRequest(Context context, Class<? extends NetBoxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetBoxError {
        throw new UnkownError("Interceptor uninitialized");
    }

    @Override
    public Response execCacheRequest(Context context, Class<? extends NetBoxConverter> converterType, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetBoxError {
        throw new UnkownError("Interceptor uninitialized");
    }
}
