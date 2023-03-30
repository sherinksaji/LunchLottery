package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Entry;
import com.example.lib.Ticket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ViewPopulatedSlots extends AppCompatActivity{

   /** @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_populated_slots);
    }*/

   DatabaseReference ref;
    TextView TV;

    ArrayList<Entry> entryArrayList;
    String priorInput;
    ArrayList<String> calStrArrayList;
    ArrayList<String> populatedSlots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_populated_slots);
        Intent intent=getIntent();
        entryArrayList=new ArrayList<>();

        /**
         *needed Week Method: public String weekForViewResult ()
         */
        String weekNode= "Week10";
        ref = FirebaseDatabase.getInstance().getReference().child(weekNode);
        TV=(TextView) findViewById(R.id.textView3);
        readWeek();
    }

    private void readWeek(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                entryArrayList.clear();
                Map<String,Integer> calStrQty=new HashMap<String, Integer>();
                if (dataSnapshot.exists()){
                    //make recycler view visible
                    String readWeekStr="";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        Ticket ticket = postSnapshot.getValue(Ticket.class);
                        Entry entry=new Entry(ticket);
                        if (calStrQty.containsKey(entry.calStr())){
                            Integer newQty=calStrQty.get(entry.calStr())+1;
                            calStrQty.put(entry.calStr(),newQty);
                        }
                        else{
                            calStrQty.put(entry.calStr(), 1);
                        }
                    }
                    for (Map.Entry<String, Integer> set :
                            calStrQty.entrySet()) {

                        // Printing all elements of a Map
                        readWeekStr+=(set.getKey() + " = "
                                + set.getValue())+"\n";

                    }
                    TV.setText(readWeekStr);

                }
                else{
                //make recycler view invisible


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