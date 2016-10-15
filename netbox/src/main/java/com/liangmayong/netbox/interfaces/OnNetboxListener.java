package com.liangmayong.netbox.interfaces;

import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface OnNetboxListener {

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


    /**
     * onProgress
     *
     * @param progress progress
     */
    void onProgress(int progress);
}
