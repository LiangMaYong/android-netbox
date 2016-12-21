package com.liangmayong.netbox.defaults;

import android.content.Context;

import com.android.volley.VolleyError;
import com.liangmayong.netbox.interfaces.DefaultNetboxInterceptor;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.params.FileParam;
import com.liangmayong.netbox.params.Request;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.AuthFailureError;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.NetworkError;
import com.liangmayong.netbox.throwables.ParseError;
import com.liangmayong.netbox.throwables.ServerError;
import com.liangmayong.netbox.throwables.UnkownError;
import com.liangmayong.netbox.volley.Vo;
import com.liangmayong.netbox.volley.VoError;
import com.liangmayong.netbox.volley.VoFile;
import com.liangmayong.netbox.volley.VoMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefaultVolleyInterceptor extends DefaultNetboxInterceptor {

    @Override
    public void execRequest(Context context, final Request parameter, final OnNetboxListener listener) {
        VoMethod method = VoMethod.valueOf(parameter.getMethod().value());
        Map<String, VoFile> files = new HashMap<>();
        if (parameter.getFiles() != null && !parameter.getFiles().isEmpty()) {
            for (Map.Entry<String, FileParam> entry : parameter.getFiles().entrySet()) {
                files.put(entry.getKey(), new VoFile(entry.getValue().getName(), entry.getValue().getPath()));
            }
        }
        Vo.string(context, method, parameter.getUrl(), parameter.getParams(), parameter.getHeaders(), files, new com.android.volley.Response.Listener<String>() {
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
    public Response syncRequest(Context context, Request parameter) throws NetboxError {
        VoMethod method = VoMethod.valueOf(parameter.getMethod().value());
        try {
            Map<String, VoFile> files = new HashMap<>();
            if (parameter.getFiles() != null && !parameter.getFiles().isEmpty()) {
                for (Map.Entry<String, FileParam> entry : parameter.getFiles().entrySet()) {
                    files.put(entry.getKey(), new VoFile(entry.getValue().getName(), entry.getValue().getPath()));
                }
            }
            String data = Vo.stringSync(context, method, parameter.getUrl(), parameter.getParams(), parameter.getHeaders(), files);
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

    @Override
    public void destroyRequest(Context context) {
        Vo.destroy(context);
    }
}
