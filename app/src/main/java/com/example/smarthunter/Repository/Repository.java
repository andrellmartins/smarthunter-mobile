package com.example.smarthunter.Repository;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

abstract public class Repository implements SingletonRepository {
    protected static final String URL_CONN = "https://smarthunter-back-app.azurewebsites.net/api/";
    protected static RequestQueue queue;
    protected static Repository instance;

    public Repository(Context context){
        queue = Volley.newRequestQueue(context);
    }

}
