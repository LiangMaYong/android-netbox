package com.liangmayong.android_netbox;

import android.content.Context;

import com.liangmayong.netbox.concretes.Parameter;
import com.liangmayong.netbox.defualts.DefualtNetboxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class GithubInterceptor extends DefualtNetboxInterceptor {

    @Override
    public void execRequest(Context context, Parameter parameter, OnNetboxListener listener) {
        Response response = new Response(parameter);
        response.setBody("{\"data\":[{\"user\":\"lmy\",\"age\":23}],\"id\":\"15800000000\"}");
        listener.onResponse(response);
    }

    @Override
    public Response syncRequest(Context context, Parameter parameter) throws NetboxError {
        Response response = new Response(parameter);
        response.setBody("this is test interceptor");
        return response;
    }

}
