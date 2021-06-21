package com.example.smarthunter.Model;

import java.util.ArrayList;

public class User {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected ArrayList<String> coursesIds;

    public User(int id,String name, String email, String password, ArrayList<String> coursesIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coursesIds = coursesIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<String> getCoursesIds() {
        return coursesIds;
    }

    public void setCoursesIds(ArrayList<String> coursesIds) {
        this.coursesIds = coursesIds;
    }
}
