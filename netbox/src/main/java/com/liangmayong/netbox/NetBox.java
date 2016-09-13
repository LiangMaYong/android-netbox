package com.liangmayong.netbox;

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
    // stringNetBoxActionMap
    private static final Map<String, NetboxAction> stringNetBoxActionMap = new HashMap<String, NetboxAction>();
    // stringNetBoxConverterMap
    private static final Map<String, NetboxConverter> stringNetBoxConverterMap = new HashMap<String, NetboxConverter>();
    // stringNetBoxInterceptorMap
    private static final Map<String, NetboxInterceptor> stringNetBoxInterceptorMap = new HashMap<String, NetboxInterceptor>();

    /**
     * getActionInstance
     *
     * @param clazz clazz
     * @param <T>   action type
     * @return newbox action
     */
    public static final <T extends NetboxAction> T generateAction(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("The action class must not null");
        }
        String key = clazz.getName();
        if (stringNetBoxActionMap.containsKey(key)) {
            return (T) stringNetBoxActionMap.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            if (constructor.getParameterTypes().length > 0) {
                throw new IllegalArgumentException("The " + clazz.getClass().getName() + " constructor method must be empty parameters");
            }
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
            stringNetBoxActionMap.put(key, action);
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
        if (stringNetBoxConverterMap.containsKey(key)) {
            return (T) stringNetBoxConverterMap.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            if (constructor.getParameterTypes().length > 0) {
                throw new IllegalArgumentException("The " + clazz.getClass().getName() + " constructor method must be empty parameters");
            }
            constructor.setAccessible(true);
            T converter = constructor.newInstance();
            stringNetBoxConverterMap.put(key, converter);
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
        if (stringNetBoxInterceptorMap.containsKey(key)) {
            return (T) stringNetBoxInterceptorMap.get(key);
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            if (constructor.getParameterTypes().length > 0) {
                throw new IllegalArgumentException("The " + clazz.getClass().getName() + " constructor method must be empty parameters");
            }
            constructor.setAccessible(true);
            T interceptor = constructor.newInstance();
            stringNetBoxInterceptorMap.put(key, interceptor);
            return interceptor;
        } catch (Exception e) {
            throw new IllegalArgumentException("The interceptor generation failure:" + clazz.getName(), e);
        }
    }
}
