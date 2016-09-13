package com.liangmayong.netbox;

import com.liangmayong.netbox.annotations.BaseURL;
import com.liangmayong.netbox.annotations.Converter;
import com.liangmayong.netbox.annotations.Interceptor;
import com.liangmayong.netbox.defualts.DefualtNetBoxConverter;
import com.liangmayong.netbox.defualts.DefualtNetBoxInterceptor;
import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.netbox.interfaces.NetBoxInterceptor;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;

/**
 * Created by liangmayong on 2016/9/12.
 */

public class NetBoxAction {

    // baseURL
    private String baseURL = "";
    // interceptorType
    private Class<? extends NetBoxInterceptor> interceptorType = DefualtNetBoxInterceptor.class;
    // converterTypes
    private Class<? extends NetBoxConverter> converterType = DefualtNetBoxConverter.class;

    // generateAction
    protected final void generateAction() {
        Interceptor interceptor = getClass().getAnnotation(Interceptor.class);
        if (interceptor != null) {
            Class<? extends NetBoxInterceptor> interceptorType = interceptor.value();
            if (interceptorType == NetBoxInterceptor.class) {
                interceptorType = DefualtNetBoxInterceptor.class;
            }
            this.interceptorType = interceptorType;
        }
        Converter converter = getClass().getAnnotation(Converter.class);
        if (converter != null) {
            Class<? extends NetBoxConverter> converterType = converter.value();
            if (converterType == NetBoxConverter.class) {
                converterType = DefualtNetBoxConverter.class;
            }
            this.converterType = converterType;
        }
        BaseURL url = getClass().getAnnotation(BaseURL.class);
        if (url != null) {
            String baseURL = url.value();
            if (baseURL == null || "".equals(baseURL)) {
                baseURL = "";
            }
            this.baseURL = baseURL;
        }
    }

    /**
     * generateBaseUrl
     *
     * @return base url
     */
    protected String generateBaseUrl() {
        return baseURL;
    }

    /**
     * generateConverterType
     *
     * @return converterType
     */
    protected Class<? extends NetBoxConverter> generateConverterType() {
        if (converterType == null) {
            return DefualtNetBoxConverter.class;
        }
        return converterType;
    }

    /**
     * generateInterceptorType
     *
     * @return interceptorType
     */
    protected Class<? extends NetBoxInterceptor> generateInterceptorType() {
        if (interceptorType == null) {
            return DefualtNetBoxInterceptor.class;
        }
        return interceptorType;
    }

    /**
     * path
     *
     * @param path path
     * @return action
     */
    public NetBoxPath path(String path) {
        return new NetBoxPath(getClass(), path);
    }

    /**
     * path
     *
     * @param path path
     * @return call
     */
    public NetBoxPath path(StringBuilder path) {
        if (path == null) {
            return new NetBoxPath(getClass(), "");
        }
        return new NetBoxPath(getClass(), path.toString());
    }


    public void handleResponse(Response response) {

    }

    public void handleFailure(NetBoxError error) {

    }
}
