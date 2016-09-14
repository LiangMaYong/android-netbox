package com.liangmayong.netbox.response;

import android.util.Log;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.concretes.Parameter;
import com.liangmayong.netbox.defualts.DefualtNetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxResponse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public final class Response implements NetboxResponse {

    // body
    private String mBody = "";
    // url
    private String mUrl = "";
    // method
    private Method mMethod = Method.GET;
    // defualtKey
    private String mDefualtKey = null;
    // converterTypes
    private Class<? extends NetboxConverter> mConverterType = DefualtNetboxConverter.class;
    // params
    private final Map<String, String> mParams = new HashMap<String, String>();
    // headers
    private final Map<String, String> mHeaders = new HashMap<String, String>();
    // Object
    private Object extraObject = null;

    public Response(Method method, String url, Map<String, String> params, Map<String, String> headers) {
        setUrl(url);
        setParams(params);
        setHeaders(headers);
        setMethod(method);
    }

    public Response(Parameter parameter) {
        if (parameter != null) {
            setUrl(parameter.getUrl());
            setParams(parameter.getParams());
            setHeaders(parameter.getHeaders());
            setMethod(parameter.getMethod());
        }
    }

    public Response() {
    }

    /**
     * getBody
     *
     * @return body
     */
    public String getBody() {
        return mBody;
    }

    /**
     * getHeaders
     *
     * @return mHeaders
     */
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    /**
     * getParams
     *
     * @return mParams
     */
    public Map<String, String> getParams() {
        return mParams;
    }

    /**
     * getUrl
     *
     * @return url
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * getExtraObject
     *
     * @return extraObject
     */
    public Object getExtraObject() {
        return extraObject;
    }

    /**
     * setMethod
     *
     * @param method method
     */
    public void setMethod(Method method) {
        this.mMethod = method;
    }

    /**
     * getMethod
     *
     * @return method
     */
    public Method getMethod() {
        return mMethod;
    }

    /**
     * setExtraObject
     *
     * @param extraObject extraObject
     */
    public void setExtraObject(Object extraObject) {
        this.extraObject = extraObject;
    }

    /**
     * setUrl
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * setBody
     *
     * @param body body
     */
    public void setBody(String body) {
        this.mBody = body;
    }

    /**
     * setHeaders
     *
     * @param headers headers
     */
    public void setHeaders(Map<String, String> headers) {
        this.mHeaders.clear();
        this.mHeaders.putAll(headers);
    }

    /**
     * setParams
     *
     * @param params params
     */
    public void setParams(Map<String, String> params) {
        this.mParams.clear();
        this.mParams.putAll(params);
    }

    /**
     * setDefualtKey
     *
     * @param defualtKey defualtKey
     */
    public void setDefualtKey(String defualtKey) {
        this.mDefualtKey = defualtKey;
    }

    /**
     * getDefualtKey
     *
     * @return defualtKey
     */
    public String getDefualtKey() {
        if (mDefualtKey == null || "".equals(mDefualtKey)) {
            mDefualtKey = getConverter().converterDefualtKey();
        }
        return mDefualtKey;
    }

    /**
     * getConverter
     *
     * @return converter
     */
    private final NetboxConverter getConverter() {
        return Netbox.generateConverter(mConverterType);
    }

    /**
     * setConverter
     *
     * @param converterType converterType
     */
    public final void setConverter(Class<? extends NetboxConverter> converterType) {
        if (converterType == null) {
            converterType = DefualtNetboxConverter.class;
        }
        this.mConverterType = converterType;
    }

    @Override
    public boolean isSuccess() {
        return getConverter().isSuccess(getBody());
    }

    @Override
    public String getErrorMessage() {
        return getConverter().converterErrorMessage(getBody());
    }

    @Override
    public String getErrorCode() {
        return getConverter().converterErrorCode(getBody());
    }

    @Override
    public <T> T getData(Type type) {
        Log.d("TAG", "type:" + type);
        String defualtKey = getDefualtKey();
        if (defualtKey == null || "".equals(defualtKey)) {
            return getConverter().converterData(getBody(), type);
        }
        return getConverter().converterData(getData(getDefualtKey()), type);
    }

    @Override
    public String getData(String key) {
        if (key == null || "".equals(key)) {
            return getBody();
        }
        return getConverter().converterKey(key, getBody());
    }

    @Override
    public <T> T getData(String key, Type type) {
        return getConverter().converterData(getData(key), type);
    }

}
