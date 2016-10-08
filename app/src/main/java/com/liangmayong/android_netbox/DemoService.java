package com.liangmayong.android_netbox;

import com.liangmayong.netbox.NetboxServer;
import com.liangmayong.netbox.annotations.BindCache;
import com.liangmayong.netbox.annotations.BindConverter;
import com.liangmayong.netbox.annotations.BindDebugable;
import com.liangmayong.netbox.annotations.BindInterceptor;
import com.liangmayong.netbox.annotations.BindURL;
import com.liangmayong.netbox.defualt.DefualtServer;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BindURL(value = "http://115.28.242.220/expo_api/index.php/api", debug = "http://115.28.242.220/expo_api/index.php/api")
@BindDebugable(true)
public class DemoService extends DefualtServer {

}
