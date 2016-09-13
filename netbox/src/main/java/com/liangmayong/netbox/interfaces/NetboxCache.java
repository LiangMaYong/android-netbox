package com.liangmayong.netbox.interfaces;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public interface NetboxCache {
    /**
     * saveCache
     *
     * @param key  key
     * @param body body
     */
    void saveCache(String key, String body);

    /**
     * getCache
     *
     * @param key key
     * @return body
     */
    String getCache(String key);
}
