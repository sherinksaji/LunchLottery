package com.example.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class create_pair {
    ArrayList<Pair> Store_pair = new ArrayList<Pair>();
    public void Create(ArrayList<LotteryEntry> lst){ //create pairs from entry array list

        Collections.shuffle(lst, new Random(100));
        if (lst.size()==1){
            Store_pair.add(new Pair((lst.get(0).getTelegramHandle()),"No one signed up"));
        }
        else if (lst.size()%2==1){
            Store_pair.add(new Pair((lst.get(0).getTelegramHandle()),(lst.get(1).getTelegramHandle()),(lst.get(2).getTelegramHandle())));
            Store_pair.add(new Pair((lst.get(1).getTelegramHandle()),(lst.get(0).getTelegramHandle()),(lst.get(2).getTelegramHandle())));
            Store_pair.add(new Pair((lst.get(2).getTelegramHandle()),(lst.get(0).getTelegramHandle()),(lst.get(1).getTelegramHandle())));
            for (int i =3; i< lst.size();i+=2){
                Store_pair.add(new Pair(lst.get(i).getTelegramHandle() , lst.get(i+1).getTelegramHandle()));
                Store_pair.add(new Pair(lst.get(i+1).getTelegramHandle() , lst.get(i).getTelegramHandle()));
            }
        }
        else {
            for (int i =0; i< lst.size();i+=2){
                Store_pair.add(new Pair(lst.get(i).getTelegramHandle() , lst.get(i+1).getTelegramHandle()));
                Store_pair.add(new Pair(lst.get(i+1).getTelegramHandle() , lst.get(i).getTelegramHandle()));
            }
        }
    }
    public ArrayList<Pair> getStore_pair() { //return array list with all the stored pairs
        return Store_pair;
    }

    public String find_pair(ArrayList<Pair> lst, String id){ // return the partner paired to
        for (int i = 0; i<lst.size();i++){
            if (lst.get(i).getId().equalsIgnoreCase(id)) {
                return lst.get(i).getPartner();
            }
        }
        return "No Pair found"; //everyone should have a partner unless only one person in the time slot, which we would show no partner.
    }

}