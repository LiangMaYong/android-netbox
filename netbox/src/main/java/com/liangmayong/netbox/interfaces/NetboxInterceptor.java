package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.params.Request;
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

    /**
     * execRequest
     *
     * @param context   context
     * @param parameter parameter
     * @param listener  listener
     */
    void execRequest(Context context, Request parameter, OnNetboxListener listener);

    /**
     * syncRequest
     *
     * @param context   context
     * @param parameter parameter
     * @return response
     * @throws NetboxError throws
     */
    Response syncRequest(Context context, Request parameter) throws NetboxError;

}
