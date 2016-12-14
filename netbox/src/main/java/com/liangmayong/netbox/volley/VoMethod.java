package com.liangmayong.netbox.volley;

/**
 * Created by liangmayong on 2016/9/15.
 */
public enum VoMethod {

    DEPRECATED_GET_OR_POST(-1), GET(0), POST(1), PUT(2), DELETE(3), HEAD(4), OPTIONS(5), TRACE(6), PATCH(7);
    private int value = 0;

    private VoMethod(int value) {
        this.value = value;
    }

    public static VoMethod valueOf(int value) {
        switch (value) {
            case 0:
                return GET;
            case 1:
                return POST;
            case 2:
                return PUT;
            case 3:
                return DELETE;
            case 4:
                return HEAD;
            case 5:
                return OPTIONS;
            case 6:
                return TRACE;
            case 7:
                return PATCH;
            default:
                return DEPRECATED_GET_OR_POST;
        }
    }

    public int value() {
        return this.value;
    }
}
