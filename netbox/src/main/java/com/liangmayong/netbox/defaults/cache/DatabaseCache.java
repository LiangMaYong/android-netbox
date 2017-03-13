package com.liangmayong.netbox.defaults.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/13.
 */
public class DatabaseCache {

    private static final String DB_NAME = "netbox_database_cache.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "netbox_database_cache";
    private static final String F_BODY = "body";
    private static final String F_KEY = "key";
    private static final String F_TIMESTAMP = "timestamp";
    private static volatile DatabaseCache ourInstance = null;

    public static DatabaseCache getInstance() {
        if (ourInstance == null) {
            synchronized (DatabaseCache.class) {
                ourInstance = new DatabaseCache();
            }
        }
        return ourInstance;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////// Private
    ///////////////////////////////////////////////////////////////////////////

    private DatabaseCache() {
    }

    /**
     * DatabaseHelper
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + F_KEY + " TEXT NOT NULL," + F_BODY + " TEXT, " + F_TIMESTAMP + " INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    ///////// Public
    ///////////////////////////////////////////////////////////////////////////

    public void setCache(Context context, String key, String body) {
        if (hasCache(context, key)) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(F_BODY, body);
                contentValues.put(F_TIMESTAMP, System.currentTimeMillis());
                db.update(TABLE_NAME, contentValues, F_KEY + " = '" + key + "'", null);
            } finally {
                db.close();
                db = null;
            }
            return;
        }
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put(F_KEY, key);
                values.put(F_BODY, body);
                values.put(F_TIMESTAMP, System.currentTimeMillis());
                db.insert(TABLE_NAME, null, values);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * getCache
     *
     * @param context context
     * @param key     key
     * @return body
     */
    public String getCache(Context context, String key) {
        String body = "";
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, F_KEY + " = '" + key + "'", null, null, null,
                    null, null);
            try {
                if (cursor.moveToNext()) {
                    try {
                        int columnIndex = cursor.getColumnIndex(F_BODY);
                        body = cursor.getString(columnIndex);
                    } catch (Exception e) {
                    }
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return body;
    }


    /**
     * deleteCache
     *
     * @param context context
     * @param key     key
     */
    public void deleteCache(Context context, String key) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                db.delete(TABLE_NAME, F_KEY + " = '" + key + "'", null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * deleteAll
     *
     * @param context context
     */
    public void deleteAll(Context context) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                db.delete(TABLE_NAME, null, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * hasCache
     *
     * @param context context
     * @param key     key
     * @return
     */
    public boolean hasCache(Context context, String key) {
        int count = 0;
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, F_KEY + " = '" + key + "'", null, null, null, null, null);
            try {
                count = cursor.getCount();
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return count > 0;
    }

    /**
     * getCaches
     *
     * @param context context
     * @return caches
     */
    public Map<String, String> getCaches(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, null, null, null, null, null, null);
            try {
                while (cursor.moveToNext()) {
                    String key = "";
                    String body = "";
                    long timestamp = System.currentTimeMillis();
                    try {
                        int columnIndex = cursor.getColumnIndex(F_KEY);
                        key = cursor.getString(columnIndex);
                    } catch (Exception e) {
                    }
                    try {
                        int columnIndex = cursor.getColumnIndex(F_TIMESTAMP);
                        timestamp = cursor.getLong(columnIndex);
                    } catch (Exception e) {
                    }
                    try {
                        int columnIndex = cursor.getColumnIndex(F_BODY);
                        body = cursor.getString(columnIndex);
                    } catch (Exception e) {
                    }
                    map.put(key + "@" + timestamp, body);
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * getCount
     *
     * @param context context
     * @return count
     */
    public int getCount(Context context) {
        int count = 0;
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(false, TABLE_NAME, null, null, null, null, null, null, null);
            try {
                count = cursor.getCount();
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
        }
        return count;
    }
}
