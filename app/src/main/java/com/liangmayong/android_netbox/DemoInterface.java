package com.liangmayong.android_netbox;

import com.liangmayong.netbox.annotations.File;
import com.liangmayong.netbox.annotations.Mod;
import com.liangmayong.netbox.annotations.Path;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.params.FileParam;
import com.liangmayong.netbox.params.Method;

/**
 * Created by LiangMaYong on 2016/12/15.
 */

public interface DemoInterface {

    @Mod(Method.POST)
    @Path("./uploadFile.php")
    void list(@File("data") FileParam file, OnNetboxListener listener);
}
