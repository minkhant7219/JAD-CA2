package com.example.CA2.controller;

import com.example.dbaccess.BookingDAO;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;
@RestController
public class BookingController {

    private final BookingDAO bookingDAO = new BookingDAO();

    @RequestMapping(method = RequestMethod.GET, path = "/bookings/today")
    public ArrayList<Map<String, Object>> getTodaysBookings() {
        try {
            return bookingDAO.getTodaysBookings();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bookings/filterByDay")
    public ArrayList<Map<String, Object>> getBookingsByDay(@RequestParam("filterValue") String filterValue) {
        try {
            System.out.println( bookingDAO.getBookingsByDay(filterValue));
            return bookingDAO.getBookingsByDay(filterValue);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bookings/filterByMonth")
    public ArrayList<Map<String, Object>> getBookingsByMonth(@RequestParam("filterValue") String filterValue) {
        try {
            return bookingDAO.getBookingsByMonth(filterValue);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bookings/filterByYear")
    public ArrayList<Map<String, Object>> getBookingsByYear(@RequestParam("filterValue") String filterValue) {
        try {
            return bookingDAO.getBookingsByYear(filterValue);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
