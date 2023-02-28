package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


public class OutputActivity extends AppCompatActivity {

    DatabaseReference ref;
    TextView TV;
    String myUID;
    String telegramHandle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Intent intent=getIntent();
        telegramHandle=intent.getStringExtra("telegramHandle");
        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Week1");
        TV=(TextView) findViewById(R.id.outputTV);
        readWeek();
    }


    private void  readWeek(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String readWeekStr="";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        Item item = postSnapshot.getValue(Item.class);
                        readWeekStr+=item.toString();
                        readWeekStr+=", ";
                    }
                    TV.setText(readWeekStr);
                }
                else{
                    TV.setText("No one has entered for Week1 ");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}