package com.liangmayong.android_netbox;

import com.liangmayong.netbox.annotations.BindDebugable;
import com.liangmayong.netbox.annotations.BindParams;
import com.liangmayong.netbox.annotations.BindURL;
import com.liangmayong.netbox.defaults.DefaultVolleyServer;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BindURL(value = "http://115.28.242.220/expo_api/index.php/api", debug = "http://115.28.242.220/expo_api/index.php/api")
@BindParams(key = "appkey", value = "sssssssssssssssss")
@BindDebugable(true)
public class DemoServer extends DefaultVolleyServer {

}
