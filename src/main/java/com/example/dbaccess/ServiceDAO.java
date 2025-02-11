package com.example.dbaccess;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ServiceDAO {

    public Map<String, Object> getServiceDetailsById(int serviceId) throws SQLException {
        Connection conn = null;
        Map<String, Object> serviceDetails = null;

        try {
            conn = DBConnection.getConnection();

            String sqlStr = "SELECT service_id, service_name, price, no_of_staff, category_id FROM service WHERE service_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            pstmt.setInt(1, serviceId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                serviceDetails = new HashMap<>();
                serviceDetails.put("serviceId", rs.getInt("service_id"));
                serviceDetails.put("serviceName", rs.getString("service_name"));
                serviceDetails.put("price", rs.getDouble("price"));
                serviceDetails.put("noOfStaff", rs.getInt("no_of_staff"));
                serviceDetails.put("categoryId", rs.getInt("category_id")); 
            }
        } catch (SQLException e) {
            System.out.println("Error in ServiceDAO: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }

        return serviceDetails;
    }

    // Reduce staff count
    public int reduceStaffCountByServiceId(int serviceId) throws SQLException {
        Connection conn = null;
        int rowsUpdated = 0;

        try {
            conn = DBConnection.getConnection();
            String sqlStr = "UPDATE service SET no_of_staff = no_of_staff - 1 WHERE service_id = ? AND no_of_staff > 0";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            pstmt.setInt(1, serviceId);

            rowsUpdated = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in Services: " + e);
        } finally {
            if (conn != null) conn.close();
        }

        return rowsUpdated;
    }

}
