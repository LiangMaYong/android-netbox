package com.liangmayong.netbox.netcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.liangmayong.netbox.netcache.disk.DiskManager;
import com.liangmayong.netbox.netcache.interfaces.ICacheInterface;
import com.liangmayong.netbox.netcache.memory.MemoryManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * NetCache
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class NetCache implements ICacheInterface {

    private static Context mContext;

    private static DiskManager mDdiskCacheManager;
    private static volatile NetCache INSTANCE = null;

    public static NetCache getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NetCache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetCache();
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

	/*----------------------------------------------------JSONObject-----------------------------------------------------*/

    @Override
    public void put(String key, JSONObject jsonObject) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, JSONObject> memoryCacheManager = (MemoryManager<String, JSONObject>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, jsonObject);

        // Disk
        mDdiskCacheManager.put(key, jsonObject);
    }

    @Override
    public void put(String key, JSONObject jsonObject, int cacheTime, int timeUnit) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, JSONObject> memoryCacheManager = (MemoryManager<String, JSONObject>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, jsonObject, cacheTime, timeUnit);

        // Disk
        mDdiskCacheManager.put(key, jsonObject, cacheTime, timeUnit);
    }

    @Override
    public JSONObject getAsJSONObject(String key) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, JSONObject> memoryCacheManager = (MemoryManager<String, JSONObject>) MemoryManager
                .getInstance();
        JSONObject result = memoryCacheManager.getAsJSONObject(key);
        if (result != null) {
            return result;
        }

        // Disk
        result = mDdiskCacheManager.getAsJSONObject(key);
        return result;
    }

	/*----------------------------------------------------JSONArray-----------------------------------------------------*/

    @Override
    public void put(String key, JSONArray jsonArray) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, JSONArray> memoryCacheManager = (MemoryManager<String, JSONArray>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, jsonArray);

        // Disk
        mDdiskCacheManager.put(key, jsonArray);
    }

    @Override
    public void put(String key, JSONArray jsonArray, int cacheTime, int timeUnit) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, JSONArray> memoryCacheManager = (MemoryManager<String, JSONArray>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, jsonArray, cacheTime, timeUnit);

        // Disk
        mDdiskCacheManager.put(key, jsonArray, cacheTime, timeUnit);
    }

    @Override
    public JSONArray getAsJSONArray(String key) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, JSONArray> memoryCacheManager = (MemoryManager<String, JSONArray>) MemoryManager
                .getInstance();
        JSONArray result = memoryCacheManager.getAsJSONArray(key);
        if (result != null) {
            return result;
        }

        // Disk
        result = mDdiskCacheManager.getAsJSONArray(key);
        return result;
    }

	/*----------------------------------------------------byte[ ]-----------------------------------------------------*/

    @Override
    public void put(String key, byte[] value) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, byte[]> memoryCacheManager = (MemoryManager<String, byte[]>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value);

        // Disk
        mDdiskCacheManager.put(key, value);
    }

    @Override
    public void put(String key, byte[] value, int cacheTime, int timeUnit) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, byte[]> memoryCacheManager = (MemoryManager<String, byte[]>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value, cacheTime, timeUnit);

        // Disk
        mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
    }

    @Override
    public byte[] getAsBytes(String key) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, byte[]> memoryCacheManager = (MemoryManager<String, byte[]>) MemoryManager
                .getInstance();
        byte[] result = memoryCacheManager.getAsBytes(key);
        if (result != null) {
            return result;
        }

        // Disk
        result = mDdiskCacheManager.getAsBytes(key);
        return result;
    }

	/*----------------------------------------------------Serializable-----------------------------------------------------*/

    @Override
    public void put(String key, Serializable value) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Serializable> memoryCacheManager = (MemoryManager<String, Serializable>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value);

        // Disk
        mDdiskCacheManager.put(key, value);
    }

    @Override
    public void put(String key, Serializable value, int cacheTime, int timeUnit) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Serializable> memoryCacheManager = (MemoryManager<String, Serializable>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value, cacheTime, timeUnit);

        // Disk
        mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAsSerializable(String key) {
        // Memory
        MemoryManager<String, Serializable> memoryCacheManager = (MemoryManager<String, Serializable>) MemoryManager
                .getInstance();
        Serializable result = memoryCacheManager.getAsSerializable(key);
        if (result != null) {
            return (T) result;
        }
        // Disk
        result = mDdiskCacheManager.getAsSerializable(key);
        return (T) result;
    }

	/*----------------------------------------------------Bitmap-----------------------------------------------------*/

    @Override
    public void put(String key, Bitmap bitmap) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Bitmap> memoryCacheManager = (MemoryManager<String, Bitmap>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, bitmap);

        // Disk
        mDdiskCacheManager.put(key, bitmap);
    }

    @Override
    public void put(String key, Bitmap bitmap, int cacheTime, int timeUnit) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Bitmap> memoryCacheManager = (MemoryManager<String, Bitmap>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, bitmap, cacheTime, timeUnit);

        // Disk
        mDdiskCacheManager.put(key, bitmap, cacheTime, timeUnit);

    }

    @Override
    public Bitmap getAsBitmap(String key) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Bitmap> memoryCacheManager = (MemoryManager<String, Bitmap>) MemoryManager
                .getInstance();
        Bitmap result = memoryCacheManager.getAsBitmap(key);
        if (result != null) {
            return result;
        }

        // Disk
        result = mDdiskCacheManager.getAsBitmap(key);
        return result;
    }

	/*----------------------------------------------------Drawable-----------------------------------------------------*/

    @Override
    public void put(String key, Drawable value) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Drawable> memoryCacheManager = (MemoryManager<String, Drawable>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value);

        // Disk
        mDdiskCacheManager.put(key, value);
    }

    @Override
    public void put(String key, Drawable value, int cacheTime, int timeUnit) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Drawable> memoryCacheManager = (MemoryManager<String, Drawable>) MemoryManager
                .getInstance();
        memoryCacheManager.put(key, value, cacheTime, timeUnit);

        // Disk
        mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
    }

    @Override
    public Drawable getAsDrawable(String key) {
        // Memory
        @SuppressWarnings("unchecked")
        MemoryManager<String, Drawable> memoryCacheManager = (MemoryManager<String, Drawable>) MemoryManager
                .getInstance();
        Drawable result = memoryCacheManager.getAsDrawable(key);
        if (result != null) {
            return result;
        }

        // Disk
        result = mDdiskCacheManager.getAsDrawable(key);
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

}
