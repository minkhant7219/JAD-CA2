package com.example.dbaccess;

import java.sql.*;

public class PaymentDAO {

    public Integer createPayment(Integer customerId, Double totalAmount, String transactionId) throws SQLException {
        Connection conn = null;
        Integer paymentId = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO payment (customer_id, total_amount, transaction_id) VALUES (?, ?, ?) RETURNING payment_id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setDouble(2, totalAmount);
            pstmt.setString(3, transactionId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                paymentId = rs.getInt("payment_id");
            }
        } finally {
            if (conn != null) conn.close();
        }
        return paymentId;
    }
}
