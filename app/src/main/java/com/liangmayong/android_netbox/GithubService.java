package com.liangmayong.android_netbox;

import com.liangmayong.netbox.NetboxServer;
import com.liangmayong.netbox.annotations.BindCache;
import com.liangmayong.netbox.annotations.BindConverter;
import com.liangmayong.netbox.annotations.BindDebugable;
import com.liangmayong.netbox.annotations.BindInterceptor;
import com.liangmayong.netbox.annotations.BindURL;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BindCache(DefualtHoldCache.class)
@BindConverter(DefualtGsonConverter.class)
@BindInterceptor(DefualtVolleyInterceptor.class)
@BindURL(value = "https://github.com/api", debug = "http://115.28.242.220/apidesigner/api")
@BindDebugable(true)
public class GithubService extends NetboxServer {

}
