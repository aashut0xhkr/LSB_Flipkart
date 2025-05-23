package com.showbooking.Model;

import java.util.HashMap;
import java.util.Map;

public class LiveShow {
    private String showName;
    private Genre genre;
    private Map<String, Slot> slots;
    private int totalTicketsBooked;

    public LiveShow(String showName, Genre genre) {
        this.showName = showName;
        this.genre = genre;
        this.slots = new HashMap<>();
    }

    public String getShowName() {
        return showName;
    }

    public Genre getGenre() {
        return genre;
    }

    public Map<String, Slot> getSlots() {
        return slots;
    }

    public int getTotalTicketsBooked() {
        return totalTicketsBooked;
    }
    public void incrementTicketsBooked(int persons) {
        this.totalTicketsBooked += persons;
    }

    public void decrementTicketsBooked(int persons) {
        this.totalTicketsBooked -= persons;
    }
}
