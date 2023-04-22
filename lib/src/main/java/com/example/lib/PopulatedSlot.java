package com.example.lib;

public class PopulatedSlot implements Countable{
    private String timing;
    private int numberOfPeople;

    public PopulatedSlot(String timing, int numberOfPeople) {
        this.timing = timing;
        this.numberOfPeople = numberOfPeople;
    }

    public String getTiming() {
        return timing;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }


    @Override
    public int count() {
        return this.getNumberOfPeople();
    }

    @Override
    public String timeSlot() {
        return this.getTiming();
    }
}
