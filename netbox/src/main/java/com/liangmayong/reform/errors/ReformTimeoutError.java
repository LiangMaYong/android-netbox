package com.liangmayong.reform.errors;

/**
 * TimeoutError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformTimeoutError extends ReformError {

    private static final long serialVersionUID = 1L;

    public ReformTimeoutError(Exception exception) {
        super(ErrorType.TIMEOUT_ERROR, exception);
    }

    public ReformTimeoutError(String message) {
        super(ErrorType.TIMEOUT_ERROR, message);
    }

}
