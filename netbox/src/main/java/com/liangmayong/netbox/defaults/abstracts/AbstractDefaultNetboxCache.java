package com.liangmayong.netbox.defaults.abstracts;

import android.content.Context;

import com.liangmayong.netbox.interfaces.NetboxCache;
import com.liangmayong.netbox.params.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class AbstractDefaultNetboxCache implements NetboxCache {

    @Override
    public void setCache(Context context, String key, String body) {
    }

    @Override
    public String getCache(Context context, String key) {
        return null;
    }

    @Override
    public void deleteCache(Context context, String key) {
    }

    @Override
    public void deleteAll(Context context) {
    }

    @Override
    public long getSize(Context context) {
        return 0;
    }

    @Override
    public Map<String, String> getCaches(Context context) {
        return new HashMap<String, String>();
    }

    @Override
    public String generateKey(Context context, Request request) {
        return null;
    }

}
