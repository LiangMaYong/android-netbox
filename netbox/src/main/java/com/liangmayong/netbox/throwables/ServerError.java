package com.liangmayong.netbox.throwables;

/**
 * ServerError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ServerError extends NetboxError {

    private static final long serialVersionUID = 1L;

    public ServerError(Exception exception) {
        super(ErrorType.SERVER_ERROR, exception);
    }

    public ServerError(String message) {
        super(ErrorType.SERVER_ERROR, message);
    }

}
