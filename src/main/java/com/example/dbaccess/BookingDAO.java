package com.example.dbaccess;

import java.sql.*;
import java.util.*;

public class BookingDAO {

    public ArrayList<Map<String, Object>> getTodaysBookings() throws SQLException {
        Connection conn = null;
        ArrayList<Map<String, Object>> bookings = new ArrayList<>();


        try {
                conn = DBConnection.getConnection();

                String sqlStr = "SELECT c.username, c.email, c.address, s.service_name, " +
                                "b.booking_date, b.booking_time, b.booking_progress, b.payment_status " +
                                "FROM booking b " +
                                "JOIN customers c ON b.customer_id = c.customer_id " +
                                "JOIN service s ON b.service_id = s.service_id " +
                                "WHERE b.booking_date = CURRENT_DATE";
             PreparedStatement stmt = conn.prepareStatement(sqlStr);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("customerName", rs.getString("username"));
                booking.put("email", rs.getString("email"));
                booking.put("address", rs.getString("address"));
                booking.put("serviceName", rs.getString("service_name"));
                booking.put("bookingDate", rs.getDate("booking_date").toString());
                booking.put("bookingTime", rs.getString("booking_time"));
                booking.put("bookingProgress", rs.getString("booking_progress"));
                String paymentStatus = rs.getString("payment_status");
                booking.put("payment_status", paymentStatus.equals("t") ? "True" : "False");

                bookings.add(booking);
                System.out.println(booking);
            }
        }finally {
            if (conn != null) conn.close();
        }
        return bookings;
    }
    

    public void createBooking(Integer customerId, Integer categoryId, Integer serviceId, java.sql.Date bookingDate, java.sql.Time bookingTime, String bookingProgress, boolean paymentStatus, String transactionId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO booking (customer_id, category_id, service_id, booking_date, booking_time, booking_progress, payment_status, created_at, updated_at, transaction_id) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, categoryId);
            pstmt.setInt(3, serviceId);
            pstmt.setDate(4, bookingDate);
            pstmt.setTime(5, bookingTime);
            pstmt.setString(6, bookingProgress);
            pstmt.setBoolean(7, paymentStatus);
            pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(10, transactionId);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " booking(s) added.");
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    
    public ArrayList<Map<String, Object>> getBookingsByDay(String day) throws SQLException {
        Connection conn = null;
        ArrayList<Map<String, Object>> bookings = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sqlStr = "SELECT c.username, c.email, c.address, s.service_name, " +
                            "b.booking_date, b.booking_time, b.booking_progress, b.payment_status " +
                            "FROM booking b " +
                            "JOIN customers c ON b.customer_id = c.customer_id " +
                            "JOIN service s ON b.service_id = s.service_id " +
                            "WHERE b.booking_date = TO_DATE(?, 'YYYY-MM-DD')";
            PreparedStatement stmt = conn.prepareStatement(sqlStr);
            stmt.setString(1, day);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("customerName", rs.getString("username"));
                booking.put("email", rs.getString("email"));
                booking.put("address", rs.getString("address"));
                booking.put("serviceName", rs.getString("service_name"));
                booking.put("bookingDate", rs.getDate("booking_date").toString());
                booking.put("bookingTime", rs.getString("booking_time"));
                booking.put("bookingProgress", rs.getString("booking_progress"));
                String paymentStatus = rs.getString("payment_status");
                booking.put("payment_status", paymentStatus.equals("t") ? "True" : "False");
                bookings.add(booking);
            }
        } finally {
            if (conn != null) conn.close();
        }

        return bookings;
    }


    public ArrayList<Map<String, Object>> getBookingsByMonth(String monthYear) throws SQLException {
        Connection conn = null;
        ArrayList<Map<String, Object>> bookings = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String[] parts = monthYear.split("-"); 
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            String sqlStr = "SELECT c.username, c.email, c.address, s.service_name, " +
                            "b.booking_date, b.booking_time, b.booking_progress, b.payment_status " +
                            "FROM booking b " +
                            "JOIN customers c ON b.customer_id = c.customer_id " +
                            "JOIN service s ON b.service_id = s.service_id " +
                            "WHERE EXTRACT(MONTH FROM b.booking_date) = ? AND EXTRACT(YEAR FROM b.booking_date) = ?";
            PreparedStatement stmt = conn.prepareStatement(sqlStr);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("customerName", rs.getString("username"));
                booking.put("email", rs.getString("email"));
                booking.put("address", rs.getString("address"));
                booking.put("serviceName", rs.getString("service_name"));
                booking.put("bookingDate", rs.getDate("booking_date").toString());
                booking.put("bookingTime", rs.getString("booking_time"));
                booking.put("bookingProgress", rs.getString("booking_progress"));
                String paymentStatus = rs.getString("payment_status");
                booking.put("payment_status", paymentStatus.equals("t") ? "True" : "False");
                bookings.add(booking);
            }
        } finally {
            if (conn != null) conn.close();
        }

        return bookings;
    }



    public ArrayList<Map<String, Object>> getBookingsByYear(String year) throws SQLException {
        Connection conn = null;
        ArrayList<Map<String, Object>> bookings = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sqlStr = "SELECT c.username, c.email, c.address, s.service_name, " +
                            "b.booking_date, b.booking_time, b.booking_progress, b.payment_status " +
                            "FROM booking b " +
                            "JOIN customers c ON b.customer_id = c.customer_id " +
                            "JOIN service s ON b.service_id = s.service_id " +
                            "WHERE EXTRACT(YEAR FROM b.booking_date) = ?";
            PreparedStatement stmt = conn.prepareStatement(sqlStr);
            stmt.setInt(1, Integer.parseInt(year)); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("customerName", rs.getString("username"));
                booking.put("email", rs.getString("email"));
                booking.put("address", rs.getString("address"));
                booking.put("serviceName", rs.getString("service_name"));
                booking.put("bookingDate", rs.getDate("booking_date").toString());
                booking.put("bookingTime", rs.getString("booking_time"));
                booking.put("bookingProgress", rs.getString("booking_progress"));
                String paymentStatus = rs.getString("payment_status");
                booking.put("payment_status", paymentStatus.equals("t") ? "True" : "False");
                bookings.add(booking);
            }
        } finally {
            if (conn != null) conn.close();
        }

        return bookings;
    }

}
