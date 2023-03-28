package com.example.androidapp;

import static android.content.ContentValues.TAG;
//import lib.main.java.com.example.lib.*;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Entry;
import com.example.lib.Ticket;
import com.example.lib.create_pair;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    TextView timeTV;
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
        /**
         *needed Week Method: public String weekForViewResult ()
         */
        ref = FirebaseDatabase.getInstance().getReference().child("Week10");
        TV=(TextView) findViewById(R.id.outputTV);
        timeTV=(TextView) findViewById(R.id.timeTV);
        readWeek();
    }

    private void  readWeek(){
        entryArrayList = new ArrayList<Entry>();
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Cannot read user telegram handle", task.getException());
                    Toast.makeText(OutputActivity.this,"Cannot read your result, log out and try again",Toast.LENGTH_LONG);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(OutputActivity.this, LoginActivity.class));
                }
                else {
                    DataSnapshot dataSnapshot=task.getResult();
                    if (dataSnapshot.exists()){
                        String readWeekStr="";
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Ticket ticket = postSnapshot.getValue(Ticket.class);
                            Entry entry=new Entry(ticket);
                            //Log.i("time",entry.calStr());
                            if (entry.calStr().equals(priorInput)){
                                entryArrayList.add(entry);
                            }

                        }
                        for (Entry entry:entryArrayList){
                            readWeekStr+=entry.toString();
                            readWeekStr+=", ";
                        }
                        create_pair cp = new create_pair();
                        cp.Create(entryArrayList);

                        /**
                         * TODO: Call your processing methods here
                         * (Entry ArrayList exists as populated here)
                         * */
                        //TV.setText("none");
                        TV.setText(cp.find_pair(cp.getStore_pair(),currentUser));
                        Log.i("test",cp.find_pair(cp.getStore_pair(),currentUser));
                        timeTV.setText(priorInput);
                        Log.i("test2",priorInput);
                    }
                    else{
                        //dont know why it gets blank
                        Log.i("else", "Still in else block.");//didnt work
                        TV.setText("You did not input for Week10");
                    }
                }

            }
        });

    }
    //https://firebase.google.com/docs/database/web/read-and-write



}



    /**TODO: Process entryArrayList using Pair and create_Pair
     * Please import the classes from the lib module so that we
     * can keep backend separate from frontend
     * done
     *
     * */
