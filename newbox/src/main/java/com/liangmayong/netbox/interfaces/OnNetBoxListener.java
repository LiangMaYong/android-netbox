package com.liangmayong.netbox.interfaces;

import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface OnNetBoxListener<T extends Response> {

    /**
     * onResponse
     *
     * @param response response
     */
    void onResponse(T response);

    /**
     * onFailure
     *
     * @param error error
     */
    void onFailure(NetBoxError error);
}
