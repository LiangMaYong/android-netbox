package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.params.Request;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefaultNetboxCache implements NetboxCache {

    @Override
    public void setCache(Context context, String key, String body) {
    }

    @Override
    public String getCache(Context context, String key) {
        return null;
    }

    @Override
    public void removeCache(Context context, String key) {
    }

    @Override
    public void clearCache(Context context) {
    }

    @Override
    public long cacheSize(Context context) {
        return 0;
    }

    @Override
    public String generateKey(Context context, Request parameter) {
        return null;
    }

}
