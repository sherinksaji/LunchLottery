package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTelegramHandle,editTextPassword;

    
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        TextView register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        Button signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextTelegramHandle=(EditText) findViewById(R.id.telegramHandle);
        editTextPassword=(EditText) findViewById(R.id.password);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);




    }
    public void onBackPressed() {
        //do nothing
    }

    @Override
    public void onClick(View v){
        //the following final int are created to resolve this warning:
        //Resource IDs will be non-final by default in Android Gradle Plugin version 8.0, avoid using them in switch case statements
        final int idRegister=R.id.register;
        final int idSignIn=R.id.signIn;

        switch(v.getId()){
            case idRegister:
                startActivity(new Intent(this, RegisterUserActivity.class));
                break;

            case idSignIn:
                userLogin();
                break;


        }
    }

    private void userLogin(){
        String telegramHandle=editTextTelegramHandle.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if (telegramHandle.isEmpty()){
            editTextTelegramHandle.setError("Telegram handle is required!");
            editTextTelegramHandle.requestFocus();
            return;
        }

        String email=telegramHandle+"@example.com";

        if (password.isEmpty()){
            editTextPassword.setError ("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            editTextPassword.setError ("Min Password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        AuthenticationOperations.signIn(email, password, new PlainTaskCompleteListener() {
            @Override
            public void onBackendComplete(boolean success) {
                if(success){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Login successful!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"Failed to login,please check your credentials!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });



    }
}