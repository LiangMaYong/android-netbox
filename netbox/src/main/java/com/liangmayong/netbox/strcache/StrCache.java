package com.liangmayong.netbox.strcache;

import android.content.Context;

import com.liangmayong.netbox.strcache.disk.DiskManager;
import com.liangmayong.netbox.strcache.interfaces.ICacheInterface;
import com.liangmayong.netbox.strcache.memory.MemoryManager;

/**
 * StrCache
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class StrCache implements ICacheInterface {

    private static Context mContext;

    private static DiskManager mDdiskCacheManager;
    private static volatile StrCache INSTANCE = null;

    public static StrCache getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (StrCache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StrCache();
                }
            }
            mContext = context.getApplicationContext();
        }
        mDdiskCacheManager = DiskManager.getInstance(mContext);
        return INSTANCE;
    }

	/*----------------------------------------------------String-----------------------------------------------------*/

    @Override
    public void put(String key, String value) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, String> memoryCacheManager = (MemoryManager<String, String>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value);

        // Disk
        mDdiskCacheManager.put(key, value);
    }

    @Override
    public void put(String key, String value, int cacheTime, int timeUnit) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, String> memoryCacheManager = (MemoryManager<String, String>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value, cacheTime, timeUnit);

        // Disk
        mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
    }

    @Override
    public String getAsString(String key) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, String> memoryCacheManager = (MemoryManager<String, String>) MemoryManager
                .getInstance();
        String result = memoryCacheManager.getAsString(key);
        if (result != null) {
            return result;
        }

        // Disk
        result = mDdiskCacheManager.getAsString(key);

        return result;
    }

    @Override
    public boolean remove(String key) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, ?> memoryCacheManager = (MemoryManager<String, ?>) MemoryManager
                .getInstance();
        memoryCacheManager.remove(key);
        // Disk
        return mDdiskCacheManager.remove(key);
    }

    @Override
    public void clear() {

        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, ?> memoryCacheManager = (MemoryManager<String, ?>) MemoryManager
                .getInstance();
        memoryCacheManager.clear();

        // Disk
        mDdiskCacheManager.clear();
    }

    @Override
    public long size() {
        MemoryManager<String, ?> memoryCacheManager = (MemoryManager<String, ?>) MemoryManager
                .getInstance();
        long size = memoryCacheManager.size() + mDdiskCacheManager.size();
        return size;
    }
}
