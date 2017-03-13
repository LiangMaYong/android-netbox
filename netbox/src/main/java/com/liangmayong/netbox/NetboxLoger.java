package com.liangmayong.netbox;

import android.util.Log;

import com.liangmayong.netbox.interfaces.NetboxLogerListener;
import com.liangmayong.netbox.utils.NetboxUtils;

/**
 * Created by LiangMaYong on 2017/3/13.
 */
public class NetboxLoger {

    private static volatile NetboxLoger ourInstance = null;

    public static NetboxLoger getInstance() {
        if (ourInstance == null) {
            synchronized (NetboxLoger.class) {
                ourInstance = new NetboxLoger();
            }
        }
        return ourInstance;
    }

    private NetboxLoger() {
    }

    private NetboxLogerListener logerListener = new NetboxLogerListener() {
        @Override
        public String getTag() {
            return "Netbox";
        }

        @Override
        public void log(String message, Throwable throwable) {
            if (NetboxUtils.isDebugable()) {
                if (throwable == null) {
                    Log.d(getTag(), message);
                } else {
                    Log.d(getTag(), message, throwable);
                }
            }
        }
    };

    public void setLogerListener(NetboxLogerListener logerListener) {
        this.logerListener = logerListener;
    }


    /**
     * debugLog
     *
     * @param message   message
     * @param throwable throwable
     */
    public void debugLog(String message, Throwable throwable) {
        if (logerListener != null) {
            logerListener.log(message, throwable);
        }
    }
}
