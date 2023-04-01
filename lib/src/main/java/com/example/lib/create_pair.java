package com.example.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class create_pair {
    ArrayList<Pair> Store_pair = new ArrayList<Pair>();
    public void Create(ArrayList<Identifiable> lst){ //create pairs from entry array list

        if (lst.size()>1) {
            Collections.sort(lst, new EntryComparator());
            Collections.shuffle(lst, new Random(100)); // to have a randomise the pairs
        }
        if (lst.size()==1){
            Store_pair.add(new Pair((lst.get(0).identify()),"No one signed up"));
        }
        else if (lst.size()%2==1){
            Store_pair.add(new Pair((lst.get(0).identify()),(lst.get(1).identify()),(lst.get(2).identify())));
            Store_pair.add(new Pair((lst.get(1).identify()),(lst.get(0).identify()),(lst.get(2).identify())));
            Store_pair.add(new Pair((lst.get(2).identify()),(lst.get(0).identify()),(lst.get(1).identify())));
            for (int i =3; i< lst.size();i+=2){
                Store_pair.add(new Pair(lst.get(i).identify() , lst.get(i+1).identify()));
                Store_pair.add(new Pair(lst.get(i+1).identify() , lst.get(i).identify()));
            }
        }
        else {
            for (int i =0; i< lst.size();i+=2){
                Store_pair.add(new Pair(lst.get(i).identify() , lst.get(i+1).identify()));
                Store_pair.add(new Pair(lst.get(i+1).identify() , lst.get(i).identify()));
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