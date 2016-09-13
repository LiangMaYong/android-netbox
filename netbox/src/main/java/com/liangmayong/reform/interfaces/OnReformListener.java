package com.liangmayong.reform.interfaces;

import com.liangmayong.reform.ReformResponse;
import com.liangmayong.reform.errors.ReformError;

/**
 * OnReformListener
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface OnReformListener {

    /**
     * onResponse
     *
     * @param response response
     */
    void onResponse(ReformResponse response);

    /**
     * onFailure
     *
     * @param error error
     */
    void onFailure(ReformError error);

}
