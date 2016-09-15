package com.liangmayong.netbox.annotations;

import com.liangmayong.netbox.interfaces.NetboxConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liangmayong on 2016/9/12.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindConverter {
    Class<? extends NetboxConverter> value();

    String note() default "";
}
