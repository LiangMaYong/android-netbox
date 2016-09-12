package com.liangmayong.reform.interfaces;

import com.liangmayong.reform.ReformResponse;

import java.util.List;

/**
 * ReformConverter
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface ReformConverter {

    /**
     * is success
     *
     * @param response response
     * @return true or false
     */
    boolean isSuccess(ReformResponse response);

    /**
     * parseErrorMessage
     *
     * @param response response
     * @return error message
     */
    String parseErrorMessage(ReformResponse response);

    /**
     * parseErrorCode
     *
     * @param response response
     * @return error code
     */
    String parseErrorCode(ReformResponse response);

    /**
     * parse
     *
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return t
     */
    <T> T parse(Class<T> entityClass, ReformResponse response);

    /**
     * parse
     *
     * @param key         key
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return t
     */
    <T> T parse(String key, Class<T> entityClass, ReformResponse response);

    /**
     * parseList
     *
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return list
     */
    <T> List<T> parseList(Class<T> entityClass, ReformResponse response);

    /**
     * parseList
     *
     * @param key         key
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return list
     */
    <T> List<T> parseList(String key, Class<T> entityClass, ReformResponse response);

}
