package com.liangmayong.netbox2.interfaces;

import java.util.List;

/**
 * Created by liangmayong on 2016/9/13.
 */
public interface INetboxResponse {

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
     * @param entityClass entityClass
     * @param <T>         t
     * @return t
     */
    <T> T getData(Class<T> entityClass);

    /**
     * getData
     *
     * @param key         key
     * @param entityClass entityClass
     * @param <T>         t
     * @return t
     */
    <T> T getData(String key, Class<T> entityClass);

    /**
     * getList
     *
     * @param entityClass entityClass
     * @param <T>         t
     * @return list
     */
    <T> List<T> getList(Class<T> entityClass);

    /**
     * getList
     *
     * @param key         key
     * @param entityClass entityClass
     * @param <T>         t
     * @return list
     */
    <T> List<T> getList(String key, Class<T> entityClass);
}
