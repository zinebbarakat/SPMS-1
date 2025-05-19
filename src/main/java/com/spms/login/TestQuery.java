package com.spms.login;

public class TestQuery {
    public static void main(String[] args) {
        float temperature = DatabaseHelper.getLatestTemperatureForUser(11); // Use your test user ID
        if (temperature != -1) {
            System.out.println("🌡️ Latest temperature: " + temperature + "°C");
        } else {
            System.out.println("❌ Could not fetch temperature.");
        }
    }
}
