package com.example.smarthunter.Repository;

import android.content.Context;

import com.example.smarthunter.Model.Course;

import java.util.ArrayList;

public class CourseRepository {
    private static final CourseRepository instance = new CourseRepository();
    private ArrayList<Course> courses;
    private Context context;
    private int selectedCourse;

    public CourseRepository() {
    }

    public static CourseRepository getInstance() {
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

    public int getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(int selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}
