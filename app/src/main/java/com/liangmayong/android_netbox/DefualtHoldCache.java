package com.liangmayong.android_netbox;

import android.content.Context;

import com.liangmayong.holdcache.HoldCache;
import com.liangmayong.netbox.interfaces.DefualtNetboxCache;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefualtHoldCache extends DefualtNetboxCache {

    @Override
    public void putCache(Context context, String key, String body) {
        HoldCache.getInstance(context).put(key, body);
    }

    @Override
    public String getCache(Context context, String key) {
        return HoldCache.getInstance(context).getAsString(key);
    }

    @Override
    public void removeCache(Context context, String key) {
        HoldCache.getInstance(context).remove(key);
    }

    @Override
    public void clearCache(Context context) {
        HoldCache.getInstance(context).clear();
    }
}
