package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.utils.NetboxUtils;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class NetboxPath {

    // action type
    private Class<? extends NetboxAction> mActionType = null;
    // method
    private Method mMethod = Method.GET;
    // path
    private String mPath = "";
    // params
    private final Map<String, String> mParams;
    // headers
    private final Map<String, String> mHeaders;

    public NetboxPath(Class<? extends NetboxAction> actionType, String path) {
        this.mActionType = actionType;
        NetboxConfig boxConfig = NetboxConfig.getInstance(actionType);
        this.mParams = boxConfig.getCommonParams();
        this.mHeaders = boxConfig.getCommonHeaders();
    }

    /**
     * method
     *
     * @param method method
     * @return call
     */
    public NetboxPath method(Method method) {
        this.mMethod = method;
        return this;
    }

    /**
     * param
     *
     * @param key   key
     * @param param param
     * @return call
     */
    public NetboxPath param(String key, String param) {
        if (key != null) {
            if (param != null) {
                mParams.put(key, param);
            } else {
                mParams.remove(key);
            }
        }
        return this;
    }

    /***
     * header
     *
     * @param key    key
     * @param header header
     * @return call
     */
    public NetboxPath header(String key, String header) {
        if (key != null) {
            if (header != null) {
                mHeaders.put(key, header);
            } else {
                mHeaders.remove(key);
            }
        }
        return this;
    }


    /**
     * exec
     *
     * @param context  context
     * @param listener listener
     */
    public void exec(Context context, final OnNetboxListener<Response> listener) {
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        String requestUrl = NetboxUtils.parseUrl(Netbox.generateAction(mActionType).generateBaseUrl(), mPath);
        Netbox.generateInterceptor(Netbox.generateAction(mActionType).generateInterceptorType()).execRequest(context, Netbox.generateAction(mActionType).generateConverterType(), mMethod, requestUrl, mParams, mHeaders, new OnNetboxListener<Response>() {
            @Override
            public void onResponse(Response response) {
                Netbox.generateAction(mActionType).handleResponse(response);
                listener.onResponse(response);
            }

            @Override
            public void onFailure(NetboxError error) {
                Netbox.generateAction(mActionType).handleFailure(error);
                listener.onFailure(error);
            }
        });
    }

    /**
     * execSync
     *
     * @param context context
     * @return response
     */
    public Response execSync(Context context) throws NetboxError {
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        try {
            String requestUrl = NetboxUtils.parseUrl(Netbox.generateAction(mActionType).generateBaseUrl(), mPath);
            Response response = Netbox.generateInterceptor(Netbox.generateAction(mActionType).generateInterceptorType()).execSyncRequest(context, Netbox.generateAction(mActionType).generateConverterType(), mMethod, requestUrl, mParams, mHeaders);
            Netbox.generateAction(mActionType).handleResponse(response);
            return response;
        } catch (NetboxError error) {
            Netbox.generateAction(mActionType).handleFailure(error);
            throw error;
        }
    }

    /**
     * execCache
     *
     * @param context context
     * @return response
     */
    public Response execCache(Context context) throws NetboxError {
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        try {
            String requestUrl = NetboxUtils.parseUrl(Netbox.generateAction(mActionType).generateBaseUrl(), mPath);
            Response response = Netbox.generateInterceptor(Netbox.generateAction(mActionType).generateInterceptorType()).execCacheRequest(context, Netbox.generateAction(mActionType).generateConverterType(), mMethod, requestUrl, mParams, mHeaders);
            Netbox.generateAction(mActionType).handleResponse(response);
            return response;
        } catch (NetboxError error) {
            Netbox.generateAction(mActionType).handleFailure(error);
            throw error;
        }
    }
}
