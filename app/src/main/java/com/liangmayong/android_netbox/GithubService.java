package com.liangmayong.android_netbox;

import com.liangmayong.netbox.NetboxServer;
import com.liangmayong.netbox.annotations.BaseURL;
import com.liangmayong.netbox.annotations.Cache;
import com.liangmayong.netbox.annotations.Converter;
import com.liangmayong.netbox.annotations.DebugURL;
import com.liangmayong.netbox.annotations.Debugable;
import com.liangmayong.netbox.annotations.Interceptor;

/**
 * Created by liangmayong on 2016/9/13.
 */
@Cache(GithubCache.class)
@Converter(GithubConverter.class)
@Interceptor(GithubInterceptor.class)
@DebugURL("http://127.0.0.1/{path}/api")
@BaseURL("https://github.com/{path}/api")
@Debugable
public class GithubService extends NetboxServer {

}
