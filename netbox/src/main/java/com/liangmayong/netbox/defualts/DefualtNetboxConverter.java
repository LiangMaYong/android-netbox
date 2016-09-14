package com.liangmayong.netbox.defualts;

import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.response.Response;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetboxConverter implements NetboxConverter {

    @Override
    public boolean isSuccess(String response) {
        return false;
    }

    @Override
    public String converterErrorMessage(String body) {
        return null;
    }

    @Override
    public String converterErrorCode(String response) {
        return null;
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

}
