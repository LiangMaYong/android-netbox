package com.liangmayong.netbox.callbacks;

import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.utils.NetboxUtils;

import java.lang.reflect.Type;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public abstract class NetboxCallback<T> implements OnNetboxListener {

    /**
     * handleResponseSuccess
     *
     * @param data data
     */
    public abstract void handleResponseSuccess(T data);

    /**
     * handleResponseError
     *
     * @param code    code
     * @param message message
     */
    public abstract void handleResponseError(String code, String message);

    /**
     * generateDefualtKey
     *
     * @param response response
     */
    public String generateDefualtKey(Response response) {
        return response.getDefualtKey();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onResponse(Response response) {
        if (response.isSuccess()) {
            Type type = null;
            T data = null;
            try {
                type = NetboxUtils.getGenericType(this, 0);
                data = response.getData(generateDefualtKey(response), type);
            } catch (Exception e) {
            }
            handleResponseSuccess(data);
        } else {
            handleResponseError(response.getErrorCode(), response.getErrorMessage());
        }
    }

    @Override
    public abstract void onFailure(NetboxError error);
}
