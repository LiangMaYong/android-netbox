package com.liangmayong.netbox.utils;

import android.app.Application;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class NetBoxUtils {


    // application
    private static WeakReference<Application> application = null;

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
            synchronized (NetBoxUtils.class) {
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


    private NetBoxUtils() {
    }

}
