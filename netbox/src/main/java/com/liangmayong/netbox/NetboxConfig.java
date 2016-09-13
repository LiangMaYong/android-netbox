package com.liangmayong.netbox;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class NetboxConfig {

    private NetboxConfig() {
    }

    // NET_BOX_CONFIG_MAP
    private static final Map<String, NetboxConfig> NET_BOX_CONFIG_MAP = new HashMap<String, NetboxConfig>();

    /**
     * getInstance
     *
     * @param serverType serverType
     * @return config
     */
    public static NetboxConfig getInstance(Class<? extends NetboxServer> serverType) {
        String key = serverType.getName();
        if (NET_BOX_CONFIG_MAP.containsKey(key)) {
            return NET_BOX_CONFIG_MAP.get(key);
        }
        NetboxConfig config = null;
        synchronized (NetboxConfig.class) {
            config = new NetboxConfig();
            NET_BOX_CONFIG_MAP.put(key, config);
        }
        return config;
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
        return commonHeaders;
    }

    /**
     * getCommonParams
     *
     * @return commonParams
     */
    public Map<String, String> getCommonParams() {
        return commonParams;
    }

    /**
     * putHeader
     *
     * @param key    key
     * @param header header
     * @return config
     */
    public NetboxConfig putHeader(String key, String header) {
        if (key != null) {
            if (header == null || "".equals(header)) {
                getCommonHeaders().remove(key);
            } else {
                getCommonHeaders().put(key, header);
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
    public NetboxConfig putHeaders(Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            getCommonHeaders().putAll(headers);
        }
        return this;
    }

    /**
     * removeHeader
     *
     * @param key key
     * @return config config
     */
    public NetboxConfig removeHeader(String key) {
        if (key != null) {
            getCommonHeaders().remove(key);
        }
        return this;
    }

    /**
     * clearHeaders
     *
     * @return config
     */
    public NetboxConfig clearHeaders() {
        getCommonHeaders().clear();
        return this;
    }

    /**
     * putParam
     *
     * @param key   key
     * @param param param
     * @return config
     */
    public NetboxConfig putParam(String key, String param) {
        if (key != null) {
            if (param == null || "".equals(param)) {
                getCommonParams().remove(key);
            } else {
                getCommonParams().put(key, param);
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
    public NetboxConfig putParams(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            getCommonParams().putAll(params);
        }
        return this;
    }

    /**
     * removeParam
     *
     * @param key key
     * @return config
     */
    public NetboxConfig removeParam(String key) {
        if (key != null) {
            getCommonParams().remove(key);
        }
        return this;
    }

    /**
     * clearParams
     *
     * @return config
     */
    public NetboxConfig clearParams() {
        getCommonParams().clear();
        return this;
    }
}
