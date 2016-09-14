package com.liangmayong.android_netbox;

import com.google.gson.Gson;
import com.liangmayong.netbox.defualts.DefualtNetboxConverter;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class GithubConverter extends DefualtNetboxConverter {
    @Override
    public boolean isSuccess(String body) {
        return false;
    }

    @Override
    public String converterErrorMessage(String body) {
        return "无法连接到服务器";
    }

    @Override
    public String converterErrorCode(String body) {
        return "404";
    }

    @Override
    public String converterDefualtKey() {
        return "data";
    }

    @Override
    public <T> T converterData(String data, Type type) {
        try {
            Gson gson = new Gson();
            T dataT = gson.fromJson(data, type);
            return dataT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.converterData(data, type);
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
}
