package com.example.lib;

public class PopulatedSlot {
    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    String timing;
    int count;

    public PopulatedSlot(String timing, int count) {
        this.timing = timing;
        this.count = count;
    }

    public PopulatedSlot(){

    }
}
