package com.liangmayong.netbox.interfaces;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/13.
 */
public interface NetboxResponse {

    /**
     * isSuccess
     *
     * @return true or false
     */
    boolean isSuccess();

    /**
     * isExist
     *
     * @param key   key
     * @param value value
     * @return bool
     */
    boolean isExist(String key, String value);

    /**
     * getTimestamp
     *
     * @return timestamp
     */
    long getTimestamp();

    /**
     * getErrorMessage
     *
     * @return error message
     */
    String getErrorMessage();

    /**
     * getErrorCode
     *
     * @return error code
     */
    String getErrorCode();

    /**
     * getData
     *
     * @param type type
     * @param <T>  type
     * @return t
     */

    <T> T getData(Type type);

    /**
     * getData
     *
     * @param key key
     * @return string data
     */
    String getData(String key);

    /**
     * getData
     *
     * @param key  key
     * @param type type
     * @param <T>  type
     * @return t
     */
    <T> T getData(String key, Type type);
}
