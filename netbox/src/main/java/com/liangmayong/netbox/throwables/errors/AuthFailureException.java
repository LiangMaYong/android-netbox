package com.liangmayong.netbox.throwables.errors;

import com.liangmayong.netbox.throwables.NetboxError;

/**
 * AuthFailureException
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AuthFailureException extends NetboxError {

    private static final long serialVersionUID = 1L;

    public AuthFailureException(Exception exception) {
        super(ErrorType.AUTH_FAILURE_ERROR, exception);
    }

    public AuthFailureException(ErrorType errorType, String message) {
        super(ErrorType.AUTH_FAILURE_ERROR, message);
    }

}
