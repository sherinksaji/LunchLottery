package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editTextTeleHandle,editTextEmail,editTextPassword;
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

        editTextTeleHandle=(EditText)findViewById(R.id.telegramHandle);
        editTextEmail=(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);


    }
    @Override
    public void onClick(View v){

        final int idBanner= R.id.banner;
        final int idRegisterUser=R.id.registerUser;

        switch (v.getId()){
            case idBanner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case idRegisterUser:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String telegramHandle=editTextTeleHandle.getText().toString().trim();

        if (telegramHandle.isEmpty()){
            editTextTeleHandle.setError("Full name is required!");
            editTextTeleHandle.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            editTextPassword.setError("Min Password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful() ){
                            //Fix for Method invocation 'getUid' may produce 'NullPointerException'
                            String uid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                            User user =new User (telegramHandle,email,uid);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(uid)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task){
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterUserActivity.this,"User has been registered successfully", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.VISIBLE);
                                                startActivity(new Intent(RegisterUserActivity.this,LoginActivity.class));
                                            }else{
                                                String exceptionMessage=(task.getException().getMessage()!=null) ? task.getException().getMessage():"Exception Not Found";
                                                Toast.makeText(RegisterUserActivity.this,"Failed to register! "+exceptionMessage,Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                        else{
                            String exceptionMessage=(task.getException().getMessage()!=null) ? task.getException().getMessage():"Exception Not Found";
                            Toast.makeText(RegisterUserActivity.this,"Failed to register! Try again!"+exceptionMessage,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }


}