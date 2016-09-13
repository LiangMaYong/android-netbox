package com.liangmayong.netbox;

import com.liangmayong.netbox.interfaces.NetboxCache;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxInterceptor;

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

    // generate action method name
    private static final String GENERATE_ACTION_METHOD_NAME = "generateAction";
    // generate action method
    private static volatile Method GENERATE_ACTION_METHOD = null;
    // STRING_NETBOX_ACTION_MAP
    private static final Map<String, NetboxAction> STRING_NETBOX_ACTION_MAP = new HashMap<String, NetboxAction>();
    // STRING_NETBOX_CONVERTER_MAP
    private static final Map<String, NetboxConverter> STRING_NETBOX_CONVERTER_MAP = new HashMap<String, NetboxConverter>();
    // STRING_NETBOX_CACHE_MAP
    private static final Map<String, NetboxCache> STRING_NETBOX_CACHE_MAP = new HashMap<String, NetboxCache>();
    // STRING_NETBOX_INTERCEPTOR_MAP
    private static final Map<String, NetboxInterceptor> STRING_NETBOX_INTERCEPTOR_MAP = new HashMap<String, NetboxInterceptor>();

    /**
     * getActionInstance
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox action
     */
    public static final <T extends NetboxAction> T action(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("The action class must not null");
        }
        String key = clazz.getName();
        if (STRING_NETBOX_ACTION_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_ACTION_MAP.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T action = constructor.newInstance();
            try {
                if (GENERATE_ACTION_METHOD == null) {
                    GENERATE_ACTION_METHOD = NetboxAction.class.getDeclaredMethod(GENERATE_ACTION_METHOD_NAME);
                    GENERATE_ACTION_METHOD.setAccessible(true);
                }
                GENERATE_ACTION_METHOD.invoke(action);
            } catch (Exception e) {
            }
            STRING_NETBOX_ACTION_MAP.put(key, action);
            return action;
        } catch (Exception e) {
            throw new IllegalArgumentException("The action generation failure:" + clazz.getName(), e);
        }
    }

    /**
     * generateConverter
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox converter
     */
    public static final <T extends NetboxConverter> T generateConverter(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("The converter class must not null");
        }
        String key = clazz.getName();
        if (STRING_NETBOX_CONVERTER_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_CONVERTER_MAP.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T converter = constructor.newInstance();
            STRING_NETBOX_CONVERTER_MAP.put(key, converter);
            return converter;
        } catch (Exception e) {
            throw new IllegalArgumentException("The converter generation failure:" + clazz.getName(), e);
        }
    }

    /**
     * generateInterceptor
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox interceptor
     */
    public static final <T extends NetboxInterceptor> T generateInterceptor(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("The interceptor class must not null");
        }
        String key = clazz.getName();
        if (STRING_NETBOX_INTERCEPTOR_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_INTERCEPTOR_MAP.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T interceptor = constructor.newInstance();
            STRING_NETBOX_INTERCEPTOR_MAP.put(key, interceptor);
            return interceptor;
        } catch (Exception e) {
            throw new IllegalArgumentException("The interceptor generation failure:" + clazz.getName(), e);
        }
    }

    /**
     * generateCache
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox interceptor
     */
    public static final <T extends NetboxCache> T generateCache(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("The cache class must not null");
        }
        String key = clazz.getName();
        if (STRING_NETBOX_CACHE_MAP.containsKey(key)) {
            return (T) STRING_NETBOX_CACHE_MAP.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T cache = constructor.newInstance();
            STRING_NETBOX_CACHE_MAP.put(key, cache);
            return cache;
        } catch (Exception e) {
            throw new IllegalArgumentException("The cache generation failure:" + clazz.getName(), e);
        }
    }
}
