package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.concretes.Parameter;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface NetboxInterceptor {

    /**
     * destroy
     *
     * @param context context
     */
    void destroyRequest(Context context);

    void execRequest(Context context, Parameter parameter, OnNetboxListener listener);

    Response syncRequest(Context context, Parameter parameter) throws NetboxError;

}
