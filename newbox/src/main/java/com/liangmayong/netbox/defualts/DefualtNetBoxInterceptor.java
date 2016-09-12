package com.liangmayong.netbox.defualts;

import android.content.Context;

import com.liangmayong.netbox.interfaces.NetBoxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetBoxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;
import com.liangmayong.netbox.throwables.UnkownError;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetBoxInterceptor implements NetBoxInterceptor<Response> {

    @Override
    public void destroy(Context context) {
    }

    @Override
    public void asynchronismExecRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetBoxListener<Response> listener) {
        listener.onFailure(new UnkownError("Interceptor uninitialized"));
    }

    @Override
    public Response synchronizationExecRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetBoxError {
        throw new UnkownError("Interceptor uninitialized");
    }

}
