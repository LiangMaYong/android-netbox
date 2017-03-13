package com.liangmayong.netbox.throwables.errors;

import com.liangmayong.netbox.throwables.NetboxError;

/**
 * ParseException
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ParseException extends NetboxError {

    private static final long serialVersionUID = 1L;

    public ParseException(Exception exception) {
        super(ErrorType.PARSE_ERROR, exception);
    }

    public ParseException(String message) {
        super(ErrorType.PARSE_ERROR, message);
    }

}
