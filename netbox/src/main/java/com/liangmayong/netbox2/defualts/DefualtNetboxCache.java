package com.liangmayong.netbox2.defualts;

import com.liangmayong.netbox2.interfaces.NetboxCache;

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
