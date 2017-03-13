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

    private static volatile DatabaseCache ourInstance = null;
    private static String table_name = "database_cache_table";
    private static String db_name = "netbox_database_cache.db";
    private static int db_version = 1;

    public static DatabaseCache getInstance() {
        if (ourInstance == null) {
            synchronized (DatabaseCache.class) {
                ourInstance = new DatabaseCache();
            }
        }
        return ourInstance;
    }

    private DatabaseCache() {
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, db_name, null, db_version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, key TEXT NOT NULL,body TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

    public void setCache(Context context, String key, String body) {
        if (hasCache(context, key)) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("body", body);
                db.update(table_name, contentValues, "key = '" + key + "'", null);
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
                values.put("key", key);
                values.put("body", body);
                db.insert(table_name, null, values);
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
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(false, table_name, null, "key = '" + key + "'", null, null, null,
                null, null);
        try {
            if (cursor.moveToNext()) {
                try {
                    int columnIndex = cursor.getColumnIndex("body");
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
                db.delete(table_name, "key = '" + key + "'", null);
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
                db.delete(table_name, null, null);
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
            Cursor cursor = db.query(false, table_name, null, "key = '" + key + "'", null, null, null, null, null);
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
            Cursor cursor = db.query(false, table_name, null, null, null, null, null, null, null);
            try {
                while (cursor.moveToNext()) {
                    String key = "";
                    String body = "";
                    try {
                        int columnIndex = cursor.getColumnIndex("key");
                        key = cursor.getString(columnIndex);
                    } catch (Exception e) {
                    }
                    try {
                        int columnIndex = cursor.getColumnIndex("body");
                        body = cursor.getString(columnIndex);
                    } catch (Exception e) {
                    }
                    map.put(key, body);
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
            Cursor cursor = db.query(false, table_name, null, null, null, null, null, null, null);
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
