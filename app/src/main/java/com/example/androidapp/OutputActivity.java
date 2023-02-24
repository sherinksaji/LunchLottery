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
    String teleHandle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Intent intent=getIntent();
        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Week1").child(myUID);
        TV=(TextView) findViewById(R.id.outputTV);
        readWeek();
    }


    private void  readWeek(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Item item = dataSnapshot.getValue(Item.class);
                Log.d(TAG, "Value is: " + item.toString());
                TV.setText(item.toString());
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