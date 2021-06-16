package com.example.smarthunter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smarthunter.Model.User;
import com.example.smarthunter.R;
import com.example.smarthunter.Repository.UserRepository;

public class MainActivity extends GenericActivity {
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
        userRepository = UserRepository.getInstance(MainActivity.this);
    }

    public void buttonRegisterClick(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void buttonLoginClick(View view){
        Toast.makeText(MainActivity.this, "Login Clicked", Toast.LENGTH_LONG).show();
        if(userRepository.login(email.getText().toString(),password.getText().toString())){
            Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
            createToast("Logging in");
            startActivity(intent);
        }else{
            createDialog("User not authenticated","User not found.","OK",null);
        }
    }



}