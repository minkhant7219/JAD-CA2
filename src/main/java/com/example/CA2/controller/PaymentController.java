package com.example.CA2.controller;

import java.sql.SQLException;
import java.util.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dbaccess.*;

@RestController
public class PaymentController {
    private final HttpSession session;

    public PaymentController(HttpSession session) {
        this.session = session;
    }
    @RequestMapping(method = RequestMethod.POST, path = "/VisaPayment", consumes = "application/json")
    public String VisaPayment(@RequestBody Map<String, Object> payload, HttpSession session) {
        Integer customerId = (Integer) payload.get("customerId");
        Double totalAmount = (Double) payload.get("totalAmount");
        String cardNumber = (String) payload.get("cardNumber");
        String expiryDate = (String) payload.get("expiryDate");
        String cvc = (String) payload.get("cvc");
        String cardholderName = (String) payload.get("cardholderName");
        
System.out.println(payload);
        
        @SuppressWarnings("unchecked")
        ArrayList<Map<String, Object>> services = (ArrayList<Map<String, Object>>) payload.get("bookings");

        Stripe.apiKey = "sk_test_51QqWvSKo6ejhURx9Wn87HizcMJhQxhoKtBpdoMBNyN3FbjAE5jyo2r5C3I1zQJJnCYXYMdd4AY01UyXUTOhAtYN5008bieJ9Dr"; // Use your Stripe secret key

        Map<String, Object> response = new HashMap<>();
        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            // Create a charge with Stripe
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int) (totalAmount * 100)); 
            chargeParams.put("currency", "sgd");
            chargeParams.put("source", "tok_visa");
            chargeParams.put("description", "Payment for Booking Services");

            Charge charge = Charge.create(chargeParams);
            String transactionId = charge.getId();

            PaymentDAO paymentDAO = new PaymentDAO();
                 paymentDAO.createPayment(customerId, totalAmount, transactionId);


            BookingDAO bookingDAO = new BookingDAO();
            for (Map<String, Object> service : services) {
                Integer serviceId = (Integer) service.get("serviceId");
                java.sql.Date bookingDate = java.sql.Date.valueOf((String) service.get("bookingDate"));
                java.sql.Time bookingTime = java.sql.Time.valueOf((String) service.get("bookingTime"));
                
                Map<String, Object> serviceDetails = serviceDAO.getServiceDetailsById(serviceId);
                if (serviceDetails == null) {
                    return "Error: Service with ID " + serviceId + " not found.";
                }else {
                    System.out.println(serviceId);
                }

                Integer categoryId = (Integer) serviceDetails.get("categoryId");
                if (categoryId == null) {
                    return "Error: Category ID missing for service ID " + serviceId;
                }else {
                    System.out.println(categoryId);
                }
                
                bookingDAO.createBooking(customerId, categoryId, serviceId, bookingDate, bookingTime, "Completed", true, transactionId);
                serviceDAO.reduceStaffCountByServiceId(serviceId);
                session.removeAttribute("bookingCart");
            }

        } catch (StripeException | SQLException e) {
            return "Payment failed";
        }

        return "Booking Completed Successfully";
    }

    
   

    @RequestMapping(method = RequestMethod.POST, path = "/InPersonPayment", consumes = "application/json")
    public String InPersonPayment(@RequestBody Map<String, Object> payload, HttpSession session) {
        Integer customerId = (Integer) payload.get("customerId");
        @SuppressWarnings("unchecked")
        ArrayList<Map<String, Object>> bookings = (ArrayList<Map<String, Object>>) payload.get("bookings");

        try {
            BookingDAO bookingDAO = new BookingDAO();
            ServiceDAO serviceDAO = new ServiceDAO();

            for (Map<String, Object> booking : bookings) {
                Integer serviceId = (Integer) booking.get("serviceId");
                java.sql.Date bookingDate = java.sql.Date.valueOf((String) booking.get("bookingDate"));
                java.sql.Time bookingTime = java.sql.Time.valueOf((String) booking.get("bookingTime"));

                Map<String, Object> serviceDetails = serviceDAO.getServiceDetailsById(serviceId);
                if (serviceDetails == null) {
                    return "Error: Service with ID " + serviceId + " not found.";
                }else {
                    System.out.println(serviceId);
                }

                Integer categoryId = (Integer) serviceDetails.get("categoryId");
                if (categoryId == null) {
                    return "Error: Category ID missing for service ID " + serviceId;
                }else {
                    System.out.println(categoryId);
                }
                String transactionId = null;
                bookingDAO.createBooking(customerId, categoryId, serviceId, bookingDate, bookingTime, "Completed", false, transactionId);
                
                // Reduce staff count
                serviceDAO.reduceStaffCountByServiceId(serviceId);
                session.removeAttribute("bookingCart");
            }
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }

        return "Booking completed successfully!";
    }
}

