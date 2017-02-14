package com.liangmayong.netbox.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by liangmayong on 2016/9/14.
 */
public class Vo {

    private static final int REQUEST_OUTTIME = 5000;
    private static final String TAG = "VOLLEY_UTILS_REQUEST_QUEUE";
    private static final Map<String, RequestQueue> CONTEXT_REQUEST_QUEUE_MAP = new HashMap<String, RequestQueue>();
    private static final String ENCODE = "UTF-8";
    private static RetryPolicy mRetryPolicy = null;

    /**
     * string
     *
     * @param context          context
     * @param method           method
     * @param url              url
     * @param params           params
     * @param headers          headers
     * @param responseListener responseListener
     * @param errorListener    errorListener
     */
    public static void string(Context context, VoMethod method, String url, final Map<String, String> params,
                              final Map<String, String> headers, final Map<String, VoFile> files, Response.Listener<String> responseListener,
                              Response.ErrorListener errorListener) {
        RequestQueue mQueue = generateQueue(context);
        VoStringRequest request = new VoStringRequest(method.value(), url, responseListener,
                errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }

            @Override
            protected Map<String, VoFile> getFiles() {
                return files;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers != null) {
                    return headers;
                }
                return super.getHeaders();
            }
        };
        request.setShouldCache(false);
        request.setTag(TAG);
        request.setRetryPolicy(generatePolicy());
        mQueue.add(request);
    }

    /**
     * stringSync
     *
     * @param context context
     * @param method  method
     * @param url     url
     * @param params  params
     * @param headers headers
     * @return string
     * @throws Exception
     */
    public static String stringSync(Context context, VoMethod method, String url, final Map<String, String> params,
                                    final Map<String, String> headers, final Map<String, VoFile> files) throws VolleyError {
        RequestFuture<String> future = RequestFuture.newFuture();
        RequestQueue mQueue = generateQueue(context);
        VoStringRequest request = new VoStringRequest(method.value(), url, future,
                future) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }

            @Override
            protected Map<String, VoFile> getFiles() {
                return files;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers != null) {
                    return headers;
                }
                return super.getHeaders();
            }
        };
        request.setShouldCache(false);
        request.setTag(TAG);
        request.setRetryPolicy(generatePolicy());
        mQueue.add(request);
        try {
            return future.get(REQUEST_OUTTIME, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new VolleyError(e);
        } catch (ExecutionException e) {
            throw new VolleyError(e);
        } catch (TimeoutException e) {
            throw new TimeoutError();
        }
    }

    /**
     * json
     *
     * @param context          context
     * @param method           method
     * @param url              context
     * @param responseListener responseListener
     * @param errorListener    errorListener
     */
    public static void json(Context context, VoMethod method, String url, JSONObject json,
                            Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue mQueue = generateQueue(context);
        VoJsonRequest request = new VoJsonRequest(method.value(), url, json, responseListener,
                errorListener);
        request.setShouldCache(false);
        request.setTag(TAG);
        request.setRetryPolicy(generatePolicy());
        mQueue.add(request);
    }


    /**
     * jsonSync
     *
     * @param context context
     * @param method  method
     * @param url     url
     * @param json    json  @return json
     */
    public static JSONObject jsonSync(Context context, VoMethod method, String url, JSONObject json) throws VolleyError {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        VoJsonRequest request = new VoJsonRequest(method.value(), url, json, future,
                future);
        request.setShouldCache(false);
        request.setTag(TAG);
        request.setRetryPolicy(generatePolicy());
        generateQueue(context).add(request);
        try {
            return future.get(REQUEST_OUTTIME, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new VolleyError(e);
        } catch (ExecutionException e) {
            throw new VolleyError(e);
        } catch (TimeoutException e) {
            throw new VolleyError(e);
        }
    }

    /**
     * destroy request
     *
     * @param context context
     */

    public static void destroy(Context context) {
        if (context == null) {
            return;
        }
        try {
            String key = context.getClass().getName() + "@" + context.hashCode();
            if (CONTEXT_REQUEST_QUEUE_MAP.containsKey(key)) {
                RequestQueue mQueue = CONTEXT_REQUEST_QUEUE_MAP.get(key);
                mQueue.cancelAll(TAG);
                CONTEXT_REQUEST_QUEUE_MAP.remove(key);
            }
        } catch (Exception e) {
        }
    }

    /**
     * generatePolicy
     *
     * @return mRetryPolicy
     */
    protected static RetryPolicy generatePolicy() {
        if (mRetryPolicy == null) {
            mRetryPolicy = new com.android.volley.DefaultRetryPolicy(REQUEST_OUTTIME, 0, 0);
        }
        return mRetryPolicy;
    }

    /**
     * generateQueue
     *
     * @param context context
     * @return queue
     */
    protected static RequestQueue generateQueue(Context context) {
        if (context == null) {
            return null;
        }
        RequestQueue mQueue = null;
        String key = context.getClass().getName() + "@" + context.hashCode();
        if (CONTEXT_REQUEST_QUEUE_MAP.containsKey(key)) {
            mQueue = CONTEXT_REQUEST_QUEUE_MAP.get(key);
        } else {
            mQueue = Volley.newRequestQueue(context);
            CONTEXT_REQUEST_QUEUE_MAP.put(key, mQueue);
        }
        VoHTTPSTrustManager.allowAllSSL();
        return mQueue;
    }

    /**
     * VoJsonRequest
     *
     * @author LiangMaYong
     * @version 1.0
     */
    private static class VoJsonRequest extends JsonObjectRequest {

        public VoJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        public VoJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
            super(url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }
    }

    /**
     * VoStringRequest
     *
     * @author LiangMaYong
     * @version 1.0
     */
    private static class VoStringRequest extends StringRequest {

        private static String boundary = UUID.randomUUID().toString();
        private static String prefix = "--";
        private static String lineEnd = "\r\n";

        public VoStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        public VoStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, parseCharset(response.headers));
                return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }

        protected Map<String, VoFile> getFiles() {
            return null;
        }

        public byte[] getBody() throws AuthFailureError {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                if ((getParams() != null && getParams().size() > 0)
                        || (getFiles() != null && getFiles().size() > 0)) {
                    // post params
                    if (getParams() != null && getParams().size() > 0) {
                        for (Map.Entry<String, String> entry : getParams().entrySet()) {
                            StringBuilder psb = new StringBuilder();
                            psb.append(prefix);
                            psb.append(boundary);
                            psb.append(lineEnd);
                            psb.append("Content-Disposition:form-data;name=\"" + entry.getKey() + "\"" + lineEnd
                                    + lineEnd);
                            psb.append(entry.getValue());
                            psb.append(lineEnd);
                            outputStream.write(psb.toString().getBytes());
                        }
                    }
                    // post files
                    if (getFiles() != null && getFiles().size() > 0) {
                        for (Map.Entry<String, VoFile> entry : getFiles().entrySet()) {
                            File item = new File(entry.getValue().getPath());
                            if (item.exists()) {
                                long totallenght = item.length();
                                int bufferSize = (int) (totallenght / 10);
                                StringBuilder sb = new StringBuilder();
                                sb.append(prefix);
                                sb.append(boundary);
                                sb.append(lineEnd);
                                sb.append("Content-Disposition: form-data;name=\"" + entry.getKey() + "\";filename=\""
                                        + entry.getValue().getName() + "\"" + lineEnd);
                                FileInputStream in = new FileInputStream(item);
                                sb.append("Content-Length:" + in.available() + lineEnd);
                                sb.append("Content-Type:" + entry.getValue().getContentType() + lineEnd + lineEnd);
                                outputStream.write(sb.toString().getBytes());
                                int bytes = 0;
                                byte[] bufferOut = new byte[Math.max(20 * 1024, Math.min(512 * 1024, bufferSize))];
                                while ((bytes = in.read(bufferOut)) != -1) {
                                    outputStream.write(bufferOut, 0, bytes);
                                }
                                outputStream.write(lineEnd.getBytes());
                                in.close();
                            }
                        }
                    }
                    byte[] end_data = (lineEnd + prefix + boundary + prefix + lineEnd).getBytes();
                    outputStream.write(end_data);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
            }
            byte[] bytes = outputStream.toByteArray();
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
            }
            return bytes;
        }

        @Override
        public String getBodyContentType() {
            return "multipart/form-data; boundary=" + boundary;
        }
    }

    /**
     * parseCharset
     *
     * @param headers headers
     * @return charset
     */
    private static String parseCharset(Map<String, String> headers) {
        String contentType = headers.get("Content-Type");
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return ENCODE;
    }
}
