package com.liangmayong.netbox.defaults;

import android.content.Context;

import com.liangmayong.netbox.defaults.abstracts.AbstractDefaultNetboxCache;
import com.liangmayong.netbox.defaults.cache.DatabaseCache;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefaultDatabaseCache extends AbstractDefaultNetboxCache {

    @Override
    public void setCache(Context context, String key, String body) {
        DatabaseCache.getInstance().setCache(context, key, body);
    }

    @Override
    public String getCache(Context context, String key) {
        return DatabaseCache.getInstance().getCache(context, key);
    }

    @Override
    public void deleteCache(Context context, String key) {
        DatabaseCache.getInstance().deleteCache(context, key);
    }

    @Override
    public void deleteAll(Context context) {
        DatabaseCache.getInstance().deleteAll(context);
    }

    @Override
    public long getSize(Context context) {
        return DatabaseCache.getInstance().getCount(context);
    }

    @Override
    public Map<String, String> getCaches(Context context) {
        return DatabaseCache.getInstance().getCaches(context);
    }
}
