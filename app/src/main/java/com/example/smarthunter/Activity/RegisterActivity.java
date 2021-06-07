package com.example.smarthunter.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthunter.R;

public class RegisterActivity extends GenericActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Drawable logo = getResources().getDrawable(R.drawable.logo, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");


    }

    public void onClick(View view){
        this.createDialog();
    }
}