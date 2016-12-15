package com.liangmayong.android_netbox;

import com.liangmayong.netbox.annotations.Key;
import com.liangmayong.netbox.annotations.Mod;
import com.liangmayong.netbox.annotations.Param;
import com.liangmayong.netbox.annotations.Path;
import com.liangmayong.netbox.interfaces.Method;
import com.liangmayong.netbox.interfaces.OnNetboxListener;

/**
 * Created by LiangMaYong on 2016/12/15.
 */

public interface DemoInterface {

    @Mod(Method.GET)
    @Path("")
    @Param(key = {"key1", "value1"}, value = {"key2", "value2"})
    void demo(@Key("key3") String key, OnNetboxListener listener);
}
