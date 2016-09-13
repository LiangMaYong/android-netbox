package com.liangmayong.netbox2.annotations;

import com.liangmayong.netbox2.interfaces.NetboxConverter;

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
    Class<? extends NetboxConverter> value();
}
