package com.example.CA2.controller;

import java.sql.SQLException;
import java.util.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dbaccess.*;

@RestController
public class CheckoutController {
    @RequestMapping(method = RequestMethod.POST, path = "/CheckOut", consumes = "application/json")
    public Map<String, Object> handleCheckout(@RequestBody Map<String, Object> payload) {
        Integer customerId = (Integer) payload.get("customerId");
        @SuppressWarnings("unchecked")
        ArrayList<Map<String, Object>> services = (ArrayList<Map<String, Object>>) payload.get("services");

        ArrayList<Map<String, Object>> availableServices = new ArrayList<>();
        ArrayList<Map<String, Object>> unavailableServices = new ArrayList<>();
        double totalPrice = 0.0;
        double gstRate = 0.09;

        try {
            ServiceDAO serviceDAO = new ServiceDAO();

            for (Map<String, Object> service : services) {
                Integer serviceId = (Integer) service.get("serviceId");
                java.sql.Date bookingDate = java.sql.Date.valueOf((String) service.get("bookingDate"));
                java.sql.Time bookingTime = java.sql.Time.valueOf((String) service.get("bookingTime"));

                Map<String, Object> serviceDetails = serviceDAO.getServiceDetailsById(serviceId);
                if (serviceDetails != null && (Integer) serviceDetails.get("noOfStaff") > 0) {
                    // Add to available services and calculate price
                    serviceDetails.put("bookingDate", bookingDate.toString());
                    serviceDetails.put("bookingTime", bookingTime.toString());
                    availableServices.add(serviceDetails);
                    totalPrice += (Double) serviceDetails.get("price");
                } else {
                    // Add to unavailable services
                    unavailableServices.add(serviceDetails);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculate GST and final total
        double gst = totalPrice * gstRate;
        double finalTotal = totalPrice + gst;

        Map<String, Object> response = new HashMap<>();
        response.put("customerId", customerId);
        response.put("availableServices", availableServices);
        response.put("unavailableServices", unavailableServices);
        response.put("totalPrice", totalPrice);
        response.put("gst", gst);
        response.put("finalTotal", finalTotal);
        return response;
    }
}