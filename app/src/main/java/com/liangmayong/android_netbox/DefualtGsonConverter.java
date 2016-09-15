package com.liangmayong.android_netbox;

import com.google.gson.Gson;
import com.liangmayong.netbox.interfaces.DefualtNetboxConverter;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/15.
 */
public class DefualtGsonConverter extends DefualtNetboxConverter {

    //gson
    private volatile Gson gson = null;

    @Override
    public boolean isSuccess(String body) {
        return true;
    }

    @Override
    public String converterErrorMessage(String body) {
        return "unkown error";
    }

    @Override
    public String converterErrorCode(String body) {
        return "-1";
    }

    @Override
    public String converterDefualtKey() {
        return null;
    }

    @Override
    public <T> T converterData(String data, Type type) {
        return to(data, type);
    }

    @Override
    public String converterKey(String key, String body) {
        try {
            JSONObject json = new JSONObject(body);
            return json.getString(key);
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * parse
     *
     * @param data data
     * @param type type
     * @param <T>  type
     * @return T
     */
    private <T> T to(String data, Type type) {
        try {
            if (type == String.class) {
                return (T) data;
            }
            return gson().fromJson(data, type);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * getGson
     *
     * @return gson
     */
    private Gson gson() {
        if (gson == null) {
            synchronized (this) {
                gson = new Gson();
            }
        }
        return gson;
    }
}
