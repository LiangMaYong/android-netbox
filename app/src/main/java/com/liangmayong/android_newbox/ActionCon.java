package com.liangmayong.android_newbox;

import com.liangmayong.netbox.defualts.DefualtNetboxConverter;
import com.liangmayong.netbox.response.Response;

import java.util.List;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class ActionCon extends DefualtNetboxConverter {
    @Override
    public boolean isSuccess(Response response) {
        return false;
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
    public <T> T converter(String key, Class<T> entityClass, Response response) {
        if (entityClass == String.class) {
            return (T) ( key + ":000000000000000000000");
        }
        return super.converter(key, entityClass, response);
    }

    @Override
    public <T> List<T> converterList(String key, Class<T> entityClass, Response response) {
        return super.converterList(key, entityClass, response);
    }
}
