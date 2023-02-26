package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTelegramHandle,editTextPassword;

    private FirebaseAuth mAuth;
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

        mAuth=FirebaseAuth.getInstance();


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
        if (telegramHandle.contains("@")){
            editTextTelegramHandle.setError("Enter Telegram handle without @");
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


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child(uid);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String telegramHandle=snapshot.child("telegramHandle").getValue(String.class);
                            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("telegramHandle",telegramHandle );


                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            String exceptionMessage=(task.getException().getMessage()!=null) ? task.getException().getMessage():"Exception Not Found";
                            Toast.makeText(LoginActivity.this,"Failed to get user!"+exceptionMessage,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                }else{
                    Toast.makeText(LoginActivity.this,"Failed to login,please check your credentials!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}