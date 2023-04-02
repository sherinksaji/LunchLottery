package com.example.lib;

import java.util.Comparator;

public class IdentifiableComparator implements Comparator<Identifiable> {
    @Override
    public int compare(Identifiable identifiable, Identifiable t1) {
        return identifiable.identity().compareTo(t1.identity());
    }
}
