package com.example.smarthunter.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.EventListener;

public class GenericActivity extends AppCompatActivity {

    public void createDialog(String title,String message,@Nullable String negativeButton,@Nullable String positiveButton){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(title);
        //define a mensagem
        builder.setMessage(message);
        //define um botão como negativo.
        if(negativeButton != null){
            builder.setNegativeButton(negativeButton, null);
        }
        //define um botão como positivo.
        if(positiveButton != null){
            builder.setNegativeButton(positiveButton, null);
        }
        //cria o AlertDialog
        builder.create().show();
    }

    public void createToast(String message){
        Toast.makeText(GenericActivity.this,message,Toast.LENGTH_LONG).show();
    }
}
