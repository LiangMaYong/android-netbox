package com.liangmayong.reform.errors;

/**
 * UnkownError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformUnkownError extends ReformError {

    private static final long serialVersionUID = 1L;

    public ReformUnkownError(Exception exception) {
        super(ErrorType.UNKOWN_ERROR, exception);
    }

    public ReformUnkownError(String message) {
        super(ErrorType.UNKOWN_ERROR, message);
    }

}
