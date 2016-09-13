package com.liangmayong.reform.errors;

/**
 * ParseError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ReformParseError extends ReformError {

    private static final long serialVersionUID = 1L;

    public ReformParseError(Exception exception) {
        super(ErrorType.PARSE_ERROR, exception);
    }

    public ReformParseError(String message) {
        super(ErrorType.PARSE_ERROR, message);
    }

}
