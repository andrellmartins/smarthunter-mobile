package com.example.smarthunter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.smarthunter.R;

public class MainActivity extends GenericActivity {
    Button buttonLogin;
    Button buttonRegister;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);
    }

    public void buttonRegisterClick(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

    public void buttonLoginClick(View view){
        if(login(email.getText().toString(), password.getText().toString()) == true){
           Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
           startActivity(intent);
        }else{
          Toast.makeText(MainActivity.this, "Cannot Login", Toast.LENGTH_LONG).show();
        }

    }



}