package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.annotations.BindCache;
import com.liangmayong.netbox.annotations.BindConverter;
import com.liangmayong.netbox.annotations.BindDebugable;
import com.liangmayong.netbox.annotations.BindHeaders;
import com.liangmayong.netbox.annotations.BindInterceptor;
import com.liangmayong.netbox.annotations.BindParams;
import com.liangmayong.netbox.annotations.BindURL;
import com.liangmayong.netbox.defualt.DefualtCache;
import com.liangmayong.netbox.defualt.DefualtConverter;
import com.liangmayong.netbox.defualt.DefualtInterceptor;
import com.liangmayong.netbox.interfaces.DefualtNetboxCache;
import com.liangmayong.netbox.interfaces.DefualtNetboxConverter;
import com.liangmayong.netbox.interfaces.DefualtNetboxInterceptor;
import com.liangmayong.netbox.interfaces.Method;
import com.liangmayong.netbox.interfaces.NetboxCache;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxInterceptor;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.utils.NetboxUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
@BindCache(DefualtCache.class)
@BindConverter(DefualtConverter.class)
@BindInterceptor(DefualtInterceptor.class)
public class NetboxServer {

    // debugable
    private boolean debugable = false;
    // isSetDebugable
    private boolean isSetDebugable = false;
    // baseURL
    private String debugURL = "";
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

        // interceptorType
        BindInterceptor interceptor = null;
        for (clazz = getClass(); clazz != Object.class && interceptor == null; clazz = clazz.getSuperclass()) {
            interceptor = clazz.getAnnotation(BindInterceptor.class);
        }
        if (interceptor != null) {
            Class<? extends NetboxInterceptor> interceptorType = interceptor.value();
            if (interceptorType == NetboxInterceptor.class) {
                interceptorType = DefualtNetboxInterceptor.class;
            }
            this.interceptorType = interceptorType;
        }

        // converterType
        BindConverter converter = null;
        for (clazz = getClass(); clazz != Object.class && converter == null; clazz = clazz.getSuperclass()) {
            converter = clazz.getAnnotation(BindConverter.class);
        }
        if (converter != null) {
            Class<? extends NetboxConverter> converterType = converter.value();
            if (converterType == NetboxConverter.class) {
                converterType = DefualtNetboxConverter.class;
            }
            this.converterType = converterType;
        }

        // cacheType
        BindCache cache = null;
        for (clazz = getClass(); clazz != Object.class && cache == null; clazz = clazz.getSuperclass()) {
            cache = clazz.getAnnotation(BindCache.class);
        }
        if (cache != null) {
            Class<? extends NetboxCache> cacheType = cache.value();
            if (cacheType == NetboxCache.class) {
                cacheType = DefualtNetboxCache.class;
            }
            this.cacheType = cacheType;
        }

        // baseURL
        BindURL url = null;
        for (clazz = getClass(); clazz != Object.class && url == null; clazz = clazz.getSuperclass()) {
            url = clazz.getAnnotation(BindURL.class);
        }
        if (url != null) {
            String baseURL = url.value();
            String debugURL = url.debug();
            if (baseURL == null || "".equals(baseURL)) {
                baseURL = "";
            }
            if (debugURL == null || "".equals(debugURL)) {
                debugURL = "";
            }
            this.debugURL = debugURL;
            this.baseURL = baseURL;
        }

        // debugable
        BindDebugable debug = null;
        for (clazz = getClass(); clazz != Object.class && debug == null; clazz = clazz.getSuperclass()) {
            debug = clazz.getAnnotation(BindDebugable.class);
        }
        if (debug != null) {
            this.debugable = debug.value();
            isSetDebugable = true;
        }

        BindParams bindParam = null;
        for (clazz = getClass(); clazz != Object.class && bindParam == null; clazz = clazz.getSuperclass()) {
            bindParam = clazz.getAnnotation(BindParams.class);
        }
        if (bindParam != null) {
            Map<String, String> params = new HashMap<String, String>();
            String[] keys = bindParam.key();
            String[] values = bindParam.value();
            for (int i = 0; i < keys.length; i++) {
                if (values.length > i) {
                    params.put(keys[i], values[i]);
                }
            }
            config().putParams(params);
        }

        BindHeaders bindHeader = null;
        for (clazz = getClass(); clazz != Object.class && bindHeader == null; clazz = clazz.getSuperclass()) {
            bindHeader = clazz.getAnnotation(BindHeaders.class);
        }
        if (bindHeader != null) {
            Map<String, String> headers = new HashMap<String, String>();
            String[] keys = bindHeader.key();
            String[] values = bindHeader.value();
            for (int i = 0; i < keys.length; i++) {
                if (values.length > i) {
                    headers.put(keys[i], values[i]);
                }
            }
            config().putHeaders(headers);
        }
    }

    /**
     * generateBaseUrl
     *
     * @return base url
     */
    protected String generateBaseURL() {
        if (baseURL == null) {
            return "";
        }
        return baseURL;
    }

    /**
     * isDebugable
     *
     * @return debugable
     */
    public boolean isDebugable() {
        if (isSetDebugable) {
            return debugable;
        }
        return NetboxUtils.isDebugable();
    }

    /**
     * generateURL
     *
     * @return base url
     */
    protected String generateURL() {
        if (isDebugable()) {
            return generateDebugURL();
        }
        return generateBaseURL();
    }

    /**
     * generateDebugURL
     *
     * @return base url
     */
    protected String generateDebugURL() {
        if (debugURL == null) {
            return "";
        }
        return debugURL;
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
     * @return action
     */
    public final NetboxPath path() {
        return new NetboxPath(getClass(), "");
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
     * cache
     *
     * @return cache
     */
    public final NetboxCache cache() {
        return Netbox.generateCache(generateCacheType());
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
