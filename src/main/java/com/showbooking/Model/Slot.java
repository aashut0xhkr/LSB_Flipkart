package com.showbooking.Model;

import java.util.LinkedList;
import java.util.Queue;

public class Slot {
    private String timeRange;
    private int capacity;
    private int availableCapacity;
    private Queue<WaitListEntry> waitlist = new LinkedList<>();

    public Slot(String timeRange, int capacity) {
        this.timeRange = timeRange;
        this.capacity = capacity;
        this.availableCapacity = capacity;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public Queue<WaitListEntry> getWaitlist() {
        return waitlist;
    }

    public void decreaseAvailableCapacity(int persons) {
        this.availableCapacity -= persons;
    }

    public void increaseAvailableCapacity(int persons) {
        this.availableCapacity += persons;
    }
}
