package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.params.Parameter;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public interface NetboxCache {
    /**
     * putCache
     *
     * @param context context
     * @param key     key
     * @param body    body
     */
    void putCache(Context context, String key, String body);

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
     * generateKey
     *
     * @param context   context
     * @param parameter parameter
     * @return key
     */
    String generateKey(Context context, Parameter parameter);
}
