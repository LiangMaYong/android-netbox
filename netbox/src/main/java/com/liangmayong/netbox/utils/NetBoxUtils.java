package com.liangmayong.netbox.utils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class NetboxUtils {
    // application
    private static WeakReference<Application> application = null;

    // application
    private static String TAG = "Netbox";

    /**
     * parseUrl
     *
     * @param baseUrl baseUrl
     * @param url     url
     * @return newUrl
     */
    public static String parseUrl(String baseUrl, String url) {
        if (baseUrl == null) {
            baseUrl = "";
        }
        if (url == null) {
            url = "";
        }
        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://")) {
            return url;
        } else if (url.startsWith("/")) {
            return parseHostURL(baseUrl) + url;
        } else if (url.startsWith("./")) {
            int subIndex = 1;
            if (baseUrl.endsWith("/")) {
                subIndex = 2;
            }
            return baseUrl + url.substring(subIndex);
        } else {
            return baseUrl + url;
        }
    }

    /**
     * parseHostURL
     *
     * @param url url
     * @return hostUrl
     */
    public static String parseHostURL(String url) {
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
     * getGenericClass
     *
     * @param target target
     * @param index  index
     * @return class
     */
    public static Class<?> getGenericClass(Object target, int index) {
        if (target == null)
            return null;
        Class<?> clazz = null;
        Type t = target.getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) t).getActualTypeArguments();
            clazz = (Class<?>) types[index];
        }
        return clazz;
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
    public static String generateCacheKey(String url, Map<String, String> params, Map<String, String> headers) {
        if (url == null)
            url = "";
        StringBuilder builder = new StringBuilder("@" + url);
        if (params != null) {
            builder.append(params.toString());
        }
        if (headers != null) {
            builder.append(headers.toString());
        }
        return md5(builder.toString());
    }

    /**
     * md5
     *
     * @param str string
     * @return encrypt string
     */
    public final static String md5(String str) {
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

    private NetboxUtils() {
    }

}
