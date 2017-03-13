package com.liangmayong.netbox.throwables.errors;

import com.liangmayong.netbox.throwables.NetboxError;

/**
 * ServerException
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ServerException extends NetboxError {

    private static final long serialVersionUID = 1L;

    public ServerException(Exception exception) {
        super(ErrorType.SERVER_ERROR, exception);
    }

    public ServerException(String message) {
        super(ErrorType.SERVER_ERROR, message);
    }

}
