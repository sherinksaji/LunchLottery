package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.CreateSlots;
import com.example.lib.LotteryEntry;
import com.example.lib.PopulatedSlot;
import com.example.lib.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPopulatedSlots extends AppCompatActivity{


   DatabaseReference ref;
    TextView nobodyJoinedTV;

    ArrayList<LotteryEntry> entryArrayList;
    ArrayList<PopulatedSlot> populatedSlots;
    RecyclerView.Adapter<PopulatedSlotAdapter.SlotsHolder> populatedSlotAdapter;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_populated_slots);
        entryArrayList=new ArrayList<>();
        populatedSlots=new ArrayList<>();


        /**
         *needed Week Method: public String weekForViewResult ()
         */
        String weekNode= "Week10";
        ref = FirebaseDatabase.getInstance().getReference().child(weekNode);
        nobodyJoinedTV=(TextView) findViewById(R.id.nobodyJoinedTV);
        readWeek();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        populatedSlotAdapter=new PopulatedSlotAdapter(this,populatedSlots);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setAdapter(populatedSlotAdapter);
    }

    private void readWeek(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                entryArrayList.clear();
                populatedSlots.clear();
                if (dataSnapshot.exists()){
                    nobodyJoinedTV.setVisibility(View.GONE);
                    String readWeekStr="";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        Ticket ticket = postSnapshot.getValue(Ticket.class);
                        LotteryEntry entry=new LotteryEntry(ticket);
                        entryArrayList.add(entry);

                    }
                    CreateSlots createSlots=new CreateSlots(populatedSlots,entryArrayList);
                    createSlots.Create();

                    Log.i("populatedSlots",populatedSlots.toString());
                    populatedSlotAdapter.notifyDataSetChanged();
                    Log.i("Adapter", "notifyDataSetChanged() called");
                }
                else{

                    nobodyJoinedTV.setVisibility(View.VISIBLE);

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