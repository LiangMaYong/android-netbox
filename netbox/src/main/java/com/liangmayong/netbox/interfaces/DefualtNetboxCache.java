package com.liangmayong.netbox.interfaces;

import com.liangmayong.netbox.interfaces.NetboxCache;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefualtNetboxCache implements NetboxCache {
    @Override
    public void saveCache(String key, String body) {
    }

    @Override
    public String getCache(String key) {
        return null;
    }
}
