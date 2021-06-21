package com.example.smarthunter.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smarthunter.Model.User;
import com.example.smarthunter.R;
import com.example.smarthunter.Repository.UserRepository;

public class ProfileActivity extends GenericActivity {
    EditText editTextProfileName;
    EditText editTextProfileEmail;
    EditText editTextProfilePassword;
    Button buttonUpdateUser;

    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Drawable logo = getResources().getDrawable(R.drawable.logo, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");

        //EditTexts
        editTextProfileName = findViewById(R.id.editTextProfileName);
        editTextProfileEmail = findViewById(R.id.editTextProfileEmail);
        editTextProfilePassword = findViewById(R.id.editTextProfilePassword);
        buttonUpdateUser = findViewById(R.id.buttonUpdateUser);

        //getUserRepositoryData
        userRepository = (UserRepository) UserRepository.getInstance(ProfileActivity.this,null,null);

        //loadData
        editTextProfileName.setText(userRepository.loggedUser.getName());
        editTextProfileEmail.setText(userRepository.loggedUser.getEmail());
    }

    public void buttonUpdateUser(View view){
        Log.d("buttonUpdateUser","Clicked");
        UserRepository.setLoginListener(new UserRepository.LoginListener() {
            @Override
            public void onSuccessListener(User user) {
                createToast("User Updated Sucessfully.");
                editTextProfilePassword.setText("");
            }

            @Override
            public void onErrorListener() {
                createToast("Error Updating User.");
            }
        });
        userRepository.updateLoggedUser(editTextProfileName.getText().toString(),editTextProfileEmail.getText().toString(),editTextProfilePassword.getText().toString());
    }
}