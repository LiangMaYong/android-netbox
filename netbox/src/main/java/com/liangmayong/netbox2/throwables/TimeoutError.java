package com.liangmayong.netbox2.throwables;

/**
 * TimeoutError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class TimeoutError extends NetboxError {

    private static final long serialVersionUID = 1L;

    public TimeoutError(Exception exception) {
        super(ErrorType.TIMEOUT_ERROR, exception);
    }

    public TimeoutError(String message) {
        super(ErrorType.TIMEOUT_ERROR, message);
    }

}
