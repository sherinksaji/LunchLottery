package com.example.lib;

import java.util.Comparator;

public class EntryComparator implements  Comparator<LotteryEntry>{
    @Override
    public int compare(LotteryEntry entry, LotteryEntry t1) {
        return entry.getTelegramHandle().compareTo(t1.getTelegramHandle());
    }
    //TODO: write a comparator class to sort an arrayList of entries by telegram handle
    //can u test out that its is the arraylist order being printed out every time

}
