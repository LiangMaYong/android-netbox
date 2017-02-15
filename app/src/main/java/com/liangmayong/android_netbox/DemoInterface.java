package com.liangmayong.android_netbox;

import com.liangmayong.netbox.annotations.KeyFile;
import com.liangmayong.netbox.annotations.Key;
import com.liangmayong.netbox.annotations.Mod;
import com.liangmayong.netbox.annotations.Path;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.params.Method;

/**
 * Created by LiangMaYong on 2016/12/15.
 */

public interface DemoInterface {

    @Mod(Method.POST)
    @Path("./uploadFile.php")
    void uploadFile(@KeyFile("data") com.liangmayong.netbox.params.ParamFile file, OnNetboxListener listener);

    @Mod(Method.POST)
    @Path("./login.php")
    void login(@Key("username") String username, @Key("password") String password, OnNetboxListener listener);
}
