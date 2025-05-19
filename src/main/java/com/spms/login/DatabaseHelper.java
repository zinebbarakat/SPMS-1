package com.spms.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String URL = "jdbc:mariadb://195.235.211.197:3306/pii2_NewLifeSystems";
    private static final String USER = "db_HasALA";
    private static final String PASSWORD = "12312344@";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static float getLatestTemperatureForUser(int userId) {
        String sql = """
            SELECT m.measurementValue
            FROM measurement m
            JOIN sensor s ON m.sensor_ID = s.sensor_ID
            WHERE s.sensorType = 'Temperature' AND m.user_ID = ?
            ORDER BY m.measurement_ID DESC
            LIMIT 1
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("measurementValue");
            }
        } catch (SQLException e) {
            System.err.println("⚠ DB error (temperature): " + e.getMessage());
        }

        return -1;
    }

    public static float getLatestLightForUser(int userId) {
        String sql = """
            SELECT m.measurementValue
            FROM measurement m
            JOIN sensor s ON m.sensor_ID = s.sensor_ID
            WHERE s.sensorType = 'Light' AND m.user_ID = ?
            ORDER BY m.measurement_ID DESC
            LIMIT 1
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("measurementValue");
            }
        } catch (SQLException e) {
            System.err.println("⚠ DB error (light): " + e.getMessage());
        }

        return -1;
    }

    public static float getLatestMoistureForUser(int userId) {
        String query = """
            SELECT m.measurementValue
            FROM measurement m
            JOIN sensor s ON m.sensor_ID = s.sensor_ID
            WHERE s.sensorType = 'Humidity' AND m.user_ID = ?
            ORDER BY m.measurement_ID DESC
            LIMIT 1
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("measurementValue");
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error fetching soil data: " + e.getMessage());
        }

        return -1;
    }
}
