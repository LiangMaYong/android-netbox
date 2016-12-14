package com.liangmayong.netbox.netcache.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.liangmayong.netbox.netcache.interfaces.ICacheInterface;
import com.liangmayong.netbox.netcache.utils.CacheHelper;
import com.liangmayong.netbox.netcache.utils.ImageHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * MemoryManager
 *
 * @param <KeyType>
 * @param <ValueType>
 * @author LiangMaYong
 * @version 1.0
 */
public class MemoryManager<KeyType, ValueType> implements ICacheInterface {

    private static final String TAG = "system.out";

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
	 * Public Method
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
        if (valueType == null) {
            Log.e(TAG, "Not find entry from memory");
        }
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

	/*----------------------------------------------------JSONObject-----------------------------------------------------*/

    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, JSONObject jsonObject) {
        ((MemoryManager<String, String>) getInstance()).putDataToMemory(key, jsonObject.toString());
    }

    @Override
    public void put(String key, JSONObject jsonObject, int cacheTime, int timeUnit) {
        int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
        put(key, CacheHelper.convertStringWithDate(expirationTime, jsonObject.toString()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject getAsJSONObject(String key) {
        String result = ((MemoryManager<String, String>) getInstance()).getDataFromMemory(key);
        String jsonStr = checkCacheExpiredWithString(key, result);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

	/*----------------------------------------------------JSONArray-----------------------------------------------------*/

    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, JSONArray jsonArray) {
        ((MemoryManager<String, String>) getInstance()).putDataToMemory(key, jsonArray.toString());
    }

    @Override
    public void put(String key, JSONArray jsonArray, int cacheTime, int timeUnit) {
        int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
        put(key, CacheHelper.convertStringWithDate(expirationTime, jsonArray.toString()));
    }

    @Override
    public JSONArray getAsJSONArray(String key) {
        @SuppressWarnings("unchecked")
        String result = ((MemoryManager<String, String>) getInstance()).getDataFromMemory(key);
        String jsonStr = checkCacheExpiredWithString(key, result);
        if (jsonStr != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                return jsonArray;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /*----------------------------------------------------byte[ ]-----------------------------------------------------*/
    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, byte[] value) {
        ((MemoryManager<String, byte[]>) getInstance()).putDataToMemory(key, value);
    }

    @Override
    public void put(String key, byte[] value, int cacheTime, int timeUnit) {
        int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
        put(key, CacheHelper.convertByteArrayWithDate(expirationTime, value));
    }

    @Override
    public byte[] getAsBytes(String key) {
        @SuppressWarnings("unchecked")
        byte[] result = ((MemoryManager<String, byte[]>) getInstance()).getDataFromMemory(key);
        return checkCacheExpiredWithByte(key, result);
    }

	/*----------------------------------------------------Serializable-----------------------------------------------------*/

    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, Serializable value) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();

            ((MemoryManager<String, byte[]>) getInstance()).putDataToMemory(key, data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, Serializable value, int cacheTime, int timeUnit) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();

            int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
            ((MemoryManager<String, byte[]>) getInstance()).putDataToMemory(key,
                    CacheHelper.convertByteArrayWithDate(expirationTime, data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAsSerializable(String key) {
        T t = null;

        byte[] bs = ((MemoryManager<String, byte[]>) getInstance()).getDataFromMemory(key);
        byte[] data = checkCacheExpiredWithByte(key, bs);
        if (data == null) {
            return null;
        }
        InputStream is = new ByteArrayInputStream(data);

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);
            t = (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

	/*----------------------------------------------------Bitmap-----------------------------------------------------*/

    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, Bitmap bitmap) {
        ((MemoryManager<String, byte[]>) getInstance()).putDataToMemory(key, ImageHelper.bitmap2Bytes(bitmap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void put(String key, Bitmap bitmap, int cacheTime, int timeUnit) {
        int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
        ((MemoryManager<String, byte[]>) getInstance()).putDataToMemory(key,
                CacheHelper.convertByteArrayWithDate(expirationTime, ImageHelper.bitmap2Bytes(bitmap)));
    }

    @Override
    public Bitmap getAsBitmap(String key) {
        @SuppressWarnings("unchecked")
        byte[] bs = ((MemoryManager<String, byte[]>) getInstance()).getDataFromMemory(key);
        byte[] bytes = checkCacheExpiredWithByte(key, bs);
        if (bytes == null) {
            return null;
        } else {
            return ImageHelper.bytes2Bitmap(bytes);
        }

    }

	/*----------------------------------------------------Drawable-----------------------------------------------------*/

    @Override
    public void put(String key, Drawable value) {
        put(key, ImageHelper.drawable2Bitmap(value));
    }

    @Override
    public void put(String key, Drawable value, int cacheTime, int timeUnit) {
        put(key, ImageHelper.drawable2Bitmap(value), cacheTime, timeUnit);
    }

    @Override
    public Drawable getAsDrawable(String key) {
        @SuppressWarnings("unchecked")
        byte[] bs = ((MemoryManager<String, byte[]>) getInstance()).getDataFromMemory(key);
        byte[] bytes = checkCacheExpiredWithByte(key, bs);
        if (bytes == null) {
            return null;
        } else {
            Bitmap bitmap = ImageHelper.bytes2Bitmap(bytes);
            return bitmap == null ? null : ImageHelper.bitmap2Drawable(bitmap);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(String key) {
        ((MemoryManager<String, String>) getInstance()).removeDataFromMemory(key);
        Log.i(TAG, "Memory cache delete success");
        return true;
    }

    private String checkCacheExpiredWithString(String key, String result) {
        if (result != null) {
            if (!CacheHelper.isExpired(result)) { // unexpired
                String originalResult = CacheHelper.clearDateInfo(result);
                Log.i(TAG, "Memory cache hint :" + originalResult + "---->time interval:"
                        + CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
                return originalResult;
            } else {
                Log.i(TAG, "Memory cache expired:" + "---->time interval:"
                        + CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
                remove(key);
                return null;
            }
        } else {
            return result;
        }

    }

    private byte[] checkCacheExpiredWithByte(String key, byte[] result) {
        if (result != null) {
            if (!CacheHelper.isExpired(result)) { // unexpired
                byte[] originalResult = CacheHelper.clearDateInfo(result);
                Log.i(TAG, "Memory cache hint :" + originalResult + "---->time interval:"
                        + CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
                return originalResult;
            } else {
                Log.i(TAG, "Memory cache expired:" + "---->time interval:"
                        + CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
                remove(key);
                return null;
            }
        } else {
            return result;
        }

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

}
