package com.example.lib;

import java.util.Comparator;

public class CountableComparator implements Comparator<Countable> {
    @Override
    public int compare(Countable countable, Countable t1) {
        if (countable.count()>t1.count()){return -1;}
        else if (countable.count()==t1.count()){return 0;}
        else {return 1;}
    }
}
