package com.example.smarthunter.Model;

public class Lesson {
    public String lessonLink;
    public String lessonTitle;
    public String lessonDescription;

    public Lesson(String lessonLink, String lessonTitle, String lessonDescription) {
        this.lessonLink = lessonLink;
        this.lessonTitle = lessonTitle;
        this.lessonDescription = lessonDescription;
    }

    public String getLessonLink() {
        return lessonLink;
    }

    public void setLessonLink(String lessonLink) {
        this.lessonLink = lessonLink;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }
}
