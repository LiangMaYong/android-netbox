package com.liangmayong.netbox.callbacks;

import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.utils.NetboxUtils;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public abstract class OnNetboxCallback<T> implements OnNetboxListener {

    private Response response = null;

    /**
     * getResponse
     *
     * @return response
     */
    public Response getResponse() {
        return response;
    }

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
     */
    public String generateDefualtKey() {
        return null;
    }

    @Override
    public void onResponse(Response response) {
        this.response = response;
        if (response.isSuccess()) {
            T data = null;
            try {
                data = response.getData(generateDefualtKey(), NetboxUtils.getGenericType(this, 0));
            } catch (Exception e) {
            }
            handleResponseSuccess(data);
        } else {
            handleResponseError(response.getErrorCode(), response.getErrorMessage());
        }
    }

    @Override
    public abstract void onFailure(NetboxError error);

    @Override
    public void onProgress(int progress) {
    }
}
