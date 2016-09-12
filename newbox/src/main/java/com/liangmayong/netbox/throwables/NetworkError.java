package com.liangmayong.netbox.throwables;

/**
 * NetworkError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class NetworkError extends NetBoxError {

    private static final long serialVersionUID = 1L;

    public NetworkError(Exception exception) {
        super(ErrorType.NETWORK_ERROR, exception);
    }

    public NetworkError(ErrorType errorType, String message) {
        super(ErrorType.NETWORK_ERROR, message);
    }

}
