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

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
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
