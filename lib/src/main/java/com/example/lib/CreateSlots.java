package com.example.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateSlots {
    public ArrayList<PopulatedSlot> getPopulatedSlotArrayList() {
        return populatedSlotArrayList;
    }

    private ArrayList<PopulatedSlot> populatedSlotArrayList;

    private ArrayList<Entry> entryArrayList;

    public CreateSlots(ArrayList<PopulatedSlot> populatedSlotArrayList, ArrayList<Entry> entryArrayList) {
        this.populatedSlotArrayList = populatedSlotArrayList;
        this.entryArrayList = entryArrayList;
    }

    public void Create(){
        if(entryArrayList.size()>0) {


            Map<String, Integer> calStrQty = new HashMap<String, Integer>();
            for (Entry e : entryArrayList) {
                if (calStrQty.containsKey(e.calStr())) {
                    Integer newQty = calStrQty.get(e.calStr()) + 1;
                    calStrQty.put(e.calStr(), newQty);
                } else {
                    calStrQty.put(e.calStr(), 1);
                }
            }

            for (Map.Entry<String, Integer> set : calStrQty.entrySet()) {
                populatedSlotArrayList.add(new PopulatedSlot(set.getKey(), set.getValue()));
            }
        }
    }

}

