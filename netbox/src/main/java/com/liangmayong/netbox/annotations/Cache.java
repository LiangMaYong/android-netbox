package com.liangmayong.netbox.annotations;

import com.liangmayong.netbox.interfaces.NetboxCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    Class<? extends NetboxCache> value();
}
