package com.example.lib;

import java.util.ArrayList;

public class Pair {
    private String id;
    private String partner;

    public Pair(String id, String partner) {
        this.id = id; //yourself
        this.partner = partner; //person paired with
    }

    public Pair(String id, String partner, String partner2) { //only used when entry array list is odd numbered
        this.id = id;
        this.partner = partner + ",\n" + partner2;
    }

    public String getId() {
        return id;
    }

    public String getPartner() {
        return partner;
    }
}
