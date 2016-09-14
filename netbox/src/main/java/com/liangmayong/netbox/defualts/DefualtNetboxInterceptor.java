package com.liangmayong.netbox.defualts;

import android.content.Context;

import com.liangmayong.netbox.concretes.Parameter;
import com.liangmayong.netbox.interfaces.NetboxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.UnkownError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetboxInterceptor implements NetboxInterceptor {

    @Override
    public void destroyRequest(Context context) {
    }

    @Override
    public void execRequest(Context context, Parameter parameter, OnNetboxListener listener) {
        listener.onFailure(new UnkownError("Interceptor uninitialized"));
    }

    @Override
    public Response syncRequest(Context context, Parameter parameter) throws NetboxError {
        throw new UnkownError("Interceptor uninitialized");
    }

}
