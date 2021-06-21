package com.example.smarthunter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarthunter.Model.User;
import com.example.smarthunter.R;
import com.example.smarthunter.Repository.Repository;
import com.example.smarthunter.Repository.UserRepository;

public class LoginActivity extends GenericActivity {
    Button buttonLogin;
    Button buttonRegister;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Config Page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //Get Views
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);

        email.setText("carlos@gmail.com");
        password.setText("12345");

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserRepository userRepository = (UserRepository) UserRepository.getInstance(LoginActivity.this,null,null);
        if(userRepository != null){
            userRepository.logOut();
        }

    }

    public void buttonRegisterClick(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void buttonLoginClick(View view){
        Log.d("buttonLoginClick","Login Clicked");
        UserRepository.setLoginListener(new UserRepository.LoginListener() {
            @Override
            public void onSuccessListener(User user) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                createToast("Logging in");
                startActivity(intent);
            }
            @Override
            public void onErrorListener() {
                createDialog("User not authenticated","User not found.","OK",null);
            }
        });
            UserRepository userRepository = (UserRepository) UserRepository.getInstance(LoginActivity.this,email.getText().toString(),password.getText().toString());
    }



}