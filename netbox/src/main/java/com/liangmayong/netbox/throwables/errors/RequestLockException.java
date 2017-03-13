package com.liangmayong.netbox.throwables.errors;

import com.liangmayong.netbox.throwables.NetboxError;

/**
 * RequestLockException
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class RequestLockException extends NetboxError {

    private static final long serialVersionUID = 1L;

    public RequestLockException(Exception exception) {
        super(ErrorType.REQUEST_LOCK_ERROR, exception);
    }

    public RequestLockException(ErrorType errorType, String message) {
        super(ErrorType.REQUEST_LOCK_ERROR, message);
    }

}
