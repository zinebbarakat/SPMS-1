package com.spms.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTestConnection {

    public static void main(String[] args) {
        String url = "jdbc:mariadb://195.235.211.197:3306/pii2_NewLifeSystems";
        String user = "db_HasALA";
        String password = "12312344@";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("✅ Connected to MariaDB successfully!");
            } else {
                System.out.println("❌ Failed to connect to MariaDB.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }
}
