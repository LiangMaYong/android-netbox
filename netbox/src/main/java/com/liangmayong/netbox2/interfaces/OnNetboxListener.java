package com.liangmayong.netbox2.interfaces;

import com.liangmayong.netbox2.response.Response;
import com.liangmayong.netbox2.throwables.NetboxError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface OnNetboxListener{

    /**
     * onResponse
     *
     * @param response response
     */
    void onResponse(Response response);

    /**
     * onFailure
     *
     * @param error error
     */
    void onFailure(NetboxError error);
}
