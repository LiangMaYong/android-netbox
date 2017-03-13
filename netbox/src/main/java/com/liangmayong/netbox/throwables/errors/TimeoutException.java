package com.liangmayong.netbox.throwables.errors;

import com.liangmayong.netbox.throwables.NetboxError;

/**
 * TimeoutException
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class TimeoutException extends NetboxError {

    private static final long serialVersionUID = 1L;

    public TimeoutException(Exception exception) {
        super(ErrorType.TIMEOUT_ERROR, exception);
    }

    public TimeoutException(String message) {
        super(ErrorType.TIMEOUT_ERROR, message);
    }

}
