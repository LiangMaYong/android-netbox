package com.liangmayong.netbox.strcache.disk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

import com.liangmayong.netbox.strcache.interfaces.ICacheInterface;
import com.liangmayong.netbox.strcache.utils.CacheHelper;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * DiskManager
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class DiskManager implements ICacheInterface {

    private static DiskManager mCacheManager;

    private DiskLruCache mDiskLruCache;

    private static final int DEFAULT_VALUE_COUNT = 1;

    private static final int DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

	/*
     * ====================================================================
	 * Constructor
	 * ====================================================================
	 */

    public DiskManager(Context context) {
        init(context);
    }

    public static DiskManager getInstance(Context context) {
        if (mCacheManager == null) {
            mCacheManager = new DiskManager(context);
        }
        return mCacheManager;
    }

    private void init(Context context) {
        if (mDiskLruCache == null) {
            File cacheDir = getDiskCacheDir(context, "diskcache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            try {
                mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), DEFAULT_VALUE_COUNT,
                        DEFAULT_MAX_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

	/*
     * ====================================================================
	 * Public Mod
	 * ====================================================================
	 */

    public void close() throws Exception {
        mDiskLruCache.close();
    }

    public void delete() throws Exception {
        mDiskLruCache.delete();
    }

    public void flush() throws Exception {
        mDiskLruCache.flush();
    }

    public boolean isClosed() {
        return mDiskLruCache.isClosed();
    }

    public long size() {
        return mDiskLruCache.size();
    }

    public void setMaxSize(long maxSize) {
        mDiskLruCache.setMaxSize(maxSize);
    }

    public File getDirectory() {
        return mDiskLruCache.getDirectory();
    }

    public long getMaxSize() {
        return mDiskLruCache.getMaxSize();
    }

	/*
     * =========================================================================
	 * Utilities
	 * =========================================================================
	 */

    public InputStream get(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(hashKeyForDisk(key));
            if (snapshot == null) {
                return null;
            }
            return snapshot.getInputStream(0);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public DiskLruCache.Editor editor(String key) {
        try {
            key = hashKeyForDisk(key);
            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
            return edit;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Record cache synchronization to the journal file.
     */
    /*
     * public void fluchCache() { if (mDiskLruCache != null) { try {
	 * mDiskLruCache.flush(); } catch (IOException e) { e.printStackTrace(); } }
	 * }
	 */
    public static String InputStreamToString(InputStream is) throws Exception {
        int BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = is.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(), "ISO-8859-1");
    }

    /**
     * Using the MD5 algorithm to encrypt the key of the incoming and return.
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * According to the incoming a unique name for the path of the hard disk
     * cache address.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir != null) {
                cachePath = cacheDir.getPath(); /// sdcard/Android/data/<application
                /// package>/cache
            } else {
                cachePath = context.getCacheDir().getPath(); // /data/data/<application
                // package>/cache
            }
        } else {
            cachePath = context.getCacheDir().getPath(); // /data/data/<application
            // package>/cache
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /*
     * =======================================================================
     * Private Mod
     * =======================================================================
     */
    private void putStringToDisk(String key, String value) {
        DiskLruCache.Editor edit = null;
        BufferedWriter bw = null;
        try {
            edit = editor(key);
            if (edit == null)
                return;
            OutputStream os = edit.newOutputStream(0);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(value);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                edit.abort();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getStringFromDisk(String key) {
        InputStream inputStream = null;
        try {
            inputStream = get(key);
            if (inputStream == null)
                return null;
            StringBuilder sb = new StringBuilder();
            int len = 0;
            byte[] buf = new byte[128];
            while ((len = inputStream.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            String result = sb.toString();

            return result;

        } catch (IOException e) {
            e.printStackTrace();
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        }
        return null;
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
     * ====================================================================
	 * Override ICache
	 * ====================================================================
	 */

	/*----------------------------------------------------String-----------------------------------------------------*/

    @Override
    public void put(String key, String value) {
        putStringToDisk(key, value);

    }

    @Override
    public void put(String key, String value, int cacheTime, int timeUnit) {
        int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
        putStringToDisk(key, CacheHelper.convertStringWithDate(expirationTime, value));
    }

    @Override
    public String getAsString(String key) {
        String result = getStringFromDisk(key);
        return checkCacheExpiredWithString(key, result);
    }

    @Override
    public boolean remove(String key) {
        try {
            key = hashKeyForDisk(key);
            boolean isSuccess = mDiskLruCache.remove(key);
            return isSuccess;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public void clear() {
        try {
            mDiskLruCache.flush();
        } catch (Exception e) {
        }
    }

}
