package com.example.smarthunter.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import java.util.EventListener;

public class GenericActivity extends AppCompatActivity {

    public void createDialog(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Error");
        //define a mensagem
        builder.setMessage("Login not authorized");
        //define um botão como negativo.
        builder.setNegativeButton("OK", null);
        //cria o AlertDialog
        builder.create().show();
    }

    public boolean login(String email, String password) {
        if (email == email && password == password) {
            return true;
        } else {
            return false;
        }
    }


}
