package com.liangmayong.netbox.interfaces;

/**
 * Created by LiangMaYong on 2017/3/13.
 */
public interface NetboxLogerListener {

    String getTag();

    void log(String message, Throwable throwable);

}
