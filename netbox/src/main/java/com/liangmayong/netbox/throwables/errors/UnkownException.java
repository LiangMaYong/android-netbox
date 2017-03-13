package com.liangmayong.netbox.throwables.errors;

import com.liangmayong.netbox.throwables.NetboxError;

/**
 * UnkownException
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class UnkownException extends NetboxError {

    private static final long serialVersionUID = 1L;

    public UnkownException(Exception exception) {
        super(ErrorType.UNKOWN_ERROR, exception);
    }

    public UnkownException(String message) {
        super(ErrorType.UNKOWN_ERROR, message);
    }

}
