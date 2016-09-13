package com.liangmayong.netbox.interfaces;

import java.lang.reflect.Type;
import java.util.List;

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
     * @param <T>  t
     * @return t
     */
    <T> T getData(Type type);

    /**
     * getData
     *
     * @param key  key
     * @param type type
     * @param <T>  t
     * @return t
     */
    <T> T getData(String key, Type type);

    /**
     * getList
     *
     * @param type type
     * @param <T>  t
     * @return list
     */
    <T> List<T> getList(Type type);

    /**
     * getList
     *
     * @param key  key
     * @param type type
     * @param <T>  t
     * @return list
     */
    <T> List<T> getList(String key, Type type);
}
