package com.liangmayong.netbox.defualt;

import com.liangmayong.netbox.NetboxServer;
import com.liangmayong.netbox.annotations.BindCache;
import com.liangmayong.netbox.annotations.BindConverter;
import com.liangmayong.netbox.annotations.BindInterceptor;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BindCache(DefualtCache.class)
@BindConverter(DefualtConverter.class)
@BindInterceptor(DefualtInterceptor.class)
public abstract class DefualtServer extends NetboxServer {
}
