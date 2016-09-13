package com.liangmayong.netbox.response;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.defualts.DefualtNetboxConverter;
import com.liangmayong.netbox.interfaces.INetboxResponse;
import com.liangmayong.netbox.interfaces.NetboxConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class Response implements INetboxResponse {

    // body
    private String mBody = "";
    // url
    private String mUrl = "";
    // defualtKey
    private String mDefualtKey = null;
    // converterTypes
    private Class<? extends NetboxConverter> mConverterType = DefualtNetboxConverter.class;
    // params
    private final Map<String, String> mParams = new HashMap<String, String>();
    // headers
    private final Map<String, String> mHeaders = new HashMap<String, String>();

    public Response(String url, Map<String, String> params, Map<String, String> headers) {
        setUrl(url);
        setParams(params);
        setHeaders(headers);
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
        return getConverter().converter(getDefualtKey(), entityClass, this);
    }

    @Override
    public <T> T getData(String key, Class<T> entityClass) {
        return getConverter().converter(key, entityClass, this);
    }

    @Override
    public <T> List<T> getList(Class<T> entityClass) {
        return getConverter().converterList(getDefualtKey(), entityClass, this);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> entityClass) {
        return getConverter().converterList(key, entityClass, this);
    }
}
