package com.liangmayong.reform.errors;

/**
 * NetBoxError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformError extends Exception {

	private static final long serialVersionUID = 1L;

	public static enum ErrorType {
		AUTH_FAILURE_ERROR, NETWORK_ERROR, PARSE_ERROR, SERVER_ERROR, TIMEOUT_ERROR, UNKOWN_ERROR
	}

	private ErrorType errorType = ErrorType.UNKOWN_ERROR;
	private Object object = null;
	private Exception exception = null;

	public ReformError(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	public ReformError(ErrorType errorType, Exception exception) {
		super();
		this.exception = exception;
		this.errorType = errorType;
	}

	/**
	 * getObject
	 * 
	 * @return object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * setObject
	 * 
	 * @param object
	 *            object
	 */
	public void setObject(Object object) {
		this.object = object;
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
