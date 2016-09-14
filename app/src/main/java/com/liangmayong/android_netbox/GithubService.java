package com.liangmayong.android_netbox;

import com.liangmayong.netbox.NetboxServer;
import com.liangmayong.netbox.annotations.BaseURL;
import com.liangmayong.netbox.annotations.Cache;
import com.liangmayong.netbox.annotations.Converter;
import com.liangmayong.netbox.annotations.Interceptor;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BaseURL(GithubFinal.API_URL)
@Cache(GithubCache.class)
@Converter(GithubConverter.class)
@Interceptor(GithubInterceptor.class)
public class GithubService extends NetboxServer {

}
