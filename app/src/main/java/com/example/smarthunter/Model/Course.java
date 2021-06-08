package com.example.smarthunter.Model;

import java.util.ArrayList;

public class Course {
    public String courseLink;
    public String courseTitle;
    public ArrayList<Class> classes;
    public String courseImage;
    public String courseDescription;

    public Course(String courseLink, String courseTitle, ArrayList<Class> classes, String courseImage, String courseDescription) {
        this.courseLink = courseLink;
        this.courseTitle = courseTitle;
        this.classes = classes;
        this.courseImage = courseImage;
        this.courseDescription = courseDescription;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
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
