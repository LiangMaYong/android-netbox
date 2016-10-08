package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.interfaces.Method;
import com.liangmayong.netbox.interfaces.NetboxCache;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.params.FileParam;
import com.liangmayong.netbox.params.Parameter;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.UnkownError;
import com.liangmayong.netbox.utils.NetboxUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public final class NetboxPath {

    // server type
    private Class<? extends NetboxServer> mServerType = null;
    // method
    private Method mMethod = Method.GET;
    // path
    private String mPath = "";
    // cacheEnable
    private boolean cacheEnable = false;
    // mRequsetIng
    private boolean mRequsetIng = false;
    // params
    private final Map<String, String> mParams;
    // headers
    private final Map<String, String> mHeaders;
    // files
    private final Map<String, FileParam> mFiles;

    /**
     * NetboxPath
     *
     * @param actionType actionType
     * @param path       path
     */
    public NetboxPath(Class<? extends NetboxServer> actionType, String path) {
        this.mServerType = actionType;
        this.mPath = path;
        NetboxConfig boxConfig = NetboxConfig.getInstance(actionType);
        this.mParams = boxConfig.getCommonParams();
        this.mHeaders = boxConfig.getCommonHeaders();
        this.mFiles = new HashMap<String, FileParam>();
    }

    /**
     * method
     *
     * @param method method
     * @return path
     */
    public NetboxPath method(Method method) {
        if (checkRequestIng()) {
            return this;
        }
        this.mMethod = method;
        return this;
    }

    /**
     * param
     *
     * @param key   key
     * @param param param
     * @return path
     */
    public NetboxPath param(String key, String param) {
        if (checkRequestIng()) {
            return this;
        }
        if (key != null) {
            if (param != null) {
                mParams.put(key, param);
            } else {
                mParams.remove(key);
            }
        }
        return this;
    }


    /**
     * file
     *
     * @param key   key
     * @param param param
     * @return path
     */
    public NetboxPath file(String key, FileParam param) {
        if (checkRequestIng()) {
            return this;
        }
        if (key != null) {
            if (param != null) {
                mFiles.put(key, param);
            } else {
                mFiles.remove(key);
            }
        }
        return this;
    }

    /**
     * cacheEnable
     *
     * @param cacheEnable cacheEnable
     */
    public NetboxPath cache(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
        return this;
    }

    /***
     * header
     *
     * @param key    key
     * @param header header
     * @return path
     */
    public NetboxPath header(String key, String header) {
        if (checkRequestIng()) {
            return this;
        }
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
    public void exec(final Context context, final OnNetboxListener listener) {
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        if (checkRequestIng()) {
            return;
        }
        if (cacheEnable) {
            Response response = cache(context);
            if (response != null) {
                handleResponse(response);
                listener.onResponse(response);
                return;
            }
        }
        final Parameter parameter = new Parameter(Method.valueOf(mMethod.value()), getRequestURL(), new HashMap<String, String>(mParams), new HashMap<String, String>(mHeaders), new HashMap<String, FileParam>(mFiles));
        mRequsetIng = true;
        final long requestTime = System.currentTimeMillis();
        Netbox.generateInterceptor(Netbox.server(mServerType).generateInterceptorType()).execRequest(context, parameter, new OnNetboxListener() {
            @Override
            public void onResponse(Response response) {
                if (response == null) {
                    onFailure(new UnkownError("response is null"));
                    return;
                }
                response.setConverter(Netbox.server(mServerType).generateConverterType());
                NetboxUtils.setField(Response.class, response, "requestTime", requestTime);
                NetboxUtils.setField(Response.class, response, "responseTime", System.currentTimeMillis());
                NetboxUtils.setField(Response.class, response, "isCache", false);
                NetboxCache cache = Netbox.generateCache(Netbox.server(mServerType).generateCacheType());
                String cacheKey = cache.generateKey(context, parameter);
                if (cacheKey == null || "".equals(cacheKey)) {
                    cacheKey = NetboxUtils.generateCacheKey(parameter);
                }
                cache.putCache(context, cacheKey, response.getBody());
                handleResponse(response);
                listener.onResponse(response);
                mRequsetIng = false;
            }

            @Override
            public void onFailure(NetboxError error) {
                mRequsetIng = false;
                handleFailure(error);
                listener.onFailure(error);
            }
        });
    }

    /**
     * sync
     *
     * @param context context
     * @return response
     */
    public Response sync(Context context) throws NetboxError {
        if (checkRequestIng()) {
            return null;
        }
        if (cacheEnable) {
            Response response = cache(context);
            if (response != null) {
                handleResponse(response);
                return response;
            }
        }
        mRequsetIng = true;
        if (context == null) {
            throw new IllegalArgumentException("The calling method exec must have context parameters,and context not null");
        }
        try {
            Parameter parameter = new Parameter(Method.valueOf(mMethod.value()), getRequestURL(), new HashMap<String, String>(mParams), new HashMap<String, String>(mHeaders), new HashMap<String, FileParam>(mFiles));
            final long requestTime = System.currentTimeMillis();
            Response response = Netbox.generateInterceptor(Netbox.server(mServerType).generateInterceptorType()).syncRequest(context, parameter);
            if (response == null) {
                throw new UnkownError("response is null");
            }
            response.setConverter(Netbox.server(mServerType).generateConverterType());
            NetboxUtils.setField(Response.class, response, "requestTime", requestTime);
            NetboxUtils.setField(Response.class, response, "responseTime", System.currentTimeMillis());
            NetboxUtils.setField(Response.class, response, "isCache", false);
            NetboxCache cache = Netbox.generateCache(Netbox.server(mServerType).generateCacheType());
            String cacheKey = cache.generateKey(context, parameter);
            if (cacheKey == null || "".equals(cacheKey)) {
                cacheKey = NetboxUtils.generateCacheKey(parameter);
            }
            cache.putCache(context, cacheKey, response.getBody());
            handleResponse(response);
            mRequsetIng = false;
            return response;
        } catch (NetboxError error) {
            handleFailure(error);
            mRequsetIng = false;
            throw error;
        }
    }

    /**
     * cache
     *
     * @return response
     */
    private Response cache(Context context) {
        Parameter parameter = new Parameter(Method.valueOf(mMethod.value()), getRequestURL(), new HashMap<String, String>(mParams), new HashMap<String, String>(mHeaders), new HashMap<String, FileParam>(mFiles));
        NetboxCache cache = Netbox.generateCache(Netbox.server(mServerType).generateCacheType());
        String cacheKey = cache.generateKey(context, parameter);
        if (cacheKey == null || "".equals(cacheKey)) {
            cacheKey = NetboxUtils.generateCacheKey(parameter);
        }
        String body = cache.getCache(context, cacheKey);
        if (body != null && !"".equals(body)) {
            final long requestTime = System.currentTimeMillis();
            Response response = new Response(parameter);
            response.setBody(body);
            response.setConverter(Netbox.server(mServerType).generateConverterType());
            NetboxUtils.setField(Response.class, response, "requestTime", requestTime);
            NetboxUtils.setField(Response.class, response, "responseTime", System.currentTimeMillis());
            NetboxUtils.setField(Response.class, response, "isCache", true);
            return response;
        }
        return null;
    }

    /**
     * checkRequestIng
     *
     * @return check
     */
    private boolean checkRequestIng() {
        if (mRequsetIng) {
            if (NetboxUtils.isDebugable()) {
                NetboxUtils.debugLog("In the request, the data can not be modified", null);
            }
            return true;
        }
        return false;
    }

    /**
     * getRequestURL
     *
     * @return url
     */
    private String getRequestURL() {
        String requestUrl = Netbox.server(mServerType).handleURL(Netbox.server(mServerType).generateURL(), mPath, mMethod);
        if (requestUrl == null || "".equals(requestUrl)) {
            requestUrl = NetboxUtils.parseUrl(Netbox.server(mServerType).generateURL(), mPath, mMethod);
        }
        return requestUrl;
    }

    /**
     * handleResponse
     *
     * @param response response
     */
    private void handleResponse(Response response) {
        if (!Netbox.server(mServerType).handleResponse(response)) {
            if (NetboxUtils.isDebugable()) {
                NetboxUtils.debugLog("+=-------------------------------------------------------=+", null);
                NetboxUtils.debugLog("+=                  By Netbox onResponse                 =+", null);
                NetboxUtils.debugLog("+=-------------------------------------------------------=+", null);
                NetboxUtils.debugLog("+= url = " + response.getUrl(), null);
                NetboxUtils.debugLog("+= method = " + response.getMethod().name(), null);
                NetboxUtils.debugLog("+= body = " + response.getBody(), null);
                if (response.getParams() != null && !response.getParams().isEmpty()) {
                    NetboxUtils.debugLog("+= params = " + response.getParams(), null);
                }
                if (response.getHeaders() != null && !response.getHeaders().isEmpty()) {
                    NetboxUtils.debugLog("+= headers = " + response.getHeaders(), null);
                }
                NetboxUtils.debugLog("+=-------------------------------------------------------=+", null);
            }
        }
    }

    /**
     * handleFailure
     *
     * @param error error
     */
    private void handleFailure(NetboxError error) {
        if (!Netbox.server(mServerType).handleFailure(error)) {
            if (NetboxUtils.isDebugable()) {
                NetboxUtils.debugLog("+=-------------------------------------------------------=+", null);
                NetboxUtils.debugLog("+=                 By Netbox onFailure                   =+", null);
                NetboxUtils.debugLog("+=-------------------------------------------------------=+", null);
                NetboxUtils.debugLog("onFailure:", error);
            }
        }
    }
}
