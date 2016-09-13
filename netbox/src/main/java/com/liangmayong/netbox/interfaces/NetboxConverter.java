package com.liangmayong.netbox.interfaces;

import com.liangmayong.netbox.response.Response;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface NetboxConverter {

    /**
     * is success
     *
     * @param response response
     * @return true or false
     */
    boolean isSuccess(Response response);

    /**
     * converterErrorMessage
     *
     * @param response response
     * @return error message
     */
    String converterErrorMessage(Response response);

    /**
     * converterErrorCode
     *
     * @param response response
     * @return error code
     */
    String converterErrorCode(Response response);

    /**
     * converterDefualtKey
     *
     * @return defualt key
     */
    String converterDefualtKey();

    /**
     * converterData
     *
     * @param data
     * @param type type
     * @param <T>  t
     * @return t
     */
    <T> T converterData(String data, Type type);

    /**
     * converterKey
     *
     * @param key      key
     * @param response response
     * @return t
     */
    String converterKey(String key, Response response);
}
