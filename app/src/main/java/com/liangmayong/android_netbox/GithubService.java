package com.liangmayong.android_netbox;

import com.liangmayong.netbox2.NetboxServer;
import com.liangmayong.netbox2.annotations.BaseURL;
import com.liangmayong.netbox2.annotations.Cache;
import com.liangmayong.netbox2.annotations.Converter;
import com.liangmayong.netbox2.annotations.Interceptor;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BaseURL("http://192.168.158.151/")
@Cache(GithubCache.class)
@Converter(GithubConverter.class)
@Interceptor(GithubInterceptor.class)
public class GithubService extends NetboxServer {

}
