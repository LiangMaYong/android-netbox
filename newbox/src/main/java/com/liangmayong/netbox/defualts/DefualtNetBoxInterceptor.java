package com.liangmayong.netbox.defualts;

import android.content.Context;

import com.liangmayong.netbox.interfaces.NetBoxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetBoxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;
import com.liangmayong.netbox.throwables.UnkownError;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetBoxInterceptor<T extends Response> implements NetBoxInterceptor<T> {

    @Override
    public void destroy(Context context) {
    }

    @Override
    public void asynchronismExecRequest(Context context, com.liangmayong.netbox.concretes.Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetBoxListener<T> listener) {
        listener.onFailure(new UnkownError("Interceptor uninitialized"));
    }

    @Override
    public T synchronizationExecRequest(Context context, com.liangmayong.netbox.concretes.Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetBoxError {
        throw new UnkownError("Interceptor uninitialized");
    }

}
