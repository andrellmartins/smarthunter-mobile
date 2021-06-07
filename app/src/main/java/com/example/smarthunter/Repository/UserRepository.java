package com.example.smarthunter.Repository;

import android.content.Context;
import com.example.smarthunter.Model.User;
import java.util.ArrayList;

public class UserRepository {
    private static final UserRepository instance = new UserRepository();
    private ArrayList<User> users;
    private Context context;
    public User loggedUser;

    public UserRepository() {
    }

    public static UserRepository getInstance() {
        return instance;
    }

    public void setContext(Context context){
        this.context =context;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }


}
