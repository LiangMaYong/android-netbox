package com.liangmayong.netbox.annotations;

import com.liangmayong.netbox.interfaces.NetBoxInterceptor;
import com.liangmayong.reform.interfaces.ReformInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interceptor
 *
 * @author LiangMaYong
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptor {
    Class<? extends NetBoxInterceptor> value();
}
