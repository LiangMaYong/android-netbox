package com.liangmayong.android_netbox;

import android.util.Log;

import com.liangmayong.netbox.defualts.DefualtNetboxConverter;
import com.liangmayong.netbox.response.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        return "string-data";
    }

    @Override
    public <T> T converter(String key, Type type, Response response) {
        if (type == String.class) {
            return (T) (key + ":000000000000000000000");
        } else if ("java.util.List<java.lang.String>".equals(type.toString())) {
            List<String> list = new ArrayList<String>();
            list.add("22222222222");
            list.add("22222222222");
            return (T) list;
        }
        return super.converter(key, type, response);
    }

    @Override
    public <T> List<T> converterList(String key, Type type, Response response) {
        return super.converterList(key, type, response);
    }
}
