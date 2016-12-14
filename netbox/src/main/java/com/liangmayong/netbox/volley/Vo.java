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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
                              final Map<String, String> headers, Response.Listener<String> responseListener,
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
                                    final Map<String, String> headers) throws VolleyError {
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
     * @param map
     * @param json    json  @return json
     */
    public static JSONObject jsonSync(Context context, VoMethod method, String url, Map<String, String> map, JSONObject json) throws VolleyError {
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
