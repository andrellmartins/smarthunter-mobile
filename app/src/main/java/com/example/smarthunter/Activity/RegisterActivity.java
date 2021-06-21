package com.example.smarthunter.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthunter.Model.User;
import com.example.smarthunter.R;
import com.example.smarthunter.Repository.UserRepository;

import java.util.ArrayList;

public class RegisterActivity extends GenericActivity {
    EditText editTextViewName;
    EditText editTextViewEmail;
    EditText editTextViewPassword;
    EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Drawable logo = getResources().getDrawable(R.drawable.logo, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");

        editTextViewName = findViewById(R.id.editTextName);
        editTextViewEmail = findViewById(R.id.editTextEmail);
        editTextViewPassword = findViewById(R.id.editTextPassword);
        editTextPassword = findViewById(R.id.editTextPassword);

    }

    public void onButtonRegisterClick(View view){
        User newUser = new User(0,editTextViewName.getText().toString(),editTextViewEmail.getText().toString(),editTextPassword.getText().toString(),new ArrayList<Integer>());
        UserRepository.setLoginListener(new UserRepository.LoginListener() {
            @Override
            public void onSuccessListener(User user) {
                createToast("User created successfully");
            }

            @Override
            public void onErrorListener() {
                createToast("Error creating User.");
            }
        });
        UserRepository.newUser(RegisterActivity.this,newUser);
    }
}