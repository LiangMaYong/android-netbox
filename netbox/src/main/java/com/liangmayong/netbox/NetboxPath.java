package com.liangmayong.netbox;

import android.content.Context;

import com.liangmayong.netbox.callbacks.OnNetboxListener;
import com.liangmayong.netbox.interfaces.NetboxCache;
import com.liangmayong.netbox.params.FileParam;
import com.liangmayong.netbox.params.Method;
import com.liangmayong.netbox.params.Request;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.errors.RequestLockException;
import com.liangmayong.netbox.throwables.errors.UnkownException;
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
        this.mParams = new HashMap<String, String>();
        this.mHeaders = new HashMap<String, String>();
        this.mFiles = new HashMap<String, FileParam>();
        resetPath();
    }

    /**
     * method
     *
     * @param method method
     * @return path
     */
    public NetboxPath method(Method method) {
        if (checkRequestIng(false)) {
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
        if (checkRequestIng(false)) {
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
     * params
     *
     * @param params params
     * @return path
     */
    public NetboxPath params(Map<String, String> params) {
        if (checkRequestIng(false)) {
            return this;
        }
        if (params != null) {
            mParams.putAll(params);
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
        if (checkRequestIng(false)) {
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
     * files
     *
     * @param files files
     * @return path
     */
    public NetboxPath files(Map<String, FileParam> files) {
        if (checkRequestIng(false)) {
            return this;
        }
        if (files != null) {
            mFiles.putAll(files);
        }
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
        if (checkRequestIng(false)) {
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
     * headers
     *
     * @param headers headers
     * @return path
     */
    public NetboxPath headers(Map<String, String> headers) {
        if (checkRequestIng(false)) {
            return this;
        }
        if (headers != null) {
            mHeaders.putAll(headers);
        }
        return this;
    }

    /**
     * execute
     *
     * @param context  context
     * @param listener listener
     */
    public void execute(final Context context, final OnNetboxListener listener) {
        if (context == null) {
            throw new IllegalArgumentException("The calling method execute must have context parameters,and context not null");
        }
        if (checkRequestIng(true)) {
            if (listener != null) {
                listener.onFailure(new RequestLockException(null, "In the request, the data can not be modified"));
            }
            return;
        }
        final Request request = new Request(Method.valueOf(mMethod.value()), getRequestURL(), new HashMap<String, String>(mParams), new HashMap<String, String>(mHeaders), new HashMap<String, FileParam>(mFiles));
        mRequsetIng = true;
        final long requestTime = System.currentTimeMillis();
        OnNetboxListener mNetboxListener = new OnNetboxListener() {

            private Response historyResponse = null;

            @Override
            public void onResponseHistory(Response response) {
                this.historyResponse = response;
                if (listener != null) {
                    listener.onResponseHistory(response);
                }
            }

            @Override
            public void onResponse(Response response) {
                if (response == null) {
                    onFailure(new UnkownException("response is null"));
                    return;
                }
                response.setConverter(Netbox.server(mServerType).generateConverterType());
                NetboxUtils.setField(Response.class, response, "requestTime", requestTime);
                NetboxUtils.setField(Response.class, response, "responseTime", System.currentTimeMillis());
                if (shouldCoverCache(response, historyResponse)) {
                    onCoverCache(response, historyResponse);
                }
                handleResponse(response);
                if (listener != null) {
                    listener.onResponse(response);
                }
                resetPath();
            }

            @Override
            public void onFailure(NetboxError error) {
                handleFailure(request, error);
                if (listener != null) {
                    listener.onFailure(error);
                }
                resetPath();
            }

            /**
             * onCoverCache
             * @param response response
             * @param historyResponse historyResponse
             */
            private void onCoverCache(Response response, Response historyResponse) {
                setCache(context, request, response);
            }

            @Override
            public boolean shouldCoverCache(Response response, Response historyResponse) {
                if (listener != null) {
                    return listener.shouldCoverCache(response, historyResponse);
                }
                return super.shouldCoverCache(response, historyResponse);
            }
        };
        Response response = getCache(context, request);
        if (response != null) {
            if (mNetboxListener != null) {
                mNetboxListener.onResponseHistory(response);
            }
        }
        Netbox.generateInterceptor(Netbox.server(mServerType).generateInterceptorType()).execRequest(context, request, mNetboxListener);
    }

    /**
     * sync
     *
     * @param context context
     * @return response
     */
    public Response execute(Context context) throws NetboxError {
        if (checkRequestIng(true)) {
            throw new RequestLockException(null, "In the request, the data can not be modified");
        }
        if (context == null) {
            throw new IllegalArgumentException("The calling method execute must have context parameters,and context not null");
        }
        mRequsetIng = true;
        Request request = new Request(Method.valueOf(mMethod.value()), getRequestURL(), new HashMap<String, String>(mParams), new HashMap<String, String>(mHeaders), new HashMap<String, FileParam>(mFiles));
        try {
            final long requestTime = System.currentTimeMillis();
            Response response = Netbox.generateInterceptor(Netbox.server(mServerType).generateInterceptorType()).syncRequest(context, request);
            if (response == null) {
                throw new UnkownException("response is null");
            }
            response.setConverter(Netbox.server(mServerType).generateConverterType());
            NetboxUtils.setField(Response.class, response, "requestTime", requestTime);
            NetboxUtils.setField(Response.class, response, "responseTime", System.currentTimeMillis());
            setCache(context, request, response);
            handleResponse(response);
            resetPath();
            return response;
        } catch (NetboxError error) {
            handleFailure(request, error);
            resetPath();
            throw error;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    //////// Private
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * getCache
     *
     * @param context context
     * @param request request
     * @return response
     */
    private Response getCache(Context context, Request request) {
        NetboxCache cache = Netbox.server(mServerType).cache();
        String cacheKey = cache.generateKey(context, request);
        if (cacheKey == null || "".equals(cacheKey)) {
            cacheKey = NetboxUtils.getCacheKey(request);
        }
        String body = cache.getCache(context, cacheKey);
        if (body != null && !"".equals(body)) {
            final long requestTime = System.currentTimeMillis();
            Response response = new Response(request);
            response.setBody(body);
            response.setConverter(Netbox.server(mServerType).generateConverterType());
            NetboxUtils.setField(Response.class, response, "requestTime", requestTime);
            NetboxUtils.setField(Response.class, response, "responseTime", System.currentTimeMillis());
            return response;
        }
        return null;
    }

    /**
     * setCache
     *
     * @param context  context
     * @param request  request
     * @param response response
     */
    private void setCache(Context context, Request request, Response response) {
        NetboxCache cache = Netbox.server(mServerType).cache();
        String cacheKey = cache.generateKey(context, request);
        if (cacheKey == null || "".equals(cacheKey)) {
            cacheKey = NetboxUtils.getCacheKey(request);
        }
        cache.setCache(context, cacheKey, response.getBody());
    }

    /**
     * checkRequestIng
     *
     * @return check
     */
    private boolean checkRequestIng(boolean debug) {
        if (mRequsetIng) {
            NetboxLoger.getInstance().debugLog("In the request, the data can not be modified", null);
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
            NetboxLoger.getInstance().debugLog("+=-------------------------------------------------------=+", null);
            NetboxLoger.getInstance().debugLog("+=                  By Netbox onResponse                 =+", null);
            NetboxLoger.getInstance().debugLog("+=-------------------------------------------------------=+", null);
            NetboxLoger.getInstance().debugLog("+= url = " + response.getUrl() + "(" + response.getConsumingTime() + "ms)", null);
            NetboxLoger.getInstance().debugLog("+= method = " + response.getMethod().name(), null);
            String body = response.getBody();
            if (body.length() > 4000) {
                for (int i = 0; i < body.length(); i += 4000) {
                    if (i == 0) {
                        NetboxLoger.getInstance().debugLog("+= body = " + body.substring(i, i + 4000), null);
                    } else if (i + 4000 < body.length()) {
                        NetboxLoger.getInstance().debugLog(body.substring(i, i + 4000), null);
                    } else {
                        NetboxLoger.getInstance().debugLog(body.substring(i, body.length()), null);
                    }
                }
            } else {
                NetboxLoger.getInstance().debugLog("+= body = " + response.getBody(), null);
            }
            if (response.getParams() != null && !response.getParams().isEmpty()) {
                NetboxLoger.getInstance().debugLog("+= params = " + response.getParams(), null);
            }
            if (response.getHeaders() != null && !response.getHeaders().isEmpty()) {
                NetboxLoger.getInstance().debugLog("+= headers = " + response.getHeaders(), null);
            }
            if (response.getFiles() != null && !response.getFiles().isEmpty()) {
                NetboxLoger.getInstance().debugLog("+= files = " + response.getFiles(), null);
            }
            NetboxLoger.getInstance().debugLog("+= timestamp = " + response.getTimestamp(), null);
            NetboxLoger.getInstance().debugLog("+=-------------------------------------------------------=+", null);
        }

    }

    /**
     * handleFailure
     *
     * @param error error
     */
    private void handleFailure(Request request, NetboxError error) {
        if (!Netbox.server(mServerType).handleFailure(request, error)) {
            NetboxLoger.getInstance().debugLog("+=-------------------------------------------------------=+", null);
            NetboxLoger.getInstance().debugLog("+=                 By Netbox onFailure                   =+", null);
            NetboxLoger.getInstance().debugLog("+=-------------------------------------------------------=+", null);
            NetboxLoger.getInstance().debugLog("+= url = " + request.getUrl(), null);
            NetboxLoger.getInstance().debugLog("+= method = " + request.getMethod().name(), null);
            if (request.getParams() != null && !request.getParams().isEmpty()) {
                NetboxLoger.getInstance().debugLog("+= params = " + request.getParams(), null);
            }
            if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
                NetboxLoger.getInstance().debugLog("+= headers = " + request.getHeaders(), null);
            }
            if (request.getFiles() != null && !request.getFiles().isEmpty()) {
                NetboxLoger.getInstance().debugLog("+= files = " + request.getFiles(), null);
            }
            NetboxLoger.getInstance().debugLog("+=-------------------------------------------------------=+", null);
            if (error != null) {
                NetboxLoger.getInstance().debugLog("onFailure:", error);
            }
        }
    }

    private void resetPath() {
        mFiles.clear();
        mParams.clear();
        mHeaders.clear();
        mMethod = Method.GET;
        NetboxConfig boxConfig = NetboxConfig.getInstance(mServerType);
        mParams.putAll(boxConfig.getCommonParams());
        mHeaders.putAll(boxConfig.getCommonHeaders());
        mRequsetIng = false;
    }
}
