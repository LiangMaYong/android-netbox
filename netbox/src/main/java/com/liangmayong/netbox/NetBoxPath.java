package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.interfaces.OnNetBoxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;
import com.liangmayong.netbox.utils.NetBoxUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class NetBoxPath {

    // action type
    private Class<? extends NetBoxAction> mActionType = null;
    // method
    private Method mMethod = Method.GET;
    // path
    private String mPath = "";
    // params
    private final Map<String, String> mParams = new HashMap<String, String>(NetBox.generateCommonParams());
    // headers
    private final Map<String, String> mHeaders = new HashMap<String, String>(NetBox.generateCommonHeaders());

    public NetBoxPath(Class<? extends NetBoxAction> actionType, String path) {
        this.mActionType = actionType;
    }

    /**
     * method
     *
     * @param method method
     * @return call
     */
    public NetBoxPath method(Method method) {
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
    public NetBoxPath param(String key, String param) {
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
    public NetBoxPath header(String key, String header) {
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
    public void exec(Context context, final OnNetBoxListener<Response> listener) {
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        String requestUrl = NetBoxUtils.parseUrl(NetBox.generateAction(mActionType).generateBaseUrl(), mPath);
        NetBox.generateInterceptor(NetBox.generateAction(mActionType).generateInterceptorType()).execRequest(context, NetBox.generateAction(mActionType).generateConverterType(), mMethod, requestUrl, mParams, mHeaders, new OnNetBoxListener<Response>() {
            @Override
            public void onResponse(Response response) {
                NetBox.generateAction(mActionType).handleResponse(response);
                listener.onResponse(response);
            }

            @Override
            public void onFailure(NetBoxError error) {
                NetBox.generateAction(mActionType).handleFailure(error);
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
    public Response execSync(Context context) throws NetBoxError {
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        try {
            String requestUrl = NetBoxUtils.parseUrl(NetBox.generateAction(mActionType).generateBaseUrl(), mPath);
            Response response = NetBox.generateInterceptor(NetBox.generateAction(mActionType).generateInterceptorType()).execSyncRequest(context, NetBox.generateAction(mActionType).generateConverterType(), mMethod, requestUrl, mParams, mHeaders);
            NetBox.generateAction(mActionType).handleResponse(response);
            return response;
        } catch (NetBoxError error) {
            NetBox.generateAction(mActionType).handleFailure(error);
            throw error;
        }
    }

    /**
     * execCache
     *
     * @param context context
     * @return response
     */
    public Response execCache(Context context) throws NetBoxError {
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        try {
            String requestUrl = NetBoxUtils.parseUrl(NetBox.generateAction(mActionType).generateBaseUrl(), mPath);
            Response response = NetBox.generateInterceptor(NetBox.generateAction(mActionType).generateInterceptorType()).execCacheRequest(context, NetBox.generateAction(mActionType).generateConverterType(), mMethod, requestUrl, mParams, mHeaders);
            NetBox.generateAction(mActionType).handleResponse(response);
            return response;
        } catch (NetBoxError error) {
            NetBox.generateAction(mActionType).handleFailure(error);
            throw error;
        }
    }
}
