package com.example.smarthunter.Model;

import java.util.ArrayList;

public class Course {
    public Integer id;
    public String courseTitle;
    public ArrayList<Class> classes;
    public String courseImage;
    public String courseDescription;

    public Course(Integer id, String courseTitle, String courseDescription, String courseImage, ArrayList<Class> classes) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.classes = classes;
        this.courseImage = courseImage;
        this.courseDescription = courseDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }

    public void addClass(Class newClass){
        this.classes.add(newClass);
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
}
