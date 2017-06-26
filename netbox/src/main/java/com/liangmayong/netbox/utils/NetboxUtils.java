package com.liangmayong.netbox.utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.liangmayong.netbox.annotations.Key;
import com.liangmayong.netbox.annotations.KeyFile;
import com.liangmayong.netbox.annotations.KeyMap;
import com.liangmayong.netbox.params.FileParam;
import com.liangmayong.netbox.params.Request;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public final class NetboxUtils {
    // application
    public static final String PATH_REGEX = "{path}";
    public static final String METHOD_REGEX = "{method}";

    /**
     * parseUrl
     *
     * @param baseUrl baseUrl
     * @param path    url
     * @return newUrl
     */
    public static String parseUrl(String baseUrl, String path, com.liangmayong.netbox.params.Method method) {
        if (baseUrl == null) {
            baseUrl = "";
        }
        if (path == null) {
            path = "";
        }
        if (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("ftp://")) {
            return path;
        }
        String reUrl = baseUrl;
        if (reUrl.contains(METHOD_REGEX)) {
            reUrl = replaceURL(reUrl, METHOD_REGEX, method.name().toLowerCase());
        }
        if (reUrl.contains(PATH_REGEX)) {
            reUrl = replaceURL(reUrl, PATH_REGEX, path);
            return reUrl;
        }
        if (path.startsWith("/")) {
            return parseHostURL(reUrl) + path;
        } else if (path.startsWith("./")) {
            int subIndex = 1;
            if (reUrl.endsWith("/")) {
                subIndex = 2;
            }
            return reUrl + path.substring(subIndex);
        } else {
            return reUrl + path;
        }
    }

    /**
     * getGenericType
     *
     * @param target target
     * @param index  index
     * @return class
     */
    public static Type getGenericType(Object target, int index) {
        if (target == null)
            return null;
        Type type = null;
        Type superType = target.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) superType).getActualTypeArguments();
            type = types[index];
        }
        return type;
    }

    /**
     * setField
     *
     * @param clazz     clazz
     * @param object    object
     * @param fieldName fieldName
     * @param value     value
     * @return true or false
     */
    public static boolean setField(Class<?> clazz, Object object, String fieldName, Object value) {
        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        }
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(object, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * getCacheKey
     *
     * @param request request
     * @return key
     */
    public static String getCacheKey(Request request) {
        StringBuilder builder = new StringBuilder(request.getMethod().name() + "@" + request.getUrl());
        if (request.getParams() != null && !request.getParams().isEmpty()) {
            builder.append("@");
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            builder.append("@");
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        if (request.getFiles() != null && !request.getFiles().isEmpty()) {
            builder.append("@");
            for (Map.Entry<String, FileParam> entry : request.getFiles().entrySet()) {
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue().getPath());
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * getMethodParameters
     *
     * @param method method
     * @param args   args
     * @return params
     */
    public static Object[] getMethodParameters(Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        Object[] parameters = new Object[2];
        Map<String, String> stringMap = new HashMap<String, String>();
        Map<String, FileParam> fileMap = new HashMap<String, FileParam>();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            if (args[i] != null) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    Annotation annotation = parameterAnnotations[i][j];
                    if (annotation instanceof Key) {
                        Key param = (Key) annotation;
                        stringMap.put(param.value(), args[i] + "");
                    } else if (annotation instanceof KeyMap) {
                        if (args[i] instanceof Map<?, ?>) {
                            KeyMap param = (KeyMap) annotation;
                            Map<?, ?> argsMap = (Map<?, ?>) args[i];
                            String key = param.value();
                            for (Map.Entry<?, ?> entry : argsMap.entrySet()) {
                                if (key.equals("")) {
                                    stringMap.put(entry.getKey() + "", entry.getValue() + "");
                                } else {
                                    stringMap.put(key + "[" + entry.getKey() + "]", entry.getValue() + "");
                                }
                            }
                        }
                    } else if (annotation instanceof KeyFile) {
                        KeyFile param = (KeyFile) annotation;
                        if (args[i] instanceof FileParam) {
                            fileMap.put(param.value(), (FileParam) args[i]);
                        } else if (args[i] instanceof File) {
                            fileMap.put(param.value(), new FileParam((File) args[i]));
                        }
                    }
                }
            }
        }
        parameters[0] = stringMap;
        parameters[1] = fileMap;
        return parameters;
    }


    private static Application application = null;

    /**
     * getApplication
     *
     * @return application
     */
    public static Application getApplication() {
        if (application == null) {
            synchronized (NetboxUtils.class) {
                if (application == null) {
                    try {
                        Class<?> clazz = Class.forName("android.app.ActivityThread");
                        Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
                        if (currentActivityThread != null) {
                            Object object = currentActivityThread.invoke(null);
                            if (object != null) {
                                Method getApplication = object.getClass().getDeclaredMethod("getApplication");
                                if (getApplication != null) {
                                    application = (Application) getApplication.invoke(object);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return application;
    }

    /**
     * isDebugable
     *
     * @return bool
     */
    public static boolean isDebugable() {
        try {
            Application application = getApplication();
            if (application != null) {
                ApplicationInfo info = NetboxUtils.getApplication().getApplicationInfo();
                boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
                return debugable;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////
    ///////// Private
    /////////////////////////////////////////////////////////////////////////////

    /**
     * replaceURL
     *
     * @param string string
     * @param regex  regex
     * @param rement rement
     * @return new
     */
    private static String replaceURL(String string, String regex, String rement) {
        String newUrl = string;
        if (newUrl.contains(regex)) {
            newUrl = newUrl.replace(regex, rement);
            while (newUrl.contains(regex)) {
                newUrl = newUrl.replace(regex, rement);
            }
        }
        return newUrl;
    }

    /**
     * parseHostURL
     *
     * @param url url
     * @return hostUrl
     */
    private static String parseHostURL(String url) {
        if (url == null) {
            url = "";
        }
        try {
            String httpName = "";
            int indexStart = url.indexOf("://");
            if (indexStart > 0) {
                httpName = url.substring(0, indexStart) + "://";
            }
            return httpName + new URL(url).getHost();
        } catch (MalformedURLException e) {
        }
        return url;
    }

    private NetboxUtils() {
    }

}
