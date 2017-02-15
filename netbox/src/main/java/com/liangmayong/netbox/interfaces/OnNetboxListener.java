package com.liangmayong.netbox.interfaces;

import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

/**
 * Created by liangmayong on 2016/9/12.
 */
public abstract class OnNetboxListener {

    /**
     * onResponseHistory
     *
     * @param historyResponse historyResponse
     */
    public void onResponseHistory(Response historyResponse) {
    }

    /**
     * onResponse
     *
     * @param response response
     */
    public abstract void onResponse(Response response);

    /**
     * onFailure
     *
     * @param error error
     */
    public abstract void onFailure(NetboxError error);

    /**
     * shouldCoverCache
     *
     * @param response        response
     * @param historyResponse historyResponse
     * @return flag
     */
    public boolean shouldCoverCache(Response response, Response historyResponse) {
        return true;
    }
}
