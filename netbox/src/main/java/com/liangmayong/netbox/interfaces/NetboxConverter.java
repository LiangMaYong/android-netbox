package com.liangmayong.netbox.interfaces;

import com.liangmayong.netbox.response.Response;

import java.util.List;

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
     * converter
     *
     * @param key         key
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return t
     */
    <T> T converter(String key, Class<T> entityClass, Response response);

    /**
     * converterList
     *
     * @param key         key
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return list
     */
    <T> List<T> converterList(String key, Class<T> entityClass, Response response);
}
