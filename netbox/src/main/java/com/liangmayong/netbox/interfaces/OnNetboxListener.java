package com.liangmayong.netbox.interfaces;

import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface OnNetboxListener {
    /**
     * onResponseHistory
     *
     * @param response response
     */
    void onResponseHistory(Response response);

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
