package com.liangmayong.netbox2;

import android.content.Context;

import com.liangmayong.netbox2.annotations.BaseURL;
import com.liangmayong.netbox2.annotations.Cache;
import com.liangmayong.netbox2.annotations.Converter;
import com.liangmayong.netbox2.annotations.Interceptor;
import com.liangmayong.netbox2.defualts.DefualtNetboxCache;
import com.liangmayong.netbox2.defualts.DefualtNetboxConverter;
import com.liangmayong.netbox2.defualts.DefualtNetboxInterceptor;
import com.liangmayong.netbox2.interfaces.NetboxCache;
import com.liangmayong.netbox2.interfaces.NetboxConverter;
import com.liangmayong.netbox2.interfaces.NetboxInterceptor;
import com.liangmayong.netbox2.response.Response;
import com.liangmayong.netbox2.throwables.NetboxError;
import com.liangmayong.netbox2.utils.NetboxUtils;

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
        Interceptor interceptor = getClass().getAnnotation(Interceptor.class);
        if (interceptor != null) {
            Class<? extends NetboxInterceptor> interceptorType = interceptor.value();
            if (interceptorType == NetboxInterceptor.class) {
                interceptorType = DefualtNetboxInterceptor.class;
            }
            this.interceptorType = interceptorType;
        }
        Converter converter = getClass().getAnnotation(Converter.class);
        if (converter != null) {
            Class<? extends NetboxConverter> converterType = converter.value();
            if (converterType == NetboxConverter.class) {
                converterType = DefualtNetboxConverter.class;
            }
            this.converterType = converterType;
        }
        Cache cache = getClass().getAnnotation(Cache.class);
        if (cache != null) {
            Class<? extends NetboxCache> cacheType = cache.value();
            if (cacheType == NetboxCache.class) {
                cacheType = DefualtNetboxCache.class;
            }
            this.cacheType = cacheType;
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
    public NetboxPath path(String path) {
        return new NetboxPath(getClass(), path);
    }

    /**
     * path
     *
     * @param path path
     * @return call
     */
    public NetboxPath path(StringBuilder path) {
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
    public NetboxConfig config() {
        return NetboxConfig.getInstance(getClass());
    }

    /**
     * destroy
     *
     * @param context context
     */
    public void destroy(Context context) {
        Netbox.generateInterceptor(generateInterceptorType()).destroyRequest(context);
    }

    public void handleResponse(Response response) {
        if (NetboxUtils.isDebugable())
            NetboxUtils.debugLog("onResponse url:" + response.getUrl() + "\n" + "body:" + response.getBody(), null);
    }

    public void handleFailure(NetboxError error) {
        if (NetboxUtils.isDebugable())
            NetboxUtils.debugLog("onFailure:", error);
    }
}
