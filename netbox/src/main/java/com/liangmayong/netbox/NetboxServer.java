package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.annotations.BaseURL;
import com.liangmayong.netbox.annotations.Cache;
import com.liangmayong.netbox.annotations.Converter;
import com.liangmayong.netbox.annotations.Interceptor;
import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.defualts.DefualtNetboxCache;
import com.liangmayong.netbox.defualts.DefualtNetboxConverter;
import com.liangmayong.netbox.defualts.DefualtNetboxInterceptor;
import com.liangmayong.netbox.interfaces.NetboxCache;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxInterceptor;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

/**
 * Created by liangmayong on 2016/9/12.
 */

public class NetboxServer {

    // baseURL
    private String baseURL = "";
    // interceptorType
    private Class<? extends NetboxInterceptor> interceptorType = DefualtNetboxInterceptor.class;
    // converterType
    private Class<? extends NetboxConverter> converterType = DefualtNetboxConverter.class;
    // cacheType
    private Class<? extends NetboxCache> cacheType = DefualtNetboxCache.class;

    // generateAction
    protected final void generateAction() {
        Class<?> clazz = null;
        Interceptor interceptor = null;
        for (clazz = getClass(); clazz != Object.class && interceptor == null; clazz = clazz.getSuperclass()) {
            interceptor = clazz.getAnnotation(Interceptor.class);
        }
        if (interceptor != null) {
            Class<? extends NetboxInterceptor> interceptorType = interceptor.value();
            if (interceptorType == NetboxInterceptor.class) {
                interceptorType = DefualtNetboxInterceptor.class;
            }
            this.interceptorType = interceptorType;
        }
        Converter converter = null;
        for (clazz = getClass(); clazz != Object.class && converter == null; clazz = clazz.getSuperclass()) {
            converter = clazz.getAnnotation(Converter.class);
        }
        if (converter != null) {
            Class<? extends NetboxConverter> converterType = converter.value();
            if (converterType == NetboxConverter.class) {
                converterType = DefualtNetboxConverter.class;
            }
            this.converterType = converterType;
        }
        Cache cache = null;
        for (clazz = getClass(); clazz != Object.class && cache == null; clazz = clazz.getSuperclass()) {
            cache = clazz.getAnnotation(Cache.class);
        }
        if (cache != null) {
            Class<? extends NetboxCache> cacheType = cache.value();
            if (cacheType == NetboxCache.class) {
                cacheType = DefualtNetboxCache.class;
            }
            this.cacheType = cacheType;
        }
        BaseURL url = null;
        for (clazz = getClass(); clazz != Object.class && url == null; clazz = clazz.getSuperclass()) {
            url = clazz.getAnnotation(BaseURL.class);
        }
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
        if (baseURL == null) {
            return "";
        }
        return baseURL;
    }

    /**
     * generateConverterType
     *
     * @return converterType
     */
    protected Class<? extends NetboxConverter> generateConverterType() {
        if (converterType == null) {
            return DefualtNetboxConverter.class;
        }
        return converterType;
    }

    /**
     * generateInterceptorType
     *
     * @return interceptorType
     */
    protected Class<? extends NetboxInterceptor> generateInterceptorType() {
        if (interceptorType == null) {
            return DefualtNetboxInterceptor.class;
        }
        return interceptorType;
    }

    /**
     * generateCacheType
     *
     * @return cacheType
     */
    protected Class<? extends NetboxCache> generateCacheType() {
        if (cacheType == null) {
            return DefualtNetboxCache.class;
        }
        return cacheType;
    }

    /**
     * path
     *
     * @param path path
     * @return action
     */
    public final NetboxPath path(String path) {
        return new NetboxPath(getClass(), path);
    }

    /**
     * path
     *
     * @param path path
     * @return call
     */
    public final NetboxPath path(StringBuilder path) {
        if (path == null) {
            return new NetboxPath(getClass(), "");
        }
        return new NetboxPath(getClass(), path.toString());
    }

    /**
     * config
     *
     * @return config
     */
    public final NetboxConfig config() {
        return NetboxConfig.getInstance(getClass());
    }

    /**
     * destroy
     *
     * @param context context
     */
    public final void destroy(Context context) {
        Netbox.generateInterceptor(generateInterceptorType()).destroyRequest(context);
    }

    /**
     * handleResponse
     *
     * @param response response
     * @return flag
     */
    protected boolean handleResponse(Response response) {
        return false;
    }

    /**
     * handleFailure
     *
     * @param error error
     * @return flag
     */
    protected boolean handleFailure(NetboxError error) {
        return false;
    }

    /**
     * handleURL
     *
     * @param baseUrl baseUrl
     * @param path    path
     * @param method  method
     * @return url
     */
    protected String handleURL(String baseUrl, String path, Method method) {
        return null;
    }
}
