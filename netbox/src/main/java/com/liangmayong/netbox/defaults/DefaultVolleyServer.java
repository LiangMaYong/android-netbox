package com.liangmayong.netbox.defaults;

import com.liangmayong.netbox.NetboxServer;
import com.liangmayong.netbox.annotations.BindCache;
import com.liangmayong.netbox.annotations.BindConverter;
import com.liangmayong.netbox.annotations.BindInterceptor;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BindCache(DefaultDatabaseCache.class)
@BindConverter(DefaultGsonConverter.class)
@BindInterceptor(DefaultVolleyInterceptor.class)
public abstract class DefaultVolleyServer extends NetboxServer {
}
