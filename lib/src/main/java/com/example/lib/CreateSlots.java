package com.example.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateSlots {
    public ArrayList<Countable> getcountables() {
        return countables;
    }

    private ArrayList<Countable> countables;

    private ArrayList<Slottable> slotables;

    public CreateSlots(ArrayList<Countable> countables, ArrayList<Slottable> slotables) {
        this.countables = countables;
        this.slotables = slotables;
    }

    public void Create(){
        if(this.slotables.size()>0) {


            Map<String, Integer> timeSlotQty = new HashMap<String, Integer>();
            for (Slottable s : this.slotables) {
                if (timeSlotQty.containsKey(s.timeSlot())) {
                    Integer newQty = timeSlotQty.get(s.timeSlot()) + 1;
                    timeSlotQty.put(s.timeSlot(), newQty);
                } else {
                    timeSlotQty.put(s.timeSlot(), 1);
                }
            }

            for (Map.Entry<String, Integer> set : timeSlotQty.entrySet()) {
                countables.add(new PopulatedSlot(set.getKey(), set.getValue()));
            }
        }
    }

}

