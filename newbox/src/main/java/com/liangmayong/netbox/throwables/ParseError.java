package com.liangmayong.netbox.throwables;

/**
 * ParseError
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ParseError extends NetBoxError {

    private static final long serialVersionUID = 1L;

    public ParseError(Exception exception) {
        super(ErrorType.PARSE_ERROR, exception);
    }

    public ParseError(String message) {
        super(ErrorType.PARSE_ERROR, message);
    }

}
