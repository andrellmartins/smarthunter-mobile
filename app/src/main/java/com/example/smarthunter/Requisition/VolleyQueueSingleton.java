package com.example.smarthunter.Requisition;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyQueueSingleton {
    protected static VolleyQueueSingleton instance;
    protected RequestQueue queue;
    protected VolleyQueueSingleton(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public static VolleyQueueSingleton getInstance(Context context){
        if(instance == null){
            instance = new VolleyQueueSingleton(context);
        }
        return instance;
    }

    public void add(Request request){
        queue.add(request);
    }
}
