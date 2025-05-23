package com.showbooking.Service;

import com.showbooking.Model.*;

import java.util.*;

public class BookingService {
    private Map<String, LiveShow> shows = new HashMap<>();
    private Map<String, Booking> bookings = new HashMap<>();
    private Map<String, List<Booking>> userBookings = new HashMap<>();
    private LiveShow trendingShow = null;

    private int bookingIdCounter = 1000;

    private static final List<String> VALID_SLOTS = Arrays.asList(
            "9:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00",
            "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00",
            "17:00-18:00", "18:00-19:00", "19:00-20:00", "20:00-21:00"
    );

    public void registerShow(String showName, String genre) {
        if (shows.containsKey(showName)) {
            System.out.println("Show already registered!");
            return;
        }
        Genre genreEnum = Genre.valueOf(genre.toUpperCase());
        LiveShow show = new LiveShow(showName,genreEnum);
        shows.put(showName, show);
        System.out.println(showName + " show is registered!!");
    }

    public void onboardShowSlots(String showName, Map<String, Integer> slotDetails) {
        LiveShow show = shows.get(showName);
        if (show == null) {
            System.out.println("Show not found!");
            return;
        }
        for (Map.Entry<String, Integer> entry : slotDetails.entrySet()) {
            String timeRange = entry.getKey();
            int capacity = entry.getValue();
            if (!VALID_SLOTS.contains(timeRange)) {
                System.out.println("Invalid slot timing: " + timeRange);
                continue;
            }
            if (show.getSlots().containsKey(timeRange)) {
                System.out.println("Slot already registered for this show: " + timeRange);
                continue;
            }
            show.getSlots().put(timeRange, new Slot(timeRange, capacity));
        }
        System.out.println("Done!");
    }

    public void showAvailByGenre(String genre) {
        for (LiveShow show : shows.values()) {
            if (show.getGenre().name().equalsIgnoreCase(genre)) {
                for (Slot slot : show.getSlots().values()) {
                    if (slot.getAvailableCapacity() > 0) {
                        System.out.println(show.getShowName() + ": (" + slot.getTimeRange() + ") " + slot.getAvailableCapacity());
                    }
                }
            }
        }
    }

    public void bookTicket(String userName, String showName, String slotTime, int persons) {
        LiveShow show = shows.get(showName);
        if (show == null) {
            System.out.println("Show not found!");
            return;
        }
        Slot slot = show.getSlots().get(slotTime);
        if (slot == null) {
            System.out.println("Slot not found for the show!");
            return;
        }

        // Check user already booked in same time slot
        if (userBookings.containsKey(userName)) {
            for (Booking b : userBookings.get(userName)) {
                if (b.getSlotTime().equals(slotTime)) {
                    System.out.println("Cannot book overlapping slots for user: " + userName);
                    return;
                }
            }
        }

        if (slot.getAvailableCapacity() >= persons) {
            slot.decreaseAvailableCapacity(persons);
            String bookingId = generateBookingId();
            Booking booking = new Booking(bookingId, userName, showName, slotTime, persons);
            bookings.put(bookingId, booking);
            userBookings.computeIfAbsent(userName, k -> new ArrayList<>()).add(booking);
            show.incrementTicketsBooked(persons);
            updateTrendingShow(show);
            System.out.println("Booked. Booking id: " + bookingId);
        } else {
            slot.getWaitlist().add(new WaitListEntry(userName, persons));
            System.out.println("Slot full. Added to waitlist.");
        }
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            System.out.println("Booking id not found!");
            return;
        }
        LiveShow show = shows.get(booking.getShowName());
        Slot slot = show.getSlots().get(booking.getSlotTime());

        slot.increaseAvailableCapacity(booking.getPersons());
        show.decrementTicketsBooked(booking.getPersons());
        bookings.remove(bookingId);

        userBookings.get(booking.getUserName()).remove(booking);

        // Assign booking to first in waitlist
        Queue<WaitListEntry> waitlist = slot.getWaitlist();
        if (!waitlist.isEmpty()) {
            WaitListEntry entry = waitlist.poll();
            bookTicket(entry.getUserName(), booking.getShowName(), booking.getSlotTime(), entry.getPersons());
        }
        System.out.println("Booking Canceled");
    }

    public void viewBookings(String userName) {
        if (!userBookings.containsKey(userName)) {
            System.out.println("No bookings found for user!");
            return;
        }
        for (Booking booking : userBookings.get(userName)) {
            System.out.println("BookingId: " + booking.getId() + ", Show: " + booking.getShowName() +
                    ", Slot: " + booking.getSlotTime() + ", Persons: " + booking.getPersons());
        }
    }

    public void trendingShow() {
        if (trendingShow == null) {
            System.out.println("No trending show yet!");
        } else {
            System.out.println("Trending Show: " + trendingShow.getShowName() + " with " + trendingShow.getTotalTicketsBooked() + " tickets!");
        }
    }

    private String generateBookingId() {
        return String.valueOf(bookingIdCounter++);
    }

    private void updateTrendingShow(LiveShow show) {
        if (trendingShow == null || show.getTotalTicketsBooked() > trendingShow.getTotalTicketsBooked()) {
            trendingShow = show;
        }
    }

}
