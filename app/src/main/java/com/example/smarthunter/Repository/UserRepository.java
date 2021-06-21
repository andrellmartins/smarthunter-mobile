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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class UserRepository extends Repository{
    private static UserRepository instance;
    protected static final String URL_BASE = Repository.URL_CONN + "users";
    private Context context;
    public static LoginListener loginListener;
    public User loggedUser;
    protected static String TOKEN;
    protected static String TOKEN_TYPE;

    private UserRepository(Context context,String username,String password) {
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
                        loggedUser = new User(0,"",username,password,new ArrayList<String>());
                        String[] tokenArray = response.getString("token").split("\\.");

                        Base64.Decoder decoder = Base64.getDecoder();
                        String header = new String(decoder.decode(tokenArray[1]));
                        Log.d("decodedHeader",header);
                        JSONObject headerJSON = new JSONObject(header);

                        Log.d("Token Header",header.toString());
                        int userId = Integer.parseInt(headerJSON.getString("jti"));
                        loggedUser.setId(userId);

                        loadData(userId);

                    }catch(JSONException e){
                        Log.d("keepAlive","Erro no Login - Parse JSON:"+e.getMessage());
                        if(loginListener != null){
                            loginListener.onErrorListener();
                        }
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("KeepAlive","Erro ao requisitar dados do Login.");
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
        Log.d("keepAlive","Running URL:"+url);
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
                            ArrayList<String> coursesIds = new ArrayList<String>();
                            JSONArray userEnrolledCourses = responseJson.getJSONArray("enrolledCourses");
                            for(int j=0;j<userEnrolledCourses.length();j++){
                                JSONObject enrolledCourse = userEnrolledCourses.getJSONObject(j);
                                String courseId = enrolledCourse.getJSONObject("course").getString("id");
                                coursesIds.add(courseId);
                            }
                            User newUser = new User(loggedUser.getId(),responseJson.getString("name"),responseJson.getString("email"),"",coursesIds);
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
                    Log.d("RequisitionError", "Erro ao Solicitar usuários");
                    if(loginListener != null){
                        loginListener.onErrorListener();
                    }
                    instance = null;
                }
            }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getRequestHeaders();
            }
        };
        Log.d("keepAlive","PosRequest - Pre Queue");
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
        Log.d("keepAlive","POSQueue");
    }

    public HashMap<String, String> getRequestHeaders(){
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type","application/json");
        headers.put("Accept","application/json");
        headers.put("Authorization",TOKEN_TYPE+" "+TOKEN);
        Log.d("REQUEST_HEADERS",headers.toString());
        return headers;
    }

    public static Repository getInstance(Context context, @Nullable String username,@Nullable String password) {
        if(instance == null && username != null && password != null){
            instance = new UserRepository(context,username,password);
        }
        return instance;
    }
    public static void newUser(Context context,User newUser){
        String url = URL_BASE;
        JSONObject newUserData = new JSONObject();
        if(queue == null){
            queue = Volley.newRequestQueue(context);
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
        if(password.equals("")){
            if(loginListener != null){
                loginListener.onErrorListener();
            }
            return;
        }
        String url = URL_BASE+"/"+String.valueOf(loggedUser.getId());
        JSONObject jsonObject = new JSONObject();
        try{
            if(loggedUser.getName().equals(name))
                jsonObject.put("name",name);
            if(loggedUser.getEmail().equals(email))
            jsonObject.put("email",email);
            jsonObject.put("password",password);
        }catch(JSONException e){
            Log.d("updateLoggedUser_JSONE",e.getMessage());
        }

        Log.d("updateLoggedUser_url",url);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("SUCCESS_UPDATE_USER",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR_UPDATE_USER","Erro ao atualizar o Usuário");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getRequestHeaders();
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
