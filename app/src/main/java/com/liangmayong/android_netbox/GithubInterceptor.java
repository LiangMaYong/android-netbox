package com.liangmayong.android_netbox;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.defualts.DefualtNetboxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class GithubInterceptor extends DefualtNetboxInterceptor {

    @Override
    public void execRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers, OnNetboxListener listener) {
        Response response = new Response(url, params, headers);
        response.setBody("[{\"user\":\"lmy\",\"age\":23}]");
        listener.onResponse(response);
    }

    @Override
    public Response syncRequest(Context context, Method method, String url, Map<String, String> params, Map<String, String> headers) throws NetboxError {
        Response response = new Response(url, params, headers);
        response.setBody("this is test interceptor");
        return response;
    }

}
