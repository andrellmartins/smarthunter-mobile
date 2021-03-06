package com.example.smarthunter.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smarthunter.Model.Lesson;
import com.example.smarthunter.Model.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class CourseRepository extends Repository{

    private static CourseRepository instance;
    private ArrayList<Course> courses;
    private int selectedCourse;
    private String TOKEN_TYPE;
    private String TOKEN;
    private String URL_BASE;

    private static LoadDataListener loadDataListener;

    public static LoadDataListener getLoadDataListener() {
        return loadDataListener;
    }

    public static void setLoadDataListener(LoadDataListener loadDataListener) {
        CourseRepository.loadDataListener = loadDataListener;
    }

    private CourseRepository(Context context, String TOKEN_TYPE, String TOKEN) {
        super(context);
        URL_BASE = URL_CONN+"courses";
        courses = new ArrayList<Course>();
        this.TOKEN_TYPE = TOKEN_TYPE;
        this.TOKEN = TOKEN;
        loadData();
    }

    public static CourseRepository getInstance(Context context, @Nullable String TOKEN_TYPE,@Nullable String TOKEN){
        if(instance == null && TOKEN != null && TOKEN_TYPE != null){
            instance = new CourseRepository(context,TOKEN_TYPE,TOKEN);
        }
        if(instance != null){
            instance.setContext(context);
        }
        return instance;
    }

    public void clearData(){
        instance = null;
    }

    public void loadData(){
        UserRepository userRepository = (UserRepository) UserRepository.getInstance(context,null,null);
        Log.d("LoadData-IN","TEST");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_BASE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("SuccessRequestCourses",response.toString());
                courses = new ArrayList<Course>();
                try {
                    JSONArray coursesJSON = response.getJSONArray("content");
                    for(int i = 0;i<coursesJSON.length();i++){
                        JSONObject courseJSON = coursesJSON.getJSONObject(i);

                        Course newCourse = new Course(
                        courseJSON.getInt("id"),courseJSON.getString("name"),courseJSON.getString("description"),courseJSON.getString("thumbUrl"),new ArrayList<>());
                        JSONArray lessons = courseJSON.getJSONArray("lessons");
                        if(userRepository.filterEnrolled){
                            boolean existe = false;
                            for(Integer courseId:userRepository.loggedUser.getCoursesIds()){
                                if(courseJSON.getInt("id") == courseId){
                                    existe = true;
                                }
                            }
                            if(!existe){
                                continue;
                            }
                        }
                        for(int j=0;j<lessons.length();j++){
                            JSONObject lesson = lessons.getJSONObject(j);
                            JSONArray activities = lesson.getJSONArray("activities");
                            for(int k=0;k<activities.length();k++){
                                JSONObject activity = activities.getJSONObject(k);
                                newCourse.addClass(new Lesson(
                                        activity.getString("urlVideo").split("watch\\?v[=]")[1],
                                        lesson.getString("name")+" - Class "+activity.getString("id"),
                                        activity.getString("title")
                                    )
                                );

                            }

                        }
                        Log.d("classesLength",String.valueOf(newCourse.getLessons().size()));
                        courses.add(newCourse);
                    }
                    Log.d("coursesLength",String.valueOf(courses.size()));
                    if(loadDataListener != null){
                        loadDataListener.onSuccessDataLoad();
                        loadDataListener = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(loadDataListener != null){
                        loadDataListener.onErrorDataLoad();
                        loadDataListener = null;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FailedCoursesRequest","Error Requesting Courses");
                if(loadDataListener != null){
                    loadDataListener.onErrorDataLoad();
                    loadDataListener = null;
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getRequestHeaders(TOKEN,TOKEN_TYPE);
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void enrollUser(Integer courseId,Integer userId){
        Log.d("enrollUserParams","courseID:"+courseId+"--UserId:"+userId);
        String url = URL_CONN + "enrolled_courses";
        JSONObject enrolledJSON = new JSONObject();
        JSONObject courseJSON = new JSONObject();
        JSONObject userJSON = new JSONObject();
        try {
            courseJSON.put("id",courseId);
            userJSON.put("id",userId);
            enrolledJSON.put("course",courseJSON);
            enrolledJSON.put("user",userJSON);
        } catch (JSONException e) {
            e.printStackTrace();
            if(loadDataListener != null){
                loadDataListener.onErrorDataLoad();
                loadDataListener = null;
            }
        }

        Log.d("enrollCourseUserBody",enrolledJSON.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, enrolledJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("enrollUserSuccess","Enrolled Sucessfully");
                if(loadDataListener != null){
                    loadDataListener.onSuccessDataLoad();
                    loadDataListener = null;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("enrollUserError","Error on Enroll on new Course");
                if(loadDataListener != null){
                    loadDataListener.onErrorDataLoad();
                    loadDataListener = null;
                }
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getRequestHeaders(TOKEN,TOKEN_TYPE);
            }
        };

        queue.add(jsonObjectRequest);
    }
    public void unenrollUser(Integer courseId,Integer userId) {
        //get Enrolled Courses
        String url = URL_CONN+"enrolled_courses";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("requestEnrolledCourses",response.toString());
                Integer deleteId = 0;
                try{
                    JSONArray enrolledCourses_list = response.getJSONArray("content");
                    for(int i = 0;i<enrolledCourses_list.length();i++){
                        JSONObject enrolledCourseJSON = enrolledCourses_list.getJSONObject(i);
                        Integer idCourse = enrolledCourseJSON.getJSONObject("course").getInt("id");
                        Integer idUser = enrolledCourseJSON.getJSONObject("user").getInt("id");
                        if(idCourse == courseId && idUser == userId){
                            deleteId = enrolledCourseJSON.getInt("id");
                        }
                    }
                }catch( JSONException e){
                    Log.d("JSONPARSE","ERROR PARSING JSON ON ENROLLED COURSES REQUEST");
                }
                if(deleteId != 0){
                    Log.d("deleteEnrolledCourse",deleteId.toString());
                    StringRequest stringRequestUnenroll = new StringRequest(
                            Request.Method.DELETE,
                            URL_CONN + "enrolled_courses/" + deleteId,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("UnEnrolledCourses","course UNENROLLED");
                                    if(instance.loadDataListener != null){
                                        instance.loadDataListener.onSuccessDataLoad();
                                        instance.loadDataListener = null;
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("UnEnrolledCoursesError","Error unenrolling, error:");
                                    if(instance.loadDataListener != null){
                                        instance.loadDataListener.onErrorDataLoad();
                                        instance.loadDataListener = null;
                                    }
                                }
                            }

                    ){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return getRequestHeaders(instance.TOKEN,instance.TOKEN_TYPE);
                        }
                    };
                    queue.add(stringRequestUnenroll);
                }else{
                    if(loadDataListener != null){
                        loadDataListener.onErrorDataLoad();
                        loadDataListener = null;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(loadDataListener != null){
                    loadDataListener.onErrorDataLoad();
                    loadDataListener = null;
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getRequestHeaders(TOKEN,TOKEN_TYPE);
            }
        };

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

    public interface LoadDataListener{
        void onSuccessDataLoad();
        void onErrorDataLoad();
    }
}
