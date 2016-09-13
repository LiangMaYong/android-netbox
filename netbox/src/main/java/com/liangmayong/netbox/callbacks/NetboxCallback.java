package com.liangmayong.netbox.callbacks;

import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.utils.NetboxUtils;

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
            T data = null;
            try {
                data = response.getData(NetboxUtils.getGenericType(this, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            handleResponseSuccess(data);
        } else {
            handleResponseError(response.getErrorCode(), response.getErrorMessage());
        }
    }

    @Override
    public abstract void onFailure(NetboxError error);
}
