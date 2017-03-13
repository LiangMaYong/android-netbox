package com.liangmayong.netbox.defaults;

import android.content.Context;

import com.android.volley.VolleyError;
import com.liangmayong.netbox.callbacks.OnNetboxListener;
import com.liangmayong.netbox.defaults.abstracts.AbstractDefaultNetboxInterceptor;
import com.liangmayong.netbox.params.FileParam;
import com.liangmayong.netbox.params.Request;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.errors.AuthFailureException;
import com.liangmayong.netbox.throwables.NetboxError;
import com.liangmayong.netbox.throwables.errors.NetworkException;
import com.liangmayong.netbox.throwables.errors.ParseException;
import com.liangmayong.netbox.throwables.errors.ServerException;
import com.liangmayong.netbox.throwables.errors.UnkownException;
import com.liangmayong.netbox.defaults.volley.Vo;
import com.liangmayong.netbox.defaults.volley.VoError;
import com.liangmayong.netbox.defaults.volley.VoFile;
import com.liangmayong.netbox.defaults.volley.VoMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/13.
 */
public class DefaultVolleyInterceptor extends AbstractDefaultNetboxInterceptor {

    @Override
    public void execRequest(Context context, final Request request, final OnNetboxListener listener) {
        VoMethod method = VoMethod.valueOf(request.getMethod().value());
        Map<String, VoFile> files = new HashMap<>();
        if (request.getFiles() != null && !request.getFiles().isEmpty()) {
            for (Map.Entry<String, FileParam> entry : request.getFiles().entrySet()) {
                files.put(entry.getKey(), new VoFile(entry.getValue().getName(), entry.getValue().getPath()));
            }
        }
        Vo.string(context, method, request.getUrl(), request.getParams(), request.getHeaders(), files, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Response response = new Response(request);
                response.setBody(s);
                listener.onResponse(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorType = VoError.getErrorType(volleyError);
                if (VoError.AUTH_FAILURE_ERROR.equals(errorType)) {
                    listener.onFailure(new AuthFailureException(volleyError));
                } else if (VoError.SERVER_ERROR.equals(errorType)) {
                    listener.onFailure(new ServerException(volleyError));
                } else if (VoError.PARSE_ERROR.equals(errorType)) {
                    listener.onFailure(new ParseException(volleyError));
                } else if (VoError.NETWORK_ERROR.equals(errorType)) {
                    listener.onFailure(new NetworkException(volleyError));
                } else {
                    listener.onFailure(new UnkownException(volleyError));
                }
            }
        });

    }

    @Override
    public Response syncRequest(Context context, Request request) throws NetboxError {
        VoMethod method = VoMethod.valueOf(request.getMethod().value());
        try {
            Map<String, VoFile> files = new HashMap<>();
            if (request.getFiles() != null && !request.getFiles().isEmpty()) {
                for (Map.Entry<String, FileParam> entry : request.getFiles().entrySet()) {
                    files.put(entry.getKey(), new VoFile(entry.getValue().getName(), entry.getValue().getPath()));
                }
            }
            String data = Vo.stringSync(context, method, request.getUrl(), request.getParams(), request.getHeaders(), files);
            Response response = new Response(request);
            response.setBody(data);
            return response;
        } catch (VolleyError error) {
            String errorType = VoError.getErrorType(error);
            if (VoError.AUTH_FAILURE_ERROR.equals(errorType)) {
                throw new AuthFailureException(error);
            } else if (VoError.SERVER_ERROR.equals(errorType)) {
                throw new ServerException(error);
            } else if (VoError.PARSE_ERROR.equals(errorType)) {
                throw new ParseException(error);
            } else if (VoError.NETWORK_ERROR.equals(errorType)) {
                throw new NetworkException(error);
            } else {
                throw new UnkownException(error);
            }
        }
    }

    @Override
    public void destroyRequest(Context context) {
        Vo.destroy(context);
    }
}
