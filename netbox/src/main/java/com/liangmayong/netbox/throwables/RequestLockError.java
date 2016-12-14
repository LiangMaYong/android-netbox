package com.liangmayong.netbox.throwables;

/**
 * AuthFailureError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class RequestLockError extends NetboxError {

    private static final long serialVersionUID = 1L;

    public RequestLockError(Exception exception) {
        super(ErrorType.REQUEST_LOCK_ERROR, exception);
    }

    public RequestLockError(ErrorType errorType, String message) {
        super(ErrorType.REQUEST_LOCK_ERROR, message);
    }

}
