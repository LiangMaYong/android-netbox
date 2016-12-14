package com.liangmayong.netbox.defaults;

import android.content.Context;

import com.liangmayong.netbox.interfaces.DefaultNetboxCache;
import com.liangmayong.netbox.netcache.NetCache;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefaultCache extends DefaultNetboxCache {

    @Override
    public void setCache(Context context, String key, String body) {
        NetCache.getInstance(context).put(key, body);
    }

    @Override
    public String getCache(Context context, String key) {
        return NetCache.getInstance(context).getAsString(key);
    }

    @Override
    public void removeCache(Context context, String key) {
        NetCache.getInstance(context).remove(key);
    }

    @Override
    public void clearCache(Context context) {
        NetCache.getInstance(context).clear();
    }
}
