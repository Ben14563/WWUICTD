package com.example.kasingj.smokecessation2;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by kasingj on 10/13/16.
 */
public class NetworkQueue {
    private static NetworkQueue mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private NetworkQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
