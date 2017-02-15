package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.params.Request;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.UnkownError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefaultNetboxInterceptor implements NetboxInterceptor {

    @Override
    public void destroyRequest(Context context) {
    }

    @Override
    public void execRequest(Context context, Request request, OnNetboxListener listener) {
        listener.onFailure(new UnkownError("Interceptor invalid"));
    }

    @Override
    public Response syncRequest(Context context, Request request) throws NetboxError {
        throw new UnkownError("Interceptor invalid");
    }

}
