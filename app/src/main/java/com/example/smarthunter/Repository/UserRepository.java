package com.example.smarthunter.Repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smarthunter.Model.User;
import com.example.smarthunter.Requisition.VolleyQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class UserRepository extends Repository{
    private static UserRepository instance;
    protected static String URL_BASE = Repository.URL_CONN + "users";
    public static LoginListener loginListener;
    public User loggedUser;
    protected static String TOKEN;
    protected static String TOKEN_TYPE;

    public User getLoggedUser() {
        return loggedUser;
    }

    public static String getTOKEN() {
        return TOKEN;
    }

    public static String getTokenType() {
        return TOKEN_TYPE;
    }

    protected UserRepository(Context context, String username, String password) {
        super(context);
        login(username,password);
    }
    public void login(String username, String password){
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("email",username);
            requestData.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new com.android.volley.toolbox.JsonObjectRequest(
            Request.Method.POST,
            URL_CONN + "login",
            requestData,
            new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("responseLogin",response.toString());
                    try{
                        TOKEN = response.getString("token");
                        TOKEN_TYPE = response.getString("type");
                        loggedUser = new User(0,"",username,password,new ArrayList<Integer>());
                        String[] tokenArray = response.getString("token").split("\\.");

                        Base64.Decoder decoder = Base64.getDecoder();
                        String header = new String(decoder.decode(tokenArray[1]));
                        Log.d("decodedHeader",header);
                        JSONObject headerJSON = new JSONObject(header);

                        Log.d("Token Header",header.toString());
                        int userId = headerJSON.getInt("jti");
                        loggedUser.setId(userId);

                        loadData(userId);

                    }catch(JSONException e){
                        Log.d("keepAlive","Login Error - Parse JSON:"+e.getMessage());
                        if(loginListener != null){
                            loginListener.onErrorListener();
                        }
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("KeepAlive","Error, Cannot request login Data.");
                    instance = null;
                    if(loginListener != null){
                        loginListener.onErrorListener();
                    }
                }
            }
        );
        queue.add(jsonRequest);
    }
    public void logOut(){
        instance = null;
    }
    public void loadData(int userID){
        assert TOKEN != null && TOKEN_TYPE != null;
        String url = URL_BASE+"/"+String.valueOf(userID);
        Log.d("keepAlive","Start URL:"+url);
        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new com.android.volley.toolbox.JsonObjectRequest(
            Request.Method.GET, url,null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject responseJson) {
                    Log.d("responseLoadData",responseJson.toString());
                    Log.d("loggedUserEmail",loggedUser.getEmail());

                    try {
                        if(loggedUser.getEmail().equals(responseJson.getString("email"))){
                            ArrayList<Integer> coursesIds = new ArrayList<Integer>();
                            JSONArray userEnrolledCourses = responseJson.getJSONArray("enrolledCourses");
                            for(int j=0;j<userEnrolledCourses.length();j++){
                                JSONObject enrolledCourse = userEnrolledCourses.getJSONObject(j);
                                Integer courseId = enrolledCourse.getJSONObject("course").getInt("id");
                                coursesIds.add(courseId);
                            }
                            User newUser = new User(responseJson.getInt("id"),responseJson.getString("name"),responseJson.getString("email"),"",coursesIds);
                            loggedUser = newUser;
                            Log.d("loggedUserData",responseJson.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(loginListener != null){
                        loginListener.onSuccessListener(loggedUser);
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Request Error", "Error Requesting Users");
                    if(loginListener != null){
                        loginListener.onErrorListener();
                    }
                    instance = null;
                }
            }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("Autenticate:",TOKEN_TYPE+" "+TOKEN);

                return getRequestHeaders(TOKEN,TOKEN_TYPE);
            }
        };
        Log.d("keepAlive","PosRequest - Pre Queue");
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
        Log.d("keepAlive","POSQueue");
    }

    public static Repository getInstance(Context context, @Nullable String username,@Nullable String password) {
        if(instance == null && username != null && password != null){
            instance = new UserRepository(context,username,password);
        }
        if(instance != null){
            instance.setContext(context);
        }
        return instance;
    }
    public static void newUser(Context context,User newUser){
        String url = URL_BASE;
        JSONObject newUserData = new JSONObject();
        if(queue == null){
            queue = VolleyQueueSingleton.getInstance(context);
        }

        try {
            newUserData.put("name",newUser.getName());
            newUserData.put("email",newUser.getEmail());
            newUserData.put("password",newUser.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, newUserData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("newUserResponse", response.toString());
                if(loginListener != null){
                    loginListener.onSuccessListener(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("newUserFailure","Error creating new User");
            }
        });
        queue.add(jsonRequest);
    }
    public void updateLoggedUser(String name,String email,String password){
        if(name.equals("") || email.equals("") || password.equals("")){
            if(loginListener != null){
                loginListener.onErrorListener();
            }
            return;
        }
        String url = URL_BASE+"/"+String.valueOf(loggedUser.getId());
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name",name);
            jsonObject.put("email",email);
            jsonObject.put("password",password);
        }catch(JSONException e){
            Log.d("updateLoggedUser_JSON_E",e.getMessage());
            if(loginListener != null){
                loginListener.onErrorListener();
            }
        }

        Log.d("updateLoggedUser_url",url);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("SUCCESS_UPDATE_USER",response.toString());
                loggedUser.setName(name);
                loggedUser.setEmail(email);
                loggedUser.setPassword(password);
                if(loginListener != null){
                    loginListener.onSuccessListener(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR_UPDATE_USER","Error Updating USER");
                if(loginListener != null){
                    loginListener.onErrorListener();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getRequestHeaders(TOKEN,TOKEN_TYPE);
            }
        };

        queue.add(jsonRequest);
    }

    public void setContext(Context context){
        this.context =context;
    }

    public static void setLoginListener(LoginListener loginListener) {
        UserRepository.loginListener = loginListener;
    }

    public interface LoginListener{
        public void onSuccessListener(User user);
        public void onErrorListener();
    }
}
