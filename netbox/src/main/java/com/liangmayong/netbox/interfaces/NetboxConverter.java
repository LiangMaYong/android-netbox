package com.liangmayong.netbox.interfaces;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/12.
 */
public interface NetboxConverter {

    /**
     * is success
     *
     * @param body body
     * @return true or false
     */
    boolean isSuccess(String body);

    /**
     * converterErrorMessage
     *
     * @param body body
     * @return error message
     */
    String converterErrorMessage(String body);

    /**
     * converterErrorCode
     *
     * @param body body
     * @return error code
     */
    String converterErrorCode(String body);

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
     * @param key  key
     * @param body body
     * @return t
     */
    String converterKey(String key, String body);

    /**
     * converterBody
     *
     * @param body body
     * @return t
     */
    String converterBody(String body);
}
