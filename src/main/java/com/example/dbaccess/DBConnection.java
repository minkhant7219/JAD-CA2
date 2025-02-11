package com.example.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        String dbUrl = "jdbc:postgresql://ep-patient-sun-a10kaazb.ap-southeast-1.aws.neon.tech:5432/cleaning_service?ssl=true&sslmode=require&serverTimezone=Asia/Singapore";
        String dbUser = "neondb_owner";
        String dbPassword = "VMbFSjq8xm6N";
        String dbClass = "org.postgresql.Driver";
        
        Connection connection = null;
        try {
            Class.forName(dbClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
