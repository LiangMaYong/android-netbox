package com.liangmayong.reform.errors;

/**
 * NetworkError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformNetworkError extends ReformError {

    private static final long serialVersionUID = 1L;

    public ReformNetworkError(Exception exception) {
        super(ErrorType.NETWORK_ERROR, exception);
    }

    public ReformNetworkError(ErrorType errorType, String message) {
        super(ErrorType.NETWORK_ERROR, message);
    }

}
