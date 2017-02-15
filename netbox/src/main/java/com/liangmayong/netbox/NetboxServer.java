package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.annotations.BindCache;
import com.liangmayong.netbox.annotations.BindConverter;
import com.liangmayong.netbox.annotations.BindDebugable;
import com.liangmayong.netbox.annotations.BindHeaders;
import com.liangmayong.netbox.annotations.BindInterceptor;
import com.liangmayong.netbox.annotations.BindParams;
import com.liangmayong.netbox.annotations.BindURL;
import com.liangmayong.netbox.annotations.Headers;
import com.liangmayong.netbox.annotations.Mod;
import com.liangmayong.netbox.annotations.Params;
import com.liangmayong.netbox.annotations.Path;
import com.liangmayong.netbox.interfaces.DefaultNetboxCache;
import com.liangmayong.netbox.interfaces.DefaultNetboxConverter;
import com.liangmayong.netbox.interfaces.DefaultNetboxInterceptor;
import com.liangmayong.netbox.params.Method;
import com.liangmayong.netbox.interfaces.NetboxCache;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.params.Request;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.utils.NetboxUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
@BindCache(DefaultNetboxCache.class)
@BindConverter(DefaultNetboxConverter.class)
@BindInterceptor(DefaultNetboxInterceptor.class)
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
    private Class<? extends NetboxInterceptor> interceptorType = DefaultNetboxInterceptor.class;
    // converterType
    private Class<? extends NetboxConverter> converterType = DefaultNetboxConverter.class;
    // cacheType
    private Class<? extends NetboxCache> cacheType = DefaultNetboxCache.class;

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
                interceptorType = DefaultNetboxInterceptor.class;
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
                converterType = DefaultNetboxConverter.class;
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
                cacheType = DefaultNetboxCache.class;
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

    public <T> T interfaceServer(final Context context, Class<T> serverInterface) {
        if (serverInterface.isInterface()) {
            T instance = (T) Proxy.newProxyInstance(serverInterface.getClassLoader(), new Class[]{serverInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) throws Throwable {
                    if (args.length < 1 || !(args[args.length - 1] instanceof OnNetboxListener)) {
                        throw new IllegalArgumentException("The last parameter must be OnNetboxListener or its inheritance class");
                    }
                    if (method.getReturnType() != void.class) {
                        throw new IllegalArgumentException("The return value must be void");
                    }
                    Mod modAnnot = method.getAnnotation(Mod.class);
                    Method met = Method.GET;
                    if (modAnnot != null) {
                        met = modAnnot.value();
                    }
                    Path pathAnnot = method.getAnnotation(Path.class);
                    String path = "";
                    if (pathAnnot != null) {
                        path = pathAnnot.value();
                    }
                    NetboxPath netboxPath = path(path);
                    String[] paramkeys = new String[0];
                    String[] paramvalues = new String[0];
                    Params paramAnnot = method.getAnnotation(Params.class);
                    if (paramAnnot != null) {
                        paramkeys = paramAnnot.key();
                        paramvalues = paramAnnot.value();
                    }
                    for (int i = 0; i < paramkeys.length; i++) {
                        if (i < paramvalues.length) {
                            netboxPath.param(paramkeys[i], paramvalues[i]);
                        } else {
                            netboxPath.param(paramkeys[i], "");
                        }
                    }
                    String[] headerkeys = new String[0];
                    String[] headervalues = new String[0];
                    Headers headerAnnot = method.getAnnotation(Headers.class);
                    if (headerAnnot != null) {
                        headerkeys = headerAnnot.key();
                        headervalues = headerAnnot.value();
                    }
                    for (int i = 0; i < headerkeys.length; i++) {
                        if (i < headervalues.length) {
                            netboxPath.header(headerkeys[i], headervalues[i]);
                        } else {
                            netboxPath.header(headerkeys[i], "");
                        }
                    }
                    netboxPath.params(NetboxUtils.getMethodParametersByAnnotation(method, args));
                    netboxPath.files(NetboxUtils.getMethodFileParamByAnnotation(method, args));
                    netboxPath.method(met);
                    netboxPath.execute(context, (OnNetboxListener) args[args.length - 1]);
                    return null;
                }
            });
            return instance;
        } else {
            throw new IllegalArgumentException("interfaceServer must is Interfaces");
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
        if (config().isDebugable()) {
            return true;
        }
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
            return DefaultNetboxConverter.class;
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
            return DefaultNetboxInterceptor.class;
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
            return DefaultNetboxCache.class;
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
    protected boolean handleFailure(Request parameter, NetboxError error) {
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
