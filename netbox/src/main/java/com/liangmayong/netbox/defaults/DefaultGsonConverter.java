package com.liangmayong.netbox.defaults;

import com.google.gson.Gson;
import com.liangmayong.netbox.defaults.abstracts.AbstractDefaultNetboxConverter;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/15.
 */
public class DefaultGsonConverter extends AbstractDefaultNetboxConverter {

    private String result_msg = "result_msg";
    private String result_code = "result_code";
    private String result_timestamp = "timestamp";
    private String result_data = "result_data";
    private String[] result_success = {"1"};

    //gson
    private volatile Gson gson = null;

    @Override
    public boolean isSuccess(String body) {
        String result_code = converterErrorCode(body);
        for (int i = 0; i < result_success.length; i++) {
            if (result_success[i].equals(result_code)) {
                return true;
            }
        }
        return super.isSuccess(body);
    }

    @Override
    public boolean isExist(String key, String value, String body) {
        String c_value = converterKey(key, body);
        if (value != null && value.equals(c_value)) {
            return true;
        }
        return super.isExist(key, value, body);
    }

    @Override
    public String converterErrorMessage(String body) {
        return converterKey(result_msg, body);
    }

    @Override
    public String converterErrorCode(String body) {
        return converterKey(result_code, body);
    }

    @Override
    public long converterTimestamp(String body) {
        String timestamp = converterKey(result_timestamp, body);
        try {
            long time = Long.parseLong(timestamp);
            return time;
        } catch (Exception e) {
        }
        return super.converterTimestamp(body);
    }

    @Override
    public String converterDefualtKey() {
        return result_data;
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
