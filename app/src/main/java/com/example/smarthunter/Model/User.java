package com.example.smarthunter.Model;

import java.util.ArrayList;

public class User {
    public String name;
    public String email;
    public String password;
    public ArrayList<Course> courses;

    public User(String name, String email, String password, ArrayList<Course> courses) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
