package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.params.Request;

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
     * removeCache
     *
     * @param context context
     * @param key     key
     */
    void removeCache(Context context, String key);

    /**
     * clearCache
     *
     * @param context context
     */
    void clearCache(Context context);

    /**
     * cacheSize
     *
     * @return size
     */
    long cacheSize(Context context);

    /**
     * generateKey
     *
     * @param context   context
     * @param parameter parameter
     * @return key
     */
    String generateKey(Context context, Request parameter);
}
