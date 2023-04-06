package com.example.androidapp;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Countable;


import com.example.lib.Slottable;

import com.example.lib.Week;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
interface UpdateArrayListOnChange{
    void onDataChanged(Boolean Success);
}
public class ViewPopulatedSlots extends AppCompatActivity {


    DatabaseReference ref;
    TextView nobodyJoinedTV;

    ArrayList<Slottable> allTicketsUnderWeekX;
    ArrayList<Countable> populatedSlots;
    RecyclerView.Adapter<PopulatedSlotAdapter.SlotsHolder> populatedSlotAdapter;
    String weekNode;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_populated_slots);
        allTicketsUnderWeekX = new ArrayList<>();
        populatedSlots = new ArrayList<>();


        /**
         *needed Week Method: public String weekForViewResult ()
         */
        weekNode = new Week.NextWeek().getWeekTitle();
        ref = FirebaseDatabase.getInstance().getReference().child(weekNode);
        nobodyJoinedTV = (TextView) findViewById(R.id.nobodyJoinedTV);
        updateRecyclerView();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        populatedSlotAdapter = new PopulatedSlotAdapter(this, populatedSlots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(populatedSlotAdapter);
    }

    private void updateRecyclerView() {
        CreateSlots.updateCountables(weekNode, populatedSlots, new UpdateArrayListOnChange() {
            @Override
            public void onDataChanged(Boolean Success) {
                if (Success) {
                    if (populatedSlots.size() == 0) {
                        nobodyJoinedTV.setVisibility(View.VISIBLE);
                        Log.i("updateRecyclerView", "populatedSlots size is zero");
                    } else {
                        Log.i("updateRecyclerView", "populatedSlots size is not zero");
                        nobodyJoinedTV.setVisibility(View.GONE);
                    }
                    //need to notify adapter when populatedSlots gets updated, ALSO when the size becomes zero
                    populatedSlotAdapter.notifyDataSetChanged();
                } else {
                    nobodyJoinedTV.setText(DatabaseOperations.SOMETHINGWRONG);
                    Log.i("updateRecyclerView", "something wrong");
                    Toast.makeText(ViewPopulatedSlots.this, "Something wrong. Logging out. Try again!", Toast.LENGTH_LONG).show();
                    AuthenticationOperations.logout();
                    startActivity(new Intent(ViewPopulatedSlots.this, LoginActivity.class));
                }
            }
        });
    }

}


    //https://firebase.google.com/docs/database/web/read-and-write


