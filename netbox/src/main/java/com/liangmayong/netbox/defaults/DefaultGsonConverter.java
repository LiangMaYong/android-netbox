package com.liangmayong.netbox.defaults;

import com.google.gson.Gson;
import com.liangmayong.netbox.interfaces.DefaultNetboxConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/15.
 */
public class DefaultGsonConverter extends DefaultNetboxConverter {

    //gson
    private volatile Gson gson = null;

    @Override
    public boolean isSuccess(String body) {
        String result_code = converterErrorCode(body);
        if ("1".equals(result_code) || "10000".equals(result_code)) {
            return true;
        }
        return false;
    }

    @Override
    public String converterErrorMessage(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.getString("result_msg");
        } catch (JSONException e) {
        }
        return "unkown error";
    }

    @Override
    public String converterErrorCode(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.getString("result_code");
        } catch (JSONException e) {
        }
        return "-1";
    }

    @Override
    public long converterTimestamp(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.getLong("timestamp");
        } catch (JSONException e) {
        }
        return super.converterTimestamp(body);
    }

    @Override
    public String converterDefualtKey() {
        return "result_data";
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
