package com.liangmayong.netbox.defaults.abstracts;

import android.content.Context;

import com.liangmayong.netbox.interfaces.NetboxInterceptor;
import com.liangmayong.netbox.callbacks.OnNetboxListener;
import com.liangmayong.netbox.params.Request;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.errors.UnkownException;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class AbstractDefaultNetboxInterceptor implements NetboxInterceptor {

    @Override
    public void destroyRequest(Context context) {
    }

    @Override
    public void execRequest(Context context, Request request, OnNetboxListener listener) {
        listener.onFailure(new UnkownException("Interceptor invalid"));
    }

    @Override
    public Response syncRequest(Context context, Request request) throws NetboxError {
        throw new UnkownException("Interceptor invalid");
    }

}
