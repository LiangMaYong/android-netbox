package com.liangmayong.netbox.throwables;

/**
 * NetboxError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class NetboxError extends Exception {

    private static final long serialVersionUID = 1L;

    public enum ErrorType {
        AUTH_FAILURE_ERROR, NETWORK_ERROR, PARSE_ERROR, SERVER_ERROR, TIMEOUT_ERROR, UNKOWN_ERROR, REQUEST_LOCK_ERROR
    }

    private ErrorType errorType = ErrorType.UNKOWN_ERROR;
    private Object data = null;
    private Exception exception = null;

    public NetboxError(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public NetboxError(ErrorType errorType, Exception exception) {
        super();
        this.exception = exception;
        this.errorType = errorType;
    }

    /**
     * getData
     *
     * @return data
     */
    public Object getData() {
        return data;
    }

    /**
     * setData
     *
     * @param data data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * getErrorType
     *
     * @return errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * getException
     *
     * @return exception
     */
    public Exception getException() {
        return exception;
    }
}
