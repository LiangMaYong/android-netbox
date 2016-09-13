package com.liangmayong.netbox2.throwables;

/**
 * AuthFailureError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class AuthFailureError extends NetboxError {

    private static final long serialVersionUID = 1L;

    public AuthFailureError(Exception exception) {
        super(ErrorType.AUTH_FAILURE_ERROR, exception);
    }

    public AuthFailureError(ErrorType errorType, String message) {
        super(ErrorType.AUTH_FAILURE_ERROR, message);
    }

}
