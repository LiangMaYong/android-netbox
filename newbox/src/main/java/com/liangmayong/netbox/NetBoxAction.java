package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.concretes.Method;
import com.liangmayong.netbox.defualts.DefualtNetBoxConverter;
import com.liangmayong.netbox.defualts.DefualtNetBoxInterceptor;
import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.netbox.interfaces.NetBoxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetBoxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;
import com.liangmayong.netbox.utils.NetBoxUtils;

import java.util.Map;

/**
 * Created by liangmayong on 2016/9/12.
 */
public abstract class NetBoxAction {

    // createAction
    private final void create() {
        NetBoxBind.bindAction(this);
    }

    private Method method = Method.GET;
    // path
    private String path = "";
    // interceptorTypes
    private Class<? extends NetBoxInterceptor> interceptorType = DefualtNetBoxInterceptor.class;
    // converterTypes
    private Class<? extends NetBoxConverter> converterType = DefualtNetBoxConverter.class;
    // params
    private static final Map<String, String> params = NetBox.getCommonParams();
    // headers
    private static final Map<String, String> headers = NetBox.getCommonHeaders();

    /**
     * getBaseUrl
     *
     * @return base url
     */
    protected abstract String getBaseUrl();

    protected Class<? extends NetBoxConverter> getConverterType() {
        return converterType;
    }

    public Class<? extends NetBoxInterceptor> getInterceptorType() {
        return interceptorType;
    }

    public NetBoxAction path(String path) {
        this.path = path;
        return this;
    }

    /**
     * param
     *
     * @param key   key
     * @param param param
     * @return action
     */
    public NetBoxAction param(String key, String param) {
        if (key != null) {
            if (param != null) {
                params.put(key, param);
            } else {
                params.remove(key);
            }
        }
        return this;
    }

    /***
     * header
     *
     * @param key    key
     * @param header header
     * @return action
     */
    public NetBoxAction header(String key, String header) {
        if (key != null) {
            if (header != null) {
                headers.put(key, header);
            } else {
                headers.remove(key);
            }
        }
        return this;
    }

    public <T extends Response> void exec(Context context, final OnNetBoxListener<T> listener) {
        if (context == null) {
            throw new IllegalArgumentException("Calling method exec must have context parameters,and context not null");
        }
        String requestUrl = NetBoxUtils.parseUrl(getBaseUrl(), path);
        NetBox.getInterceptorInstance(getInterceptorType()).asynchronismExecRequest(context, method, requestUrl, params, headers, new OnNetBoxListener<T>() {
            @Override
            public void onResponse(T response) {
                handleResponse(response);
                listener.onResponse(response);
            }

            @Override
            public void onFailure(NetBoxError error) {
                handleFailure(error);
                listener.onFailure(error);
            }
        });
    }

    public Response syncexec(Context context) throws NetBoxError {
        if (context == null) {
            throw new IllegalArgumentException("Calling method exec must have context parameters,and context not null");
        }
        try {
            String requestUrl = NetBoxUtils.parseUrl(getBaseUrl(), path);
            Response response = NetBox.getInterceptorInstance(getInterceptorType()).synchronizationExecRequest(context, method, requestUrl, params, headers);
            handleResponse(response);
            return response;
        } catch (NetBoxError error) {
            handleFailure(error);
            throw error;
        }
    }

    public void handleResponse(Response response) {

    }

    public void handleFailure(NetBoxError error) {

    }
}
