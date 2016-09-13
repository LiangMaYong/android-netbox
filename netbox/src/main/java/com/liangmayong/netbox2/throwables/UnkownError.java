package com.liangmayong.netbox2.throwables;

/**
 * UnkownError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class UnkownError extends NetboxError {

    private static final long serialVersionUID = 1L;

    public UnkownError(Exception exception) {
        super(ErrorType.UNKOWN_ERROR, exception);
    }

    public UnkownError(String message) {
        super(ErrorType.UNKOWN_ERROR, message);
    }

}
