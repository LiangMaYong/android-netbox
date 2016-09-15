package com.liangmayong.android_netbox;

import android.content.Context;

import com.android.volley.VolleyError;
import com.liangmayong.netbox.interfaces.DefualtNetboxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.params.Parameter;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.AuthFailureError;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.NetworkError;
import com.liangmayong.netbox.throwables.ParseError;
import com.liangmayong.netbox.throwables.ServerError;
import com.liangmayong.netbox.throwables.UnkownError;
import com.liangmayong.volley.Vo;
import com.liangmayong.volley.VoError;
import com.liangmayong.volley.VoMethod;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefualtVolleyInterceptor extends DefualtNetboxInterceptor {

    @Override
    public void execRequest(Context context, final Parameter parameter, final OnNetboxListener listener) {
        VoMethod method = VoMethod.valueOf(parameter.getMethod().value());
        Vo.string(context, method, parameter.getUrl(), parameter.getParams(), parameter.getHeaders(), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Response response = new Response(parameter);
                response.setBody(s);
                listener.onResponse(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorType = VoError.getErrorType(volleyError);
                if (VoError.AUTH_FAILURE_ERROR.equals(errorType)) {
                    listener.onFailure(new AuthFailureError(volleyError));
                } else if (VoError.SERVER_ERROR.equals(errorType)) {
                    listener.onFailure(new ServerError(volleyError));
                } else if (VoError.PARSE_ERROR.equals(errorType)) {
                    listener.onFailure(new ParseError(volleyError));
                } else if (VoError.NETWORK_ERROR.equals(errorType)) {
                    listener.onFailure(new NetworkError(volleyError));
                } else {
                    listener.onFailure(new UnkownError(volleyError));
                }
            }
        });

    }

    @Override
    public Response syncRequest(Context context, Parameter parameter) throws NetboxError {
        VoMethod method = VoMethod.valueOf(parameter.getMethod().value());
        try {
            String data = Vo.stringSync(context, method, parameter.getUrl(), parameter.getParams(), parameter.getHeaders());

            Response response = new Response(parameter);
            response.setBody(data);
            return response;
        } catch (VolleyError error) {
            String errorType = VoError.getErrorType(error);
            if (VoError.AUTH_FAILURE_ERROR.equals(errorType)) {
                throw new AuthFailureError(error);
            } else if (VoError.SERVER_ERROR.equals(errorType)) {
                throw new ServerError(error);
            } else if (VoError.PARSE_ERROR.equals(errorType)) {
                throw new ParseError(error);
            } else if (VoError.NETWORK_ERROR.equals(errorType)) {
                throw new NetworkError(error);
            } else {
                throw new UnkownError(error);
            }
        }
    }

}
