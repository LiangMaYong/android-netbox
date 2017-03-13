package com.liangmayong.netbox.throwables.errors;

import com.liangmayong.netbox.throwables.NetboxError;

/**
 * NetworkException
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class NetworkException extends NetboxError {

    private static final long serialVersionUID = 1L;

    public NetworkException(Exception exception) {
        super(ErrorType.NETWORK_ERROR, exception);
    }

    public NetworkException(ErrorType errorType, String message) {
        super(ErrorType.NETWORK_ERROR, message);
    }

}
