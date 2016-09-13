package com.liangmayong.android_netbox;

import android.util.Log;

import com.google.gson.Gson;
import com.liangmayong.netbox.defualts.DefualtNetboxConverter;
import com.liangmayong.netbox.response.Response;

import java.lang.reflect.Type;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class GithubConverter extends DefualtNetboxConverter {
    @Override
    public boolean isSuccess(Response response) {
        return true;
    }

    @Override
    public String converterErrorMessage(Response response) {
        return "3399ff";
    }

    @Override
    public String converterErrorCode(Response response) {
        return "11";
    }

    @Override
    public String converterDefualtKey() {
        return null;
    }

    @Override
    public <T> T converterData(String data, Type type) {
        Log.d("TAG",data);
        Log.d("TAG",type+"");
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
    public String converterKey(String key, Response response) {
        Log.d("TAG",key);
        return null;
    }
}
