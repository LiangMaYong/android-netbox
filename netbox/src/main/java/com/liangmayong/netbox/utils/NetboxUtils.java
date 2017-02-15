package com.liangmayong.netbox.utils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import com.liangmayong.netbox.annotations.Key;
import com.liangmayong.netbox.annotations.KeyFile;
import com.liangmayong.netbox.params.ParamFile;
import com.liangmayong.netbox.params.Request;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public final class NetboxUtils {
    // application
    private static WeakReference<Application> application = null;

    // application
    private static String TAG = "Netbox";
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
     * getApplication
     *
     * @return application
     */
    public static Application getApplication() {
        if (application == null || application.get() == null) {
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
                                    application = new WeakReference<Application>((Application) getApplication.invoke(object));
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return application.get();
    }


    /**
     * isDebugable
     *
     * @return true or false
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public static boolean isDebugable() {
        try {
            ApplicationInfo info = NetboxUtils.getApplication().getApplicationInfo();
            boolean debugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return debugable;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * debugLog
     *
     * @param message   message
     * @param throwable throwable
     */
    public static void debugLog(String message, Throwable throwable) {
        if (isDebugable()) {
            if (throwable == null) {
                Log.d(TAG, message);
            } else {
                Log.d(TAG, message, throwable);
            }
        }
    }

    /**
     * generateCacheKey
     *
     * @return key
     */
    public static String generateCacheKey(Request request) {
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
        return md5(builder.toString());
    }

    /**
     * md5
     *
     * @param str string
     * @return encrypt string
     */
    private final static String md5(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte tmp[] = mdTemp.digest();
            char strs[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strs[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(strs).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * setDebugTAG
     *
     * @param tag tag
     */
    public static void setDebugTAG(String tag) {
        NetboxUtils.TAG = tag;
    }

    /**
     * getMethodParametersByAnnotation
     *
     * @param method method
     * @param args   args
     * @return params
     */
    public static Map<String, String> getMethodParametersByAnnotation(Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        Map<String, String> stringMap = new HashMap<String, String>();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Key) {
                    Key param = (Key) annotation;
                    stringMap.put(param.value(), args[i] + "");
                }
            }
        }
        return stringMap;
    }

    /**
     * getMethodFileParamByAnnotation
     *
     * @param method method
     * @param args   args
     * @return params
     */
    public static Map<String, ParamFile> getMethodFileParamByAnnotation(Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        Map<String, com.liangmayong.netbox.params.ParamFile> stringMap = new HashMap<String, com.liangmayong.netbox.params.ParamFile>();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof KeyFile) {
                    if (args[i] instanceof com.liangmayong.netbox.params.ParamFile) {
                        KeyFile param = (KeyFile) annotation;
                        stringMap.put(param.value(), (com.liangmayong.netbox.params.ParamFile) args[i]);
                    }
                }
            }
        }
        return stringMap;
    }

    private NetboxUtils() {
    }

}
