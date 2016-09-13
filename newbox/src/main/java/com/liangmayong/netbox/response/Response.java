package com.liangmayong.netbox.response;

import com.liangmayong.netbox.NetBox;
import com.liangmayong.netbox.defualts.DefualtNetBoxConverter;
import com.liangmayong.netbox.interfaces.INetBoxResponse;
import com.liangmayong.netbox.interfaces.NetBoxConverter;

import java.util.List;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class Response implements INetBoxResponse {

    private String body = "";
    private String defualtKey = null;
    // converterTypes
    private Class<? extends NetBoxConverter> converterType = DefualtNetBoxConverter.class;

    public String getBody() {
        return body;
    }

    /**
     * setBody
     *
     * @param body body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * setDefualtKey
     *
     * @param defualtKey defualtKey
     */
    public void setDefualtKey(String defualtKey) {
        this.defualtKey = defualtKey;
    }

    /**
     * getConverter
     *
     * @return converter
     */
    public final NetBoxConverter getConverter() {
        return NetBox.getConverterInstance(converterType);
    }

    /**
     * setConverter
     *
     * @param converterType converterType
     */
    public final void setConverter(Class<? extends NetBoxConverter> converterType) {
        if (converterType == null) {
            converterType = DefualtNetBoxConverter.class;
        }
        this.converterType = converterType;
    }

    @Override
    public boolean isSuccess() {
        return getConverter().isSuccess(this);
    }

    @Override
    public String getErrorMessage() {
        return getConverter().converterErrorMessage(this);
    }

    @Override
    public String getErrorCode() {
        return getConverter().converterErrorCode(this);
    }

    @Override
    public <T> T getData(Class<T> entityClass) {
        if (defualtKey == null || "".equals(defualtKey)) {
            defualtKey = getConverter().converterDefualtKey();
        }
        return getConverter().converter(defualtKey, entityClass, this);
    }

    @Override
    public <T> T getData(String key, Class<T> entityClass) {
        return getConverter().converter(key, entityClass, this);
    }

    @Override
    public <T> List<T> getList(Class<T> entityClass) {
        if (defualtKey == null || "".equals(defualtKey)) {
            defualtKey = getConverter().converterDefualtKey();
        }
        return getConverter().converterList(defualtKey, entityClass, this);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> entityClass) {
        return getConverter().converterList(key, entityClass, this);
    }
}
