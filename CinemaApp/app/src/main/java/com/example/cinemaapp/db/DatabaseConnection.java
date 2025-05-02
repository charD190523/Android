package com.example.cinemaapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://your-server-ip:3306/your-database-name";
    private static final String USER = "your-username";
    private static final String PASSWORD = "your-password";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Set timeout cho kết nối
            Properties props = new Properties();
            props.setProperty("user", USER);
            props.setProperty("password", PASSWORD);
            props.setProperty("connectTimeout", "5000"); // 5 giây timeout connect
            props.setProperty("socketTimeout", "5000"); // 5 giây timeout đọc dữ liệu

            conn = DriverManager.getConnection(URL, props);
            System.out.println("✅ Kết nối DB thành công!"); // log thẳng luôn
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Exception: " + e.getMessage());
        }
        return conn;
    }
}
