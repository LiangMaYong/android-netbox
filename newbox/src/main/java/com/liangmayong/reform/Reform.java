package com.liangmayong.reform;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.liangmayong.reform.annotation.Converter;
import com.liangmayong.reform.annotation.Interceptor;
import com.liangmayong.reform.errors.ReformError;
import com.liangmayong.reform.errors.ReformUnkownError;
import com.liangmayong.reform.interfaces.OnReformListener;
import com.liangmayong.reform.interfaces.ReformConverter;
import com.liangmayong.reform.interfaces.ReformInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reform
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class Reform {

    // debug
    private static boolean DEBUG = true;
    // debugable
    private static boolean DEBUGABLE = true;
    // reformMap
    private static Map<String, Reform> reformMap = new HashMap<String, Reform>();
    // reformModuleMap
    private static Map<String, ReformModule> reformModuleMap = new HashMap<String, ReformModule>();

    /**
     * autoDebugable
     *
     * @param context context
     * @return true or false
     */
    private static void autoDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            DEBUGABLE = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            DEBUGABLE = true;
        }
    }

    /**
     * setDebug
     *
     * @param debug debug
     */
    public static void setDebug(boolean debug) {
        Reform.DEBUG = debug;
    }

    /**
     * isDebug
     *
     * @return debug
     */
    public static boolean isDebug() {
        return DEBUG && DEBUGABLE;
    }

    /**
     * getModuleInstance
     *
     * @param clazz clazz
     * @param <T>   module type
     * @return reform module
     */
    public static final <T extends ReformModule> T getModuleInstance(Class<T> clazz) {
        if (clazz == null) {
            // clazz == null
            ReformLog.e("module class == null");
            return null;
        }
        String key = clazz.getName();
        if (reformModuleMap.containsKey(key)) {
            return (T) reformModuleMap.get(key);
        }
        try {
            Constructor<T> classConstructor = clazz.getDeclaredConstructor();
            classConstructor.setAccessible(true);
            T t = (T) classConstructor.newInstance();
            Class<? extends ReformInterceptor> interceptorClass = t.getInterceptorType();
            if (interceptorClass == null) {
                Interceptor interceptor = clazz.getAnnotation(Interceptor.class);
                if (interceptor == null) {
                    // must set interceptor
                    ReformLog.e("module must set interceptor");
                    return t;
                }
                interceptorClass = interceptor.value();
                if (interceptorClass == ReformInterceptor.class) {
                    // interceptor must extends ReformInterceptor
                    ReformLog.e("interceptor must extends ReformInterceptor");
                    return t;
                }
            }
            Reform reform = interceptor(interceptorClass);
            ReformConverter reformConverter = null;
            Class<? extends ReformConverter> converterType = t.getConverterType();
            if (converterType == null) {
                Converter converter = clazz.getAnnotation(Converter.class);
                if (converter != null) {
                    try {
                        converterType = converter.value();
                    } catch (Exception e) {
                    }
                }
            }
            if (converterType != null) {
                reformConverter = converterType.newInstance();
            }
            try {
                method(clazz, t, "setReform", Reform.class).invoke(reform);
            } catch (Exception e) {
                ReformLog.e("set reform error", e);
            }
            try {
                method(clazz, t, "setConverter", ReformConverter.class).invoke(reformConverter);
            } catch (Exception e) {
                ReformLog.e("set converter error", e);
            }
            reformModuleMap.put(key, t);
            return t;
        } catch (Exception e) {
            ReformLog.e("create module error", e);
        }
        return null;
    }

    /**
     * interceptor
     *
     * @param interceptorClass interceptorClass
     * @return reform
     */
    private static Reform interceptor(Class<? extends ReformInterceptor> interceptorClass) {
        if (interceptorClass == null) {
            return null;
        }
        String key = interceptorClass.getName();
        if (reformMap.containsKey(key)) {
            Reform reform = reformMap.get(key);
            return reform;
        } else {
            try {
                Reform reform = new Reform(interceptorClass);
                reformMap.put(key, reform);
                return reform;
            } catch (Exception e) {
                return null;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // reform interceptor
    private ReformInterceptor interceptor;
    // reformParameterList
    private List<ReformParameter> reformParameterList = new ArrayList<ReformParameter>();

    /**
     * Reform
     *
     * @param interceptorClass interceptorClass
     * @throws Exception Exception
     */
    private Reform(Class<? extends ReformInterceptor> interceptorClass) throws Exception {
        this.interceptor = interceptorClass.newInstance();
    }

    /**
     * getInterceptor
     *
     * @return interceptor
     */
    protected ReformInterceptor getInterceptor() {
        return interceptor;
    }

    /**
     * enqueue
     *
     * @param context   context
     * @param converter converter
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     */
    protected void enqueue(final Context context, final ReformConverter converter, final String url, final ReformParameter parameter,
                           final OnReformListener listener) {
        if (context == null) {
            listener.onFailure(new ReformUnkownError("Context is null"));
        }
        Reform.autoDebugable(context);
        if (parameter == null) {
            listener.onFailure(new ReformUnkownError("ReformParameter is null"));
            return;
        }
        if (getInterceptor() == null && (parameter == null || parameter.getInterceptor() == null)) {
            listener.onFailure(new ReformUnkownError("ReformInterceptor is null"));
            return;
        }
        reformParameterList.add(parameter);
        ReformInterceptor interceptor = parameter.getInterceptor();
        if (interceptor == null) {
            interceptor = getInterceptor();
        }
        try {
            method(ReformParameter.class, parameter, "setInterceptorCommonHeaders", Map.class).invoke(interceptor.getCommonHeaders());
        } catch (Exception e) {
        }
        try {
            method(ReformParameter.class, parameter, "setInterceptorCommonParams", Map.class).invoke(interceptor.getCommonParams());
        } catch (Exception e) {
        }
        parameter.setRequestTime(System.currentTimeMillis());

        ReformResponse response = null;
        if (parameter.isCacheEnable() && parameter.isPriorityLocalCache()) {
            //local cache
            try {
                response = interceptor.getCache(context, url, parameter);
            } catch (ReformError reformError) {
            }
        }
        if (response != null) {
            //local cache
            if (listener != null) {
                if (parameter != null && parameter.getConverter() != null) {
                    response.setConverter(parameter.getConverter());
                } else {
                    response.setConverter(converter);
                }
                response.setRequestTime(parameter.getRequestTime());
                response.setResponseTime(System.currentTimeMillis());
                response.setFormCache(true);
                listener.onResponse(response);
            }
            reformParameterList.remove(parameter);
            ReformLog.d("-----------------Reform-----------------");
            ReformLog.d("RequestType:enqueue");
            ReformLog.d("onResponse-Url:" + response.getUrl());
            ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
            ReformLog.d("onResponse-Params:" + parameter.getParams());
            ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
            if (response.isFormCache()) {
                ReformLog.d("onResponse-FormCache:" + response.isFormCache());
            }
            ReformLog.d("onResponse-Body:" + response.getBody());
            ReformLog.d("----------------------------------------");
            return;
        }
        final ReformInterceptor finalInterceptor = interceptor;
        interceptor.enqueue(context, url, parameter, new OnReformListener() {
            @Override
            public void onResponse(ReformResponse response) {
                //net
                if (listener != null) {
                    if (parameter != null && parameter.getConverter() != null) {
                        response.setConverter(parameter.getConverter());
                    } else {
                        response.setConverter(converter);
                    }
                    response.setRequestTime(parameter.getRequestTime());
                    response.setResponseTime(System.currentTimeMillis());
                    listener.onResponse(response);
                }
                reformParameterList.remove(parameter);
                ReformLog.d("-----------------Reform-----------------");
                ReformLog.d("RequestType:enqueue");
                ReformLog.d("onResponse-Url:" + response.getUrl());
                ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
                ReformLog.d("onResponse-Params:" + parameter.getParams());
                ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
                if (response.isFormCache()) {
                    ReformLog.d("onResponse-FormCache:" + response.isFormCache());
                }
                ReformLog.d("onResponse-Body:" + response.getBody());
                ReformLog.d("----------------------------------------");
            }

            @Override
            public void onFailure(ReformError reformError) {

                if (parameter.isCacheEnable() && !parameter.isPriorityLocalCache()) {
                    //local cache
                    try {
                        ReformResponse response = finalInterceptor.getCache(context, url, parameter);
                        if (listener != null) {
                            if (parameter != null && parameter.getConverter() != null) {
                                response.setConverter(parameter.getConverter());
                            } else {
                                response.setConverter(converter);
                            }
                            response.setRequestTime(parameter.getRequestTime());
                            response.setResponseTime(System.currentTimeMillis());
                            response.setFormCache(true);
                            listener.onResponse(response);
                        }
                        reformParameterList.remove(parameter);
                        ReformLog.d("-----------------Reform-----------------");
                        ReformLog.d("RequestType:enqueue");
                        ReformLog.d("onResponse-Url:" + response.getUrl());
                        ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
                        ReformLog.d("onResponse-Params:" + parameter.getParams());
                        ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
                        if (response.isFormCache()) {
                            ReformLog.d("onResponse-FormCache:" + response.isFormCache());
                        }
                        ReformLog.d("onResponse-Body:" + response.getBody());
                        ReformLog.d("----------------------------------------");
                        return;
                    } catch (ReformError e) {
                    }
                }
                if (listener != null) {
                    listener.onFailure(reformError);
                }
                reformParameterList.remove(parameter);
                ReformLog.d("-----------------Reform-----------------");
                ReformLog.d("RequestType:enqueue");
                ReformLog.d("onResponse-Url:" + url);
                ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
                ReformLog.d("onResponse-Params:" + parameter.getParams());
                ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
                ReformLog.d("onFailure", reformError);
                ReformLog.d("----------------------------------------");
            }
        });
    }

    /**
     * execute
     *
     * @param context   context
     * @param converter converter
     * @param url       url
     * @param parameter parameter
     * @return Response
     * @throws ReformError error
     */
    protected ReformResponse execute(Context context, ReformConverter converter, String url, ReformParameter parameter) throws ReformError {
        if (context == null) {
            throw new ReformUnkownError("Context is null");
        }
        Reform.autoDebugable(context);
        if (parameter == null) {
            throw new ReformUnkownError("ReformParameter is null");
        }
        if (getInterceptor() == null && parameter.getInterceptor() == null) {
            throw new ReformUnkownError("ReformInterceptor is null");
        }
        ReformInterceptor interceptor = parameter.getInterceptor();
        if (interceptor == null) {
            interceptor = getInterceptor();
        }
        reformParameterList.add(parameter);
        try {
            method(ReformParameter.class, parameter, "setInterceptorCommonHeaders", Map.class).invoke(interceptor.getCommonHeaders());
        } catch (Exception e) {
        }
        try {
            method(ReformParameter.class, parameter, "setInterceptorCommonParams", Map.class).invoke(interceptor.getCommonParams());
        } catch (Exception e) {
        }
        parameter.setRequestTime(System.currentTimeMillis());
        try {
            ReformResponse response = null;
            if (parameter.isCacheEnable() && parameter.isPriorityLocalCache()) {
                //local cache
                try {
                    response = interceptor.getCache(context, url, parameter);
                    response.setFormCache(true);
                } catch (ReformError reformError) {
                }
            }
            if (response == null) {
                try {
                    //net
                    response = interceptor.execute(context, url, parameter);
                } catch (ReformError reformError) {
                    if (parameter.isCacheEnable() && !parameter.isPriorityLocalCache()) {
                        //local cache
                        try {
                            response = interceptor.getCache(context, url, parameter);
                            response.setFormCache(true);
                        } catch (ReformError e) {
                            throw e;
                        }
                    }
                }
            }
            if (response != null) {
                ReformLog.d("-----------------Reform-----------------");
                ReformLog.d("RequestType:execute");
                ReformLog.d("onResponse-Url:" + response.getUrl());
                ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
                ReformLog.d("onResponse-Params:" + parameter.getParams());
                ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
                if (response.isFormCache()) {
                    ReformLog.d("onResponse-FormCache:" + response.isFormCache());
                }
                ReformLog.d("onResponse-Body:" + response.getBody());
                ReformLog.d("----------------------------------------");
                if (parameter != null && parameter.getConverter() != null) {
                    response.setConverter(parameter.getConverter());
                } else {
                    response.setConverter(converter);
                }
            }
            reformParameterList.remove(parameter);
            response.setRequestTime(parameter.getRequestTime());
            response.setResponseTime(System.currentTimeMillis());
            return response;
        } catch (ReformError reformError) {
            reformParameterList.remove(parameter);
            ReformLog.d("-----------------Reform-----------------");
            ReformLog.d("RequestType:execute");
            ReformLog.d("onResponse-Url:" + url);
            ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
            ReformLog.d("onResponse-Params:" + parameter.getParams());
            ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
            ReformLog.d("onFailure", reformError);
            ReformLog.d("----------------------------------------");
            throw reformError;
        }
    }

    /**
     * getCache
     *
     * @param context   context
     * @param converter converter
     * @param url       url
     * @param parameter parameter
     * @return Response
     * @throws ReformError error
     */
    protected ReformResponse getCache(Context context, ReformConverter converter, String url, ReformParameter parameter) throws ReformError {
        if (context == null) {
            throw new ReformUnkownError("Context is null");
        }
        Reform.autoDebugable(context);
        if (parameter == null) {
            throw new ReformUnkownError("ReformParameter is null");
        }
        if (getInterceptor() == null && parameter.getInterceptor() == null) {
            throw new ReformUnkownError("ReformInterceptor is null");
        }
        ReformInterceptor interceptor = parameter.getInterceptor();
        if (interceptor == null) {
            interceptor = getInterceptor();
        }
        reformParameterList.add(parameter);
        try {
            method(ReformParameter.class, parameter, "setInterceptorCommonHeaders", Map.class).invoke(interceptor.getCommonHeaders());
        } catch (Exception e) {
        }
        try {
            method(ReformParameter.class, parameter, "setInterceptorCommonParams", Map.class).invoke(interceptor.getCommonParams());
        } catch (Exception e) {
        }
        parameter.setRequestTime(System.currentTimeMillis());
        try {
            ReformResponse response = interceptor.getCache(context, url, parameter);
            if (response != null) {
                ReformLog.d("-----------------Reform-----------------");
                ReformLog.d("RequestType:getCache");
                ReformLog.d("onResponse-Url:" + response.getUrl());
                ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
                ReformLog.d("onResponse-Params:" + parameter.getParams());
                ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
                if (response.isFormCache()) {
                    ReformLog.d("onResponse-FormCache:" + response.isFormCache());
                }
                ReformLog.d("onResponse-Body:" + response.getBody());
                ReformLog.d("----------------------------------------");
                if (parameter != null && parameter.getConverter() != null) {
                    response.setConverter(parameter.getConverter());
                } else {
                    response.setConverter(converter);
                }
            }
            reformParameterList.remove(parameter);
            response.setRequestTime(parameter.getRequestTime());
            response.setResponseTime(System.currentTimeMillis());
            return response;
        } catch (ReformError reformError) {
            reformParameterList.remove(parameter);
            ReformLog.d("-----------------Reform-----------------");
            ReformLog.d("RequestType:getCache");
            ReformLog.d("onResponse-Url:" + url);
            ReformLog.d("onResponse-Method:" + parameter.getMethod().name());
            ReformLog.d("onResponse-Params:" + parameter.getParams());
            ReformLog.d("onResponse-Headers:" + parameter.getHeaders());
            ReformLog.d("onFailure", reformError);
            ReformLog.d("----------------------------------------");
            throw reformError;
        }
    }

    /**
     * getBaseUrl
     *
     * @return base url
     */
    protected String getBaseUrl() {
        if (getInterceptor() == null) {
            return "";
        }
        String baseUrl = getInterceptor().getBaseUrl();
        if (baseUrl == null) {
            baseUrl = "";
        }
        return baseUrl;
    }

    /**
     * destroy
     *
     * @param context context
     */
    protected void destroy(Context context) {
        if (getInterceptor() == null) {
            return;
        }
        getInterceptor().destroy(context);
        for (int i = 0; i < reformParameterList.size(); i++) {
            if (reformParameterList.get(i).getInterceptor() != null) {
                reformParameterList.get(i).getInterceptor().destroy(context);
            }
        }
        reformParameterList.removeAll(reformParameterList);
    }


    /**
     * method
     *
     * @param clazz          clazz
     * @param object         object
     * @param method         method
     * @param parameterTypes parameterTypes
     * @return APMethod
     */
    private static final ReflectMethod method(Class<?> clazz, Object object, String method, Class<?>... parameterTypes) {
        if (object == null) {
            return null;
        }
        return new ReflectMethod(clazz, object, method, parameterTypes);
    }

    /**
     * ReflectMethod
     */
    private static final class ReflectMethod {

        private Method method;
        private Object object;

        private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
            Method method = null;
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    method = clazz.getDeclaredMethod(name, parameterTypes);
                    return method;
                } catch (Exception e) {
                }
            }
            return null;
        }

        public ReflectMethod(Class<?> cls, Object object, String method, Class<?>... parameterTypes) {
            try {
                this.object = object;
                this.method = getDeclaredMethod(cls, method, parameterTypes);
            } catch (Exception e) {
            }
        }

        public Object invoke(Object... args) throws Exception {
            if (method != null) {
                method.setAccessible(true);
                Object object = method.invoke(this.object, args);
                method = null;
                this.object = null;
                return object;
            }
            return null;
        }
    }

    private static class ReformLog {

        private ReformLog() {
        }

        private static final String TAG = "Reform";

        /**
         * Send a DEBUG log message and log the exception.
         *
         * @param msg The message you would like logged.
         */
        public static void d(String msg) {
            if (Reform.isDebug()) {
                Log.d(TAG, msg);
            }
        }

        /**
         * Send a DEBUG log message and log the exception.
         *
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        public static void d(String msg, Throwable tr) {
            if (Reform.isDebug()) {
                Log.d(TAG, msg, tr);
            }
        }

        /**
         * Send a ERROR log message and log the exception.
         *
         * @param msg The message you would like logged.
         */
        public static void e(String msg) {
            if (Reform.isDebug()) {
                Log.e(TAG, msg);
            }
        }

        /**
         * Send a ERROR log message and log the exception.
         *
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        public static void e(String msg, Throwable tr) {
            if (Reform.isDebug()) {
                Log.e(TAG, msg, tr);
            }
        }

        /**
         * Send a INFO log message and log the exception.
         *
         * @param msg The message you would like logged.
         */
        public static void i(String msg) {
            if (Reform.isDebug()) {
                Log.i(TAG, msg);
            }
        }

        /**
         * Send a INFO log message and log the exception.
         *
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        public static void i(String msg, Throwable tr) {
            if (Reform.isDebug()) {
                Log.i(TAG, msg, tr);
            }
        }

        /**
         * Send a VERBOSE log message and log the exception.
         *
         * @param msg The message you would like logged.
         */
        public static void v(String msg) {
            if (Reform.isDebug()) {
                Log.v(TAG, msg);
            }
        }

        /**
         * Send a VERBOSE log message and log the exception.
         *
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        public static void v(String msg, Throwable tr) {
            if (Reform.isDebug()) {
                Log.v(TAG, msg, tr);
            }
        }

        /**
         * Send a WARN log message and log the exception.
         *
         * @param msg The message you would like logged.
         */
        public static void w(String msg) {
            if (Reform.isDebug()) {
                Log.w(TAG, msg);
            }
        }

        /**
         * Send a WARN log message and log the exception.
         *
         * @param msg The message you would like logged.
         * @param tr  An exception to log
         */
        public static void w(String msg, Throwable tr) {
            if (Reform.isDebug()) {
                Log.w(TAG, msg, tr);
            }
        }
    }
}
