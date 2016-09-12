package com.liangmayong.reform.errors;

/**
 * AuthFailureError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformAuthFailureError extends ReformError {

    private static final long serialVersionUID = 1L;

    public ReformAuthFailureError(Exception exception) {
        super(ErrorType.AUTH_FAILURE_ERROR, exception);
    }

    public ReformAuthFailureError(ErrorType errorType, String message) {
        super(ErrorType.AUTH_FAILURE_ERROR, message);
    }

}
