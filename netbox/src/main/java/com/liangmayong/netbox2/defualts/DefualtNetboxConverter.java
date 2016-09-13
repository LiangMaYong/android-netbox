package com.liangmayong.netbox2.defualts;

import com.liangmayong.netbox2.interfaces.NetboxConverter;
import com.liangmayong.netbox2.response.Response;

import java.util.List;

/**
 * Created by liangmayong on 2016/9/12.
 */
public class DefualtNetboxConverter implements NetboxConverter {

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
