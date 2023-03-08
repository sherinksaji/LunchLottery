package com.example.lib;

import java.util.ArrayList;

public class Pair {
    private ArrayList <Entry> pair=new ArrayList<>();
    public Pair(Entry entry1,Entry entry2) {
        this.pair = pair;
        pair.add(entry1);
        pair.add(entry2);
    }

    public boolean inPair(String telegramHandle){
        for (Entry e:this.pair){
            if (telegramHandle.equals(e.getTelegramHandle())){
                return true;
            }
        }
        return false;
    }

    public Entry match(String telegramHandle){
        //only to be used if inPair is true
        if (this.pair.get(0).getTelegramHandle()==telegramHandle){
            return this.pair.get(1);
        }
        else return this.pair.get(0);
    }



}
