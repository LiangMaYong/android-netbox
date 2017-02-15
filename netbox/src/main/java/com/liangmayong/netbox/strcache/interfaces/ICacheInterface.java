package com.liangmayong.netbox.strcache.interfaces;

/**
 * ICacheInterface
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface ICacheInterface {

	/*
     * ========================================================================
	 * String
	 * ========================================================================
	 */

    void put(String key, String value);

    void put(String key, String value, int cacheTime, int timeUnit);

    String getAsString(String key);


	/*
     * ========================================================================
	 * Other methods
	 * ========================================================================
	 */

    boolean remove(String key);

    void clear();

    long size();
}
