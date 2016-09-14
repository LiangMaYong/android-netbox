package com.liangmayong.netbox.types;

import com.liangmayong.netbox.utils.NetboxUtils;

import java.lang.reflect.Type;

/**
 * Created by liangmayong on 2016/9/13.
 */
public abstract class NetboxTypeToken<T> {

    public Type getType() {
        return NetboxUtils.getGenericType(this, 0);
    }

}
