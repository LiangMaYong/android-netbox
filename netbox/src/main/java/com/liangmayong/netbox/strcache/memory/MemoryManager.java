package com.liangmayong.netbox.strcache.memory;

import com.liangmayong.netbox.strcache.interfaces.ICacheInterface;
import com.liangmayong.netbox.strcache.utils.CacheHelper;

import org.json.JSONObject;

/**
 * MemoryManager
 *
 * @param <KeyType>
 * @param <ValueType>
 * @author LiangMaYong
 * @version 1.0
 */
public class MemoryManager<KeyType, ValueType> implements ICacheInterface {

    public static MemoryManager<?, ?> mMemoryCacheManager;

    private MemoryLruCache<KeyType, ValueType> mMemoryCache;

    private int cacheSize = (int) Runtime.getRuntime().maxMemory() / 8;

	/*
     * ====================================================================
	 * Constructor
	 * ====================================================================
	 */

    private MemoryManager() {
        init();
    }

    private void init() {
        if (mMemoryCache == null) {
            mMemoryCache = new MemoryLruCache<KeyType, ValueType>(cacheSize) {
                @Override
                protected int sizeOf(KeyType key, ValueType value) {
                    return super.sizeOf(key, value);
                }
            };
        }
    }

    public static <K, V> MemoryManager<?, ?> getInstance() {
        if (mMemoryCacheManager == null) {
            mMemoryCacheManager = new MemoryManager<K, V>();
        }
        mMemoryCacheManager.init();
        return mMemoryCacheManager;
    }

	/*
     * ========================================================================
	 * Public Mod
	 * ========================================================================
	 */

    /**
     * Set the data to cache
     *
     * @param key
     * @param value
     */
    public void putDataToMemory(KeyType key, ValueType value) {
        if (value instanceof String) {

        } else if (value instanceof JSONObject) {

        }
        mMemoryCache.put(key, value);
    }

    /**
     * Read the data from cache
     *
     * @param key
     * @return
     */
    public ValueType getDataFromMemory(KeyType key) {
        ValueType valueType = mMemoryCache.get(key);
        return valueType;
    }

    /**
     * Remove the data from cache
     *
     * @param key
     */
    public void removeDataFromMemory(KeyType key) {
        if (getDataFromMemory(key) != null) {
            mMemoryCache.remove(key);
        }
    }

    private String checkCacheExpiredWithString(String key, String result) {
        if (result != null) {
            if (!CacheHelper.isExpired(result)) { // unexpired
                String originalResult = CacheHelper.clearDateInfo(result);
                return originalResult;
            } else {
                remove(key);
                return null;
            }
        } else {
            return result;
        }
    }

	/*
     * ========================================================================
	 * Override ICache
	 * ========================================================================
	 */

	/*----------------------------------------------------String-----------------------------------------------------*/

    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, String value) {
        ((MemoryManager<String, String>) getInstance()).putDataToMemory(key, value);
    }

    @Override
    public void put(String key, String value, int cacheTime, int timeUnit) {
        int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
        put(key, CacheHelper.convertStringWithDate(expirationTime, value));
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getAsString(String key) {
        String result = ((MemoryManager<String, String>) getInstance()).getDataFromMemory(key);
        // If the memory cache exists already
        return checkCacheExpiredWithString(key, result);
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(String key) {
        ((MemoryManager<String, String>) getInstance()).removeDataFromMemory(key);
        return true;
    }

    /**
     * clear
     */
    public void clear() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                mMemoryCache.evictAll();
            }
            mMemoryCache = null;
        }
    }

    @Override
    public long size() {
        return mMemoryCache.size();
    }

}
