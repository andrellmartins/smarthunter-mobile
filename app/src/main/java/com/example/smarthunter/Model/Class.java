package com.example.smarthunter.Model;

public class Class {
    public String classLink;
    public String classTitle;
    public String classDescription;

    public Class(String classLink, String classTitle, String classDescription) {
        this.classLink = classLink;
        this.classTitle = classTitle;
        this.classDescription = classDescription;
    }

    public String getClassLink() {
        return classLink;
    }

    public void setClassLink(String classLink) {
        this.classLink = classLink;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
}
