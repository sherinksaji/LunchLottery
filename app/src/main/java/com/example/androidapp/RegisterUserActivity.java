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

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTelegramHandle,editTextPassword, confirmPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();

        TextView banner=(TextView)findViewById(R.id.banner);
        banner.setOnClickListener(this);

        TextView registerUser=(Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextTelegramHandle=(EditText)findViewById(R.id.telegramHandle);
        editTextPassword=(EditText)findViewById(R.id.password);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        TextView back = findViewById(R.id.back);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        //final int idBanner= R.id.banner;
        final int idRegisterUser=R.id.registerUser;

        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                break;
            case idRegisterUser:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String password=editTextPassword.getText().toString().trim();
        String confirm_password=confirmPassword.getText().toString().trim();
        String telegramHandle=editTextTelegramHandle.getText().toString().trim();

        if (telegramHandle.isEmpty()){
            editTextTelegramHandle.setError("Telegram handle is required!");
            editTextTelegramHandle.requestFocus();
            return;
        }
        else if (telegramHandle.contains("@")){
            editTextTelegramHandle.setError("Enter Telegram handle without @");
        }

        else if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        else if (password.length()<6){
            editTextPassword.setError("Min Password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        else if (!password.equals(confirm_password)){
            confirmPassword.setError("Password given is different");
            confirmPassword.requestFocus();
            return;
        }

        String email=telegramHandle+"@example.com";

        progressBar.setVisibility(View.VISIBLE);

        AuthenticationOperations.registerUser(telegramHandle, email, password, new PlainTaskCompleteListener() {
            @Override
            public void onBackendComplete(boolean success) {
                if (success){
                    Toast.makeText(RegisterUserActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterUserActivity.this,"Failed to register user!",Toast.LENGTH_LONG).show();

                }
            }
        });



    }
}