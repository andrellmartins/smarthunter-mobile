package com.example.smarthunter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.smarthunter.R;
import com.example.smarthunter.Repository.UserRepository;

public class MenuActivity extends AppCompatActivity {

    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Drawable logo = getResources().getDrawable(R.drawable.logo, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");

        userRepository = (UserRepository) UserRepository.getInstance(MenuActivity.this,null,null);

    }

    public void buttonProfileClicked(View view){
        Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void buttonEnrolledClicked(View view){
        userRepository.filterEnrolled = true;
        Intent intent = new Intent(MenuActivity.this, CourseListActivity.class);
        startActivity(intent);
    }

    public void buttonCourseListClicked(View view){
        userRepository.filterEnrolled = false;
        Intent intent = new Intent(MenuActivity.this, CourseListActivity.class);
        startActivity(intent);
    }


}