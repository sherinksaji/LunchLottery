package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Entry;
import com.example.lib.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class OutputActivity extends AppCompatActivity {

    DatabaseReference ref;
    TextView TV;
    String currentUser;
    String priorInput;
    ArrayList<Entry> entryArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Intent intent=getIntent();
        currentUser=intent.getStringExtra("telegramHandle");
        priorInput=intent.getStringExtra("priorInput");
        ref = FirebaseDatabase.getInstance().getReference().child("Week10");
        TV=(TextView) findViewById(R.id.outputTV);
        readWeek();
    }

    /**
     *no prior input: i plan on making the output button invisible
     * */

    private void  readWeek(){
        entryArrayList=new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String readWeekStr="";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        Ticket ticket = postSnapshot.getValue(Ticket.class);
                        Entry entry=new Entry(ticket);
                        if (entry.calStr().equals(priorInput)){
                            entryArrayList.add(entry);
                        }

                    }
                    for (Entry entry:entryArrayList){
                        readWeekStr+=entry.toString();
                        readWeekStr+=", ";
                    }
                    TV.setText(readWeekStr);
                }
                else{
                    //dont know why it gets blank
                    Log.i("else", "Still in else block.");//didnt work
                    TV.setText("You did not input for Week10");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("readWeek", "Failed to read value.", error.toException());
            }
        });
    }
}