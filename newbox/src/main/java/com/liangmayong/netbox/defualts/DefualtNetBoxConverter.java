package com.liangmayong.netbox.defualts;

import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.netbox.response.Response;

import java.util.List;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetBoxConverter implements NetBoxConverter {

    @Override
    public boolean isSuccess(Response response) {
        return false;
    }

    @Override
    public String converterErrorMessage(Response response) {
        return null;
    }

    @Override
    public String converterErrorCode(Response response) {
        return null;
    }

    @Override
    public String converterDefualtKey() {
        return null;
    }

    @Override
    public <T> T converter(String key, Class<T> entityClass, Response response) {
        return null;
    }

    @Override
    public <T> List<T> converterList(String key, Class<T> entityClass, Response response) {
        return null;
    }
}
