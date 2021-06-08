package com.example.smarthunter.Repository;

import android.content.Context;

import com.example.smarthunter.Model.Course;
import com.example.smarthunter.Model.User;

import java.util.ArrayList;

public class CoursesRepository {
    private static final CoursesRepository instance = new CoursesRepository();
    private ArrayList<Course> courses;
    private Context context;

    public CoursesRepository() {
    }

    public static CoursesRepository getInstance() {
        return instance;
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
}
