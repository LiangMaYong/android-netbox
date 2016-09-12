package com.liangmayong.netbox.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class NetBoxUtils {

    private NetBoxUtils() {
    }

    /**
     * parseUrl
     *
     * @param baseUrl baseUrl
     * @param url     url
     * @return newUrl
     */
    public static String parseUrl(String baseUrl, String url) {
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
}
