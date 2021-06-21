package com.example.smarthunter.Repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.smarthunter.Requisition.VolleyQueueSingleton;

import java.util.HashMap;

abstract public class Repository implements SingletonRepository {
    protected static final String URL_CONN = "https://smarthunter-back-app.azurewebsites.net/api/";
    protected static VolleyQueueSingleton queue;
    protected static Repository instance;
    protected Context context;
    protected String TOKEN_TYPE;
    protected String TOKEN;


    public Repository(Context context){
        queue = VolleyQueueSingleton.getInstance(context);
        this.context = context;
    }

    protected HashMap<String, String> getRequestHeaders(String TOKEN,String TOKEN_TYPE){
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type","application/json");
        headers.put("Accept","application/json");
        if(TOKEN == null || TOKEN_TYPE == null) {
            Log.d("REQUEST_HEADERS", headers.toString());
            return headers;
        }
        headers.put("Authorization",TOKEN_TYPE+" "+TOKEN);
        Log.d("REQUEST_HEADERS",headers.toString());
        return headers;
    }
}
