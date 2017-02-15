package com.liangmayong.netbox.interfaces;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefaultNetboxConverter implements NetboxConverter {

    @Override
    public boolean isSuccess(String response) {
        return false;
    }

    @Override
    public String converterErrorMessage(String body) {
        return "unkown";
    }

    @Override
    public String converterErrorCode(String response) {
        return "-1";
    }

    @Override
    public String converterDefualtKey() {
        return null;
    }

    @Override
    public <T> T converterData(String data, Type type) {
        return null;
    }

    @Override
    public String converterKey(String key, String response) {
        return null;
    }

    @Override
    public String converterBody(String body) {
        return body;
    }

    @Override
    public long converterTimestamp(String body) {
        return System.currentTimeMillis();
    }
}
