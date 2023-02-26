package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity{
    DatabaseReference ref;
    String myUID;
    TextView TV;
    String telegramHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        ref= FirebaseDatabase.getInstance().getReference("Users").child(myUID);


        TV=(TextView)findViewById(R.id.textName);

        readTelegramHandle();

        TV.setText("Welcome, "+telegramHandle);

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
    private void  readTelegramHandle(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: " + user.getTelegramHandle());
                telegramHandle=user.getTelegramHandle();
                TV.setText("Welcome, "+telegramHandle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                TV.setText("You did not enter any item yet");
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}