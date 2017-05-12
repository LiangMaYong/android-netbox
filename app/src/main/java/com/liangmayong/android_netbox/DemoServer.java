package com.liangmayong.android_netbox;

import com.liangmayong.netbox.annotations.BindParams;
import com.liangmayong.netbox.annotations.BindURL;
import com.liangmayong.netbox.defaults.DefaultVolleyServer;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BindURL(value = "http://115.28.242.220/expo_api/index.php/api")
@BindParams(key = "common_key", value = "common_value")
public class DemoServer extends DefaultVolleyServer {

}
