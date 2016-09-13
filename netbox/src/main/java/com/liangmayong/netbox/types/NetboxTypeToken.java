package com.liangmayong.netbox.types;

import com.liangmayong.netbox.utils.NetboxUtils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/13.
 */
public class NetboxTypeToken<T> {

    public Type getType() {
        return NetboxUtils.getGenericType(this, 0);
    }

}
