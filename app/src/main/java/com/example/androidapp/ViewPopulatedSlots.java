package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Countable;
import com.example.lib.CreateSlots;
import com.example.lib.LotteryTicket;
import com.example.lib.Slottable;
import com.example.lib.Week;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPopulatedSlots extends AppCompatActivity{


   DatabaseReference ref;
    TextView nobodyJoinedTV, button;
    pl.droidsonroids.gif.GifImageView photo;
    ArrayList<Slottable> allTicketsUnderWeekX;
    ArrayList<Countable> populatedSlots;
    RecyclerView.Adapter<PopulatedSlotAdapter.SlotsHolder> populatedSlotAdapter;
    String weekNode,priorInput;
    RecyclerView recyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_populated_slots);
        allTicketsUnderWeekX =new ArrayList<>();
        populatedSlots=new ArrayList<>();
        photo = findViewById(R.id.Photo);
        Intent intent=getIntent();
        priorInput=intent.getStringExtra("priorInput");

        /**
         *needed Week Method: public String weekForViewResult ()
         */
        weekNode=new Week.NextWeek().getWeekTitle();
        ref = FirebaseDatabase.getInstance().getReference().child(weekNode);
        //nobodyJoinedTV=(TextView) findViewById(R.id.nobodyJoinedTV);
        nobodyJoinedTV = findViewById(R.id.nobodyJoinedTV);

        //photo = findViewById(R.id.Photo);
        button = findViewById(R.id.signUp);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        populatedSlotAdapter=new PopulatedSlotAdapter(this,populatedSlots);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setAdapter(populatedSlotAdapter);
        readWeek();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPopulatedSlots.this,InputActivity.class));
                finish();
            }
        });
    }

    private void readWeek(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                allTicketsUnderWeekX.clear();
                populatedSlots.clear();
                if (dataSnapshot.exists()){
                    //nobodyJoinedTV.setVisibility(View.GONE);
                    nobodyJoinedTV.setText("Your selected time is" +priorInput);
                    photo.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    String readWeekStr="";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        LotteryTicket ticket = postSnapshot.getValue(LotteryTicket.class);
                        allTicketsUnderWeekX.add(ticket);

                    }

                    CreateSlots createSlots=new CreateSlots(populatedSlots, allTicketsUnderWeekX);
                    createSlots.Create();

                    Log.i("populatedSlots",populatedSlots.toString());
                    populatedSlotAdapter.notifyDataSetChanged();
                    Log.i("Adapter", "notifyDataSetChanged() called");
                }
                else{
                    nobodyJoinedTV.setVisibility(View.VISIBLE);
                    photo.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }





















                        /**HashSet<String> setCalStrArrayList=new HashSet<>();
                        HashSet<String> setPopulatedSlots=new HashSet<>();

                        for (String calStr: calStrArrayList) {
                            if (setCalStrArrayList.add(calStr) == false) {
                                setPopulatedSlots.add(calStr);
                            }
                        }
                        //If add() returns false it means that element is not allowed in the Set
                        // and that is your duplicate.

                        //https://javarevisited.blogspot.com/2015/06/3-ways-to-find-duplicate-elements-in-array-java.html#axzz7xF81gQtH

                        populatedSlots = new ArrayList<>(setPopulatedSlots);
                        //https://www.geeksforgeeks.org/convert-hashset-to-a-arraylist-in-java/

                        for (String calStr:populatedSlots){
                            readWeekStr+=calStr.toString();
                            readWeekStr+=",\n";
                        }*/


    //https://firebase.google.com/docs/database/web/read-and-write


}