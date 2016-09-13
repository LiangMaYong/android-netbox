package com.liangmayong.netbox2.defualts;

import android.content.Context;

import com.liangmayong.netbox2.concretes.Method;
import com.liangmayong.netbox2.interfaces.NetboxInterceptor;
import com.liangmayong.netbox2.interfaces.OnNetboxListener;
import com.liangmayong.netbox2.response.Response;
import com.liangmayong.netbox2.throwables.NetboxError;
import com.liangmayong.netbox2.throwables.UnkownError;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetboxInterceptor implements NetboxInterceptor {

    @Override
    public void destroyRequest(Context context) {
    }

    @Override
    public void execRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetboxListener listener) {
        listener.onFailure(new UnkownError("Interceptor uninitialized"));
    }

    @Override
    public Response syncRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError {
        throw new UnkownError("Interceptor uninitialized");
    }

}
