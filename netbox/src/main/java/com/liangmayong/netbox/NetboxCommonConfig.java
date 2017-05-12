package com.liangmayong.netbox;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public final class NetboxCommonConfig {

    private NetboxCommonConfig() {
    }

    private static volatile NetboxCommonConfig defaultInstance = null;

    public static NetboxCommonConfig getDefaultInstance() {
        if (defaultInstance == null) {
            synchronized (NetboxCommonConfig.class) {
                defaultInstance = new NetboxCommonConfig();
            }
        }
        return defaultInstance;
    }

    // commonParams
    private final Map<String, String> commonParams = new HashMap<String, String>();
    // commonHeaders
    private final Map<String, String> commonHeaders = new HashMap<String, String>();

    /**
     * getCommonHeaders
     *
     * @return commonHeaders
     */
    public Map<String, String> getCommonHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.putAll(commonHeaders);
        return headers;
    }

    /**
     * getCommonParams
     *
     * @return commonParams
     */
    public Map<String, String> getCommonParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(commonParams);
        return params;
    }

    /**
     * putHeader
     *
     * @param key    key
     * @param header header
     * @return config
     */
    public NetboxCommonConfig putHeader(String key, String header) {
        if (key != null) {
            if (header == null || "".equals(header)) {
                commonHeaders.remove(key);
            } else {
                commonHeaders.put(key, header);
            }
        }
        return this;
    }

    /**
     * putHeaders
     *
     * @param headers headers
     * @return config
     */
    public NetboxCommonConfig putHeaders(Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            commonHeaders.putAll(headers);
        }
        return this;
    }

    /**
     * removeHeader
     *
     * @param key key
     * @return config config
     */
    public NetboxCommonConfig removeHeader(String key) {
        if (key != null) {
            commonHeaders.remove(key);
        }
        return this;
    }

    /**
     * clearHeaders
     *
     * @return config
     */
    public NetboxCommonConfig clearHeaders() {
        commonHeaders.clear();
        return this;
    }

    /**
     * putParam
     *
     * @param key   key
     * @param param param
     * @return config
     */
    public NetboxCommonConfig putParam(String key, String param) {
        if (key != null) {
            if (param == null || "".equals(param)) {
                commonParams.remove(key);
            } else {
                commonParams.put(key, param);
            }
        }
        return this;
    }

    /**
     * putParams
     *
     * @param params params
     * @return config
     */
    public NetboxCommonConfig putParams(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            commonParams.putAll(params);
        }
        return this;
    }

    /**
     * removeParam
     *
     * @param key key
     * @return config
     */
    public NetboxCommonConfig removeParam(String key) {
        if (key != null) {
            commonParams.remove(key);
        }
        return this;
    }

    /**
     * clearParams
     *
     * @return config
     */
    public NetboxCommonConfig clearParams() {
        commonParams.clear();
        return this;
    }
}
