package com.example.androidapp;

import android.util.Log;

import com.example.lib.Countable;
import com.example.lib.CountableComparator;
import com.example.lib.PopulatedSlot;
import com.example.lib.Slottable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
interface DbReadArrayListOnChange{
    void onDataChanged(Boolean Success,ArrayList<Slottable>Slottables);
}
public class CreateSlots {


    private static void create(ArrayList <Slottable> slottablesFromDb,ArrayList <Countable> countablesFromFE){
        if(slottablesFromDb.size()>0) {


            Map<String, Integer> timeSlotQty = new HashMap<>();
            for (Slottable s : slottablesFromDb) {
                if (timeSlotQty.containsKey(s.timeSlot())) {

                    if (timeSlotQty.get(s.timeSlot())!=null){
                        Integer qty = timeSlotQty.get(s.timeSlot());
                        qty+=1;
                        timeSlotQty.put(s.timeSlot(), qty);
                    }

                } else {
                    timeSlotQty.put(s.timeSlot(), 1);
                }
            }


            for (Map.Entry<String, Integer> set : timeSlotQty.entrySet()) {
                countablesFromFE.add(new PopulatedSlot(set.getKey(), set.getValue()));
            }
           Collections.sort(countablesFromFE,new CountableComparator());
        }
    }
    /**
     * declaring an ArrayList as final only prevents the reference to the ArrayList from being changed,
     * but it does not prevent the elements of the ArrayList from being modified using its methods.
     * (ChatGPT)
     * */
    public static void updateCountables(String weekNode,final ArrayList<Countable> countablesFromFE,final UpdateArrayListOnChange listener ){
        DatabaseOperations.readWeekPersistent(weekNode, new DbReadArrayListOnChange() {
            @Override
            public void onDataChanged(Boolean Success, ArrayList<Slottable> Slottables) {
                countablesFromFE.clear();
                //Slottables will be a new address every time
                //countables address is unchanged
                if (Success){
                    //if success, then the arraylist is definately non-null
                    Log.i("slottables",Slottables.toString());
                    create(Slottables,countablesFromFE);
                    listener.onDataChanged(true);
                    Log.i("countablesFromFE",countablesFromFE.toString());
                }
                else{
                    listener.onDataChanged(false);
                    Log.i("countablesFromFE","some technical error.countableFromFE is empty");

                }
            }
        });
    }

}

