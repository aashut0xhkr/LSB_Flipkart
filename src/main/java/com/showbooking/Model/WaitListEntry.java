package com.showbooking.Model;

public class WaitListEntry {
    private String userName;
    private int persons;

    public WaitListEntry(String userName, int persons) {
        this.userName = userName;
        this.persons = persons;
    }

    public String getUserName() {
        return userName;
    }

    public int getPersons() {
        return persons;
    }
}
