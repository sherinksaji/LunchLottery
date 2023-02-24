package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent=getIntent();
        String telegramHandle=intent.getStringExtra("telegramHandle");
        
        TextView textViewName=(TextView)findViewById(R.id.textName);
        textViewName.setText("Welcome, "+telegramHandle);

        Button logout=(Button)findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));

            }
        });

        Button InputButton=(Button)findViewById(R.id.Input);
        InputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent InputIntent=new Intent(HomeActivity.this, InputActivity.class);
                InputIntent.putExtra("telegramHandle",telegramHandle);
                
                startActivity(InputIntent);
            }
        });




        Button OutputButton=(Button)findViewById(R.id.Output);
        OutputButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent InputIntent=new Intent(HomeActivity.this, OutputActivity.class);
                InputIntent.putExtra("telegramHandle",telegramHandle);

                startActivity(InputIntent);
            }
        });

    }

}