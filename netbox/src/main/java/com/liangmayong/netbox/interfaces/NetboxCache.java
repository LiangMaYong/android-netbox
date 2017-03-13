package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.params.Request;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public interface NetboxCache {
    /**
     * setCache
     *
     * @param context context
     * @param key     key
     * @param body    body
     */
    void setCache(Context context, String key, String body);

    /**
     * getCache
     *
     * @param context context
     * @param key     key
     * @return body
     */
    String getCache(Context context, String key);

    /**
     * deleteCache
     *
     * @param context context
     * @param key     key
     */
    void deleteCache(Context context, String key);

    /**
     * deleteAll
     *
     * @param context context
     */
    void deleteAll(Context context);

    /**
     * getSize
     *
     * @return size
     */
    long getSize(Context context);

    /**
     * getCaches
     *
     * @param context context
     * @return caches
     */
    Map<String, String> getCaches(Context context);

    /**
     * generateKey
     *
     * @param context context
     * @param request request
     * @return key
     */
    String generateKey(Context context, Request request);
}
