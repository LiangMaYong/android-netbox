package com.liangmayong.netbox2;

import com.liangmayong.netbox2.interfaces.NetboxCache;
import com.liangmayong.netbox2.interfaces.NetboxConverter;
import com.liangmayong.netbox2.interfaces.NetboxInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class Netbox {

    private Netbox() {
    }

    // GENERATE_ACTION_METHOD_NAME
    private static final String GENERATE_ACTION_METHOD_NAME = "generateAction";
    // gGENERATE_ACTION_METHOD
    private static volatile Method GENERATE_ACTION_METHOD = null;
    // STRING_NETBOX_SERVER_MAP
    private static final Map<String, NetboxServer> STRING_NETBOX_SERVER_MAP = new HashMap<String, NetboxServer>();
    // STRING_NETBOX_CONVERTER_MAP
    private static final Map<String, NetboxConverter> STRING_NETBOX_CONVERTER_MAP = new HashMap<String, NetboxConverter>();
    // STRING_NETBOX_CACHE_MAP
    private static final Map<String, NetboxCache> STRING_NETBOX_CACHE_MAP = new HashMap<String, NetboxCache>();
    // STRING_NETBOX_INTERCEPTOR_MAP
    private static final Map<String, NetboxInterceptor> STRING_NETBOX_INTERCEPTOR_MAP = new HashMap<String, NetboxInterceptor>();

    /**
     * server
     *
     * @param serverType serverType
     * @param <T>        serverType
     * @return newbox server
     */
    @SuppressWarnings("unchecked")
    public static final <T extends NetboxServer> T server(Class<T> serverType) {
        if (serverType == null) {
            throw new IllegalArgumentException("The action class must not null");
        }
        String key = serverType.getName();
        if (STRING_NETBOX_SERVER_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_SERVER_MAP.get(key);
        }
        try {
            Constructor<T> constructor = serverType.getDeclaredConstructor();
            constructor.setAccessible(true);
            T action = constructor.newInstance();
            try {
                if (GENERATE_ACTION_METHOD == null) {
                    GENERATE_ACTION_METHOD = NetboxServer.class.getDeclaredMethod(GENERATE_ACTION_METHOD_NAME);
                    GENERATE_ACTION_METHOD.setAccessible(true);
                }
                GENERATE_ACTION_METHOD.invoke(action);
            } catch (Exception e) {
            }
            STRING_NETBOX_SERVER_MAP.put(key, action);
            return action;
        } catch (Exception e) {
            throw new IllegalArgumentException("The action generation failure:" + serverType.getName(), e);
        }
    }

    /**
     * generateConverter
     *
     * @param converterType converterType
     * @param <T>           converterType
     * @return newbox converter
     */
    @SuppressWarnings("unchecked")
    public static final <T extends NetboxConverter> T generateConverter(Class<T> converterType) {
        if (converterType == null) {
            throw new IllegalArgumentException("The converter class must not null");
        }
        String key = converterType.getName();
        if (STRING_NETBOX_CONVERTER_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_CONVERTER_MAP.get(key);
        }
        try {
            Constructor<T> constructor = converterType.getDeclaredConstructor();
            constructor.setAccessible(true);
            T converter = constructor.newInstance();
            STRING_NETBOX_CONVERTER_MAP.put(key, converter);
            return converter;
        } catch (Exception e) {
            throw new IllegalArgumentException("The converter generation failure:" + converterType.getName(), e);
        }
    }

    /**
     * generateInterceptor
     *
     * @param interceptorType interceptorType
     * @param <T>             interceptorType
     * @return newbox interceptor
     */
    @SuppressWarnings("unchecked")
    public static final <T extends NetboxInterceptor> T generateInterceptor(Class<T> interceptorType) {
        if (interceptorType == null) {
            throw new IllegalArgumentException("The interceptor class must not null");
        }
        String key = interceptorType.getName();
        if (STRING_NETBOX_INTERCEPTOR_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_INTERCEPTOR_MAP.get(key);
        }
        try {
            Constructor<T> constructor = interceptorType.getDeclaredConstructor();
            constructor.setAccessible(true);
            T interceptor = constructor.newInstance();
            STRING_NETBOX_INTERCEPTOR_MAP.put(key, interceptor);
            return interceptor;
        } catch (Exception e) {
            throw new IllegalArgumentException("The interceptor generation failure:" + interceptorType.getName(), e);
        }
    }

    /**
     * generateCache
     *
     * @param cacheType cacheType
     * @param <T>       cacheType
     * @return newbox interceptor
     */
    @SuppressWarnings("unchecked")
    public static final <T extends NetboxCache> T generateCache(Class<T> cacheType) {
        if (cacheType == null) {
            throw new IllegalArgumentException("The cache class must not null");
        }
        String key = cacheType.getName();
        if (STRING_NETBOX_CACHE_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_CACHE_MAP.get(key);
        }
        try {
            Constructor<T> constructor = cacheType.getDeclaredConstructor();
            constructor.setAccessible(true);
            T cache = constructor.newInstance();
            STRING_NETBOX_CACHE_MAP.put(key, cache);
            return cache;
        } catch (Exception e) {
            throw new IllegalArgumentException("The cache generation failure:" + cacheType.getName(), e);
        }
    }
}
