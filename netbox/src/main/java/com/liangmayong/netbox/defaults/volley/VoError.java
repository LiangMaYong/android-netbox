package com.liangmayong.netbox.defaults.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by liangmayong on 2016/9/15.
 */
public class VoError {
    /**
     * if doing a HTTP authentication, this error may occur.
     */
    public static final String AUTH_FAILURE_ERROR = "AUTH_FAILURE_ERROR";
    /**
     * Socket closed, servers, DNS error will produce the error.
     */
    public static final String NETWORK_ERROR = "NETWORK_ERROR";
    /**
     * When using JsonObjectRequest or JsonArrayRequest, if received JSON is
     * abnormal, can produce abnormal.
     */
    public static final String PARSE_ERROR = "PARSE_ERROR";
    /**
     * A mistake of the server's response, most likely 4 or 5 xx xx HTTP status
     * code.
     */
    public static final String SERVER_ERROR = "SERVER_ERROR";
    /**
     * The Socket timeout, the server is too busy or network latency can produce
     * this exception. By default, a Volley of timeout time of 2.5 seconds. If
     * you get this error RetryPolicy may be used.
     */
    public static final String TIMEOUT_ERROR = "TIMEOUT_ERROR";
    /**
     * Other unknown error
     */
    public static final String UNKOWN_ERROR = "UNKOWN_ERROR";

    /**
     * Returns appropriate message which is to be displayed to the user against
     * the specified error object.
     *
     * @param error volleyError
     * @return error type string
     */
    public static String getErrorType(VolleyError error) {
        if (error instanceof TimeoutError) {
            return TIMEOUT_ERROR;
        } else if (error instanceof AuthFailureError) {
            return AUTH_FAILURE_ERROR;
        } else if (error instanceof ServerError) {
            return SERVER_ERROR;
        } else if (error instanceof ParseError) {
            return PARSE_ERROR;
        } else if (error instanceof NoConnectionError) {
            return NETWORK_ERROR;
        }
        return UNKOWN_ERROR;
    }
}
