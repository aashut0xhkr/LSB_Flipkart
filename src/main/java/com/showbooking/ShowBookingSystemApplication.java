package com.showbooking;

import com.showbooking.Service.BookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ShowBookingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShowBookingSystemApplication.class, args);
        BookingService service = new BookingService();

        service.registerShow("TMKOC", "Comedy");

        Map<String, Integer> slots = new HashMap<>();
        slots.put("9:00-10:00", 3);
        slots.put("12:00-13:00", 2);
        slots.put("15:00-16:00", 5);

        service.onboardShowSlots("TMKOC", slots);

        service.showAvailByGenre("Comedy");

        service.bookTicket("UserA", "TMKOC", "12:00-13:00", 2);

        service.showAvailByGenre("Comedy");

        service.viewBookings("UserA");

        service.cancelBooking("1000");

        service.showAvailByGenre("Comedy");

        service.trendingShow();


    }
}