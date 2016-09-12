package com.liangmayong.reform.errors;

/**
 * ServerError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformServerError extends ReformError {

    private static final long serialVersionUID = 1L;

    public ReformServerError(Exception exception) {
        super(ErrorType.SERVER_ERROR, exception);
    }

    public ReformServerError(String message) {
        super(ErrorType.SERVER_ERROR, message);
    }

}
