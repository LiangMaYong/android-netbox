package com.liangmayong.netbox.defaults;

import android.content.Context;

import com.liangmayong.netbox.interfaces.DefaultNetboxCache;
import com.liangmayong.netbox.strcache.StrCache;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefaultCache extends DefaultNetboxCache {

    @Override
    public void setCache(Context context, String key, String body) {
        StrCache.getInstance(context).put(key, body);
    }

    @Override
    public String getCache(Context context, String key) {
        return StrCache.getInstance(context).getAsString(key);
    }

    @Override
    public void removeCache(Context context, String key) {
        StrCache.getInstance(context).remove(key);
    }

    @Override
    public void clearCache(Context context) {
        StrCache.getInstance(context).clear();
    }

    @Override
    public long cacheSize(Context context) {
        return StrCache.getInstance(context).size();
    }
}
