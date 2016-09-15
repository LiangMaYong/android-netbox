package com.liangmayong.netbox.interfaces;

import android.content.Context;

import com.liangmayong.netbox.params.Parameter;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefualtNetboxCache implements NetboxCache {

    @Override
    public void putCache(Context context, String key, String body) {
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
    public String generateKey(Context context, Parameter parameter) {
        return null;
    }

}
