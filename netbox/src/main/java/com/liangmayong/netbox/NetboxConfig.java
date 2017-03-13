package com.liangmayong.netbox;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public final class NetboxConfig {

    private NetboxConfig() {
    }

    // NET_BOX_CONFIG_MAP
    private static final Map<String, NetboxConfig> NET_BOX_CONFIG_MAP = new HashMap<String, NetboxConfig>();

    private static volatile NetboxConfig defaultInstance = null;

    public static NetboxConfig getDefaultInstance() {
        if (defaultInstance == null) {
            synchronized (NetboxConfig.class) {
                defaultInstance = new NetboxConfig();
            }
        }
        return defaultInstance;
    }

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
            config.setParent(getDefaultInstance());
            NET_BOX_CONFIG_MAP.put(key, config);
        }
        return config;
    }

    // parent
    private NetboxConfig parent = null;
    // commonParams
    private final Map<String, String> commonParams = new HashMap<String, String>();
    // commonHeaders
    private final Map<String, String> commonHeaders = new HashMap<String, String>();
    // debugable
    private boolean debugable = false;
    // releaseURL
    private String debugURL = "";
    // releaseURL
    private String releaseURL = "";

    public String getDebugURL() {
        if (debugURL == null) {
            return "";
        }
        return debugURL;
    }

    public void setDebugURL(String debugURL) {
        this.debugURL = debugURL;
    }

    public String getReleaseURL() {
        if (releaseURL == null) {
            return "";
        }
        return releaseURL;
    }

    public void setReleaseURL(String releaseURL) {
        this.releaseURL = releaseURL;
    }

    /**
     * setParent
     *
     * @param parent parent
     */
    public void setParent(NetboxConfig parent) {
        this.parent = parent;
    }

    /**
     * isDebugable
     *
     * @return debugable
     */
    public boolean isDebugable() {
        if (parent != null) {
            if (parent.isDebugable()) {
                return true;
            }
        }
        return debugable;
    }

    /**
     * setDebugable
     *
     * @param debugable debugable
     */
    public void setDebugable(boolean debugable) {
        this.debugable = debugable;
    }

    /**
     * getCommonHeaders
     *
     * @return commonHeaders
     */
    public Map<String, String> getCommonHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        if (parent != null) {
            headers.putAll(parent.getCommonHeaders());
        }
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
        if (parent != null) {
            params.putAll(parent.getCommonParams());
        }
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
    public NetboxConfig putHeader(String key, String header) {
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
    public NetboxConfig putHeaders(Map<String, String> headers) {
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
    public NetboxConfig removeHeader(String key) {
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
    public NetboxConfig clearHeaders() {
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
    public NetboxConfig putParam(String key, String param) {
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
    public NetboxConfig putParams(Map<String, String> params) {
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
    public NetboxConfig removeParam(String key) {
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
    public NetboxConfig clearParams() {
        commonParams.clear();
        return this;
    }
}
