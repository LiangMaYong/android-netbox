package com.liangmayong.netbox.annotations;

import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.reform.interfaces.ReformConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liangmayong on 2016/9/12.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Converter {
    Class<? extends NetBoxConverter> value();
}
