package com.liangmayong.netbox;

import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.netbox.interfaces.NetBoxInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class NetBox {

    private NetBox() {
    }

    private static volatile Method createAction = null;
    // stringNetBoxActionMap
    private static final Map<String, NetBoxAction> stringNetBoxActionMap = new HashMap<String, NetBoxAction>();
    // stringNetBoxConverterMap
    private static final Map<String, NetBoxConverter> stringNetBoxConverterMap = new HashMap<String, NetBoxConverter>();
    // stringNetBoxInterceptorMap
    private static final Map<String, NetBoxInterceptor> stringNetBoxInterceptorMap = new HashMap<String, NetBoxInterceptor>();
    // commonParams
    private static final Map<String, String> commonParams = new HashMap<String, String>();
    // commonHeaders
    private static final Map<String, String> commonHeaders = new HashMap<String, String>();

    /**
     * getActionInstance
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox action
     */
    public static final <T extends NetBoxAction> T getActionInstance(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("the action class must not null");
        }
        String key = clazz.getName();
        if (stringNetBoxActionMap.containsKey(key)) {
            return (T) stringNetBoxActionMap.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T action = constructor.newInstance();
            try {
                if (createAction == null) {
                    createAction = NetBoxAction.class.getDeclaredMethod("create");
                }
                createAction.invoke(action);
            } catch (Exception e) {
            }
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("the " + clazz.getName() + " constructor method must be empty parameters");
        }
    }

    /**
     * getConverterInstance
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox converter
     */
    public static final <T extends NetBoxConverter> T getConverterInstance(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("the converter class must not null");
        }
        String key = clazz.getName();
        if (stringNetBoxConverterMap.containsKey(key)) {
            return (T) stringNetBoxConverterMap.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T converter = constructor.newInstance();
            return converter;
        } catch (Exception e) {
            throw new IllegalArgumentException("the " + clazz.getClass().getName() + " constructor method must be empty parameters");
        }
    }

    /**
     * getInterceptorInstance
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox interceptor
     */
    public static final <T extends NetBoxInterceptor> T getInterceptorInstance(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("the interceptor class must not null");
        }
        String key = clazz.getName();
        if (stringNetBoxInterceptorMap.containsKey(key)) {
            return (T) stringNetBoxInterceptorMap.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("the " + clazz.getClass().getName() + " constructor method must be empty parameters");
        }
    }

    /**
     * getCommonParams
     *
     * @return commonParams
     */
    public static Map<String, String> getCommonParams() {
        return commonParams;
    }

    /**
     * getCommonHeaders
     *
     * @return commonHeaders
     */
    public static Map<String, String> getCommonHeaders() {
        return commonHeaders;
    }
}
