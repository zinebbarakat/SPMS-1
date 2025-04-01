package com.spms.login;
import java.sql.*;


public class Auth {
    static public String role = null;
    static public String loggedInUser = null;


    public boolean validateUser(String email, String password) throws SQLException {
        Connection authConnection = spmsDB.connectToDB();
        Statement statement = authConnection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'");
        if (resultSet.next()) {
            role = resultSet.getString("user_type");
            loggedInUser = resultSet.getString("name");
            spmsDB.terminateConnection(authConnection);
            return true;
        }
        spmsDB.terminateConnection(authConnection);
        return false;
    }

    public void registerUser(String email, String password, String role) throws SQLException {
        Connection authConnection = spmsDB.connectToDB();
        Statement statement = authConnection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE email = '" + email + "'");
        if (resultSet.next()) {
            System.out.println("User already exists!");

        } else {
            statement.executeUpdate("INSERT INTO user (email, password, user_type) VALUES ('" + email + "', '" + password + "', '" + role + "')");
            System.out.println("User registered successfully!");

        }
        spmsDB.terminateConnection(authConnection);
    }
}
