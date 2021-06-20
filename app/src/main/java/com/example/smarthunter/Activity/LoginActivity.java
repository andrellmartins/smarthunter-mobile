package com.example.smarthunter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarthunter.R;
import com.example.smarthunter.Repository.UserRepository;

public class LoginActivity extends GenericActivity {
    Button buttonLogin;
    Button buttonRegister;
    EditText email;
    EditText password;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        userRepository = UserRepository.getInstance(LoginActivity.this);
    }

    public void buttonRegisterClick(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void buttonLoginClick(View view){
        Toast.makeText(LoginActivity.this, "Login Clicked", Toast.LENGTH_LONG).show();
        if(userRepository.login(email.getText().toString(),password.getText().toString())){
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            createToast("Logging in");
            startActivity(intent);
        }else{
            createDialog("User not authenticated","User not found.","OK",null);
        }
    }



}