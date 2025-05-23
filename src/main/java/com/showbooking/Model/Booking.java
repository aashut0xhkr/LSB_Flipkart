package com.showbooking.Model;

public class Booking {
    private String id;
    private String userName;
    private String showName;
    private String slotTime;
    private int persons;

    public Booking(String id, String userName, String showName, String slotTime, int persons) {
        this.id = id;
        this.userName = userName;
        this.showName = showName;
        this.slotTime = slotTime;
        this.persons = persons;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getShowName() {
        return showName;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public int getPersons() {
        return persons;
    }

}
