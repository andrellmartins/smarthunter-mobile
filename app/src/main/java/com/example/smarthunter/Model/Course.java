package com.example.smarthunter.Model;

import java.util.ArrayList;

public class Course {
    public Integer id;
    public String courseTitle;
    public ArrayList<Lesson> lessons;
    public String courseImage;
    public String courseDescription;

    public Course(Integer id, String courseTitle, String courseDescription, String courseImage, ArrayList<Lesson> lessons) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.lessons = lessons;
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

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addClass(Lesson newLesson){
        this.lessons.add(newLesson);
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
