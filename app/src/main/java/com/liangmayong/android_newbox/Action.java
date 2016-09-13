package com.liangmayong.android_newbox;

import com.liangmayong.netbox.NetboxAction;
import com.liangmayong.netbox.annotations.BaseURL;
import com.liangmayong.netbox.interfaces.NetboxConverter;
import com.liangmayong.netbox.interfaces.NetboxInterceptor;

/**
 * Created by liangmayong on 2016/9/13.
 */
@BaseURL("http://192.168.158.151/")
public class Action extends NetboxAction {

    @Override
    protected Class<? extends NetboxConverter> generateConverterType() {
        return ActionCon.class;
    }

    @Override
    protected Class<? extends NetboxInterceptor> generateInterceptorType() {
        return ActionInt.class;
    }

}
