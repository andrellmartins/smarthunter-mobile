package com.example.smarthunter.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smarthunter.Model.Course;
import com.example.smarthunter.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserRepository {
    private static final UserRepository instance = new UserRepository();
    private ArrayList<User> users;
    private Context context;
    public User loggedUser;

    public UserRepository() {
        users = new ArrayList<User>();
    }
    public boolean login(String username, String password){
        for(User u : users){
            if(u.getEmail().equals(username) && u.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public void loadData(){
        RequestQueue queue = Volley.newRequestQueue(context);
            String url ="https://jsonplaceholder.typicode.com/users";
            // Request a string response from the provided URL.
            JsonArrayRequest stringRequest = new com.android.volley.toolbox.JsonArrayRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray responseJson) {
                            users = new ArrayList<User>();
                            try {
                                Log.d("testeeee", responseJson.toString());
                                for(int i=0;i<responseJson.length();i++){
                                    JSONObject user = responseJson.getJSONObject(i);
                                    users.add(
                                        new User(
                                            user.getString("name"),
                                            user.getString("email"),
                                            user.getString("username"),
                                            new ArrayList<Course>()
                                        )
                                    );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("usersLength",String.valueOf(users.size()));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,"Requisition Error", Toast.LENGTH_LONG);
                }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static UserRepository getInstance(Context context) {
        instance.setContext(context);
        if(instance.users.size() == 0){
            instance.loadData();
        }
        return instance;
    }

    public void setContext(Context context){
        this.context =context;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }


}
