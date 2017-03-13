package com.liangmayong.android_netbox;

import com.liangmayong.netbox.annotations.Key;
import com.liangmayong.netbox.annotations.KeyFile;
import com.liangmayong.netbox.annotations.KeyMap;
import com.liangmayong.netbox.annotations.Mod;
import com.liangmayong.netbox.annotations.Path;
import com.liangmayong.netbox.callbacks.OnNetboxListener;
import com.liangmayong.netbox.params.Method;
import com.liangmayong.netbox.params.FileParam;

import java.util.Map;

/**
 * Created by LiangMaYong on 2016/12/15.
 */

public interface DemoInterface {

    @Mod(Method.POST)
    @Path("./uploadFile.php")
    void uploadFile(@KeyFile("data") FileParam file, OnNetboxListener listener);

    @Mod(Method.POST)
    @Path("./login.php")
    void login(@Key("username") String username, @Key("password") String password, @KeyMap("profile") Map<String, String> profile, OnNetboxListener listener);
}
