package com.liangmayong.android_newbox;

import com.liangmayong.netbox.NetBoxAction;
import com.liangmayong.netbox.annotations.BaseURL;
import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.netbox.interfaces.NetBoxInterceptor;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BaseURL("http://192.168.158.151/")
public class Action extends NetBoxAction {

    @Override
    protected Class<? extends NetBoxConverter> generateConverterType() {
        return ActionCon.class;
    }

    @Override
    protected Class<? extends NetBoxInterceptor> generateInterceptorType() {
        return ActionInt.class;
    }

}
