package com.example.smarthunter.Repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smarthunter.Model.Course;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseRepository extends Repository{

    private static CourseRepository instance;
    private ArrayList<Course> courses;
    private int selectedCourse;
    private String TOKEN_TYPE;
    private String TOKEN;
    private String URL_BASE;

    private CourseRepository(Context context,String TOKEN_TYPE,String TOKEN) {
        super(context);
        URL_BASE = URL_CONN+"/courses";
        this.TOKEN_TYPE = TOKEN_TYPE;
        this.TOKEN = TOKEN;
        loadData();
    }

    public static CourseRepository getInstance(Context context,String TOKEN_TYPE,String TOKEN){
        if(instance == null && TOKEN != null){
            instance = new CourseRepository(context,TOKEN_TYPE,TOKEN);
        }
        instance.setContext(context);
        return instance;
    }

    public void loadData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_BASE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("SuccessRequestCourses",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FailedCoursesRequest","Error Requesting Courses");
            }
        });
        queue.add(jsonObjectRequest);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(int selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}
