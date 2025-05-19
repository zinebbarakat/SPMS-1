package com.spms.login;

import com.spms.session.Session;
import java.sql.*;

public class Auth {
    static public String role = null;
    static public String loggedInUser = null;

    public boolean validateUser(String email, String password) throws SQLException {
        Connection authConnection = spmsDB.connectToDB();

        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        PreparedStatement preparedStatement = authConnection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // ✅ FIXED: use the correct column name from your DB
            int userId = resultSet.getInt("user_ID");
            String name = resultSet.getString("name");
            String userType = resultSet.getString("user_type");

            // Optional legacy static values (still work if used elsewhere)
            role = userType;
            loggedInUser = name;

            // ✅ Store in session for global access
            User user = new User();
            user.setId(userId);
            user.setEmail(email);
            user.setName(name);
            user.setRole(userType);

            Session.setCurrentUser(user);

            spmsDB.terminateConnection(authConnection);
            return true;
        }

        spmsDB.terminateConnection(authConnection);
        return false;
    }

    public void registerUser(String email, String password, String role, String name) throws SQLException {
        Connection authConnection = spmsDB.connectToDB();

        String checkQuery = "SELECT * FROM user WHERE email = ?";
        PreparedStatement checkStmt = authConnection.prepareStatement(checkQuery);
        checkStmt.setString(1, email);
        ResultSet resultSet = checkStmt.executeQuery();

        if (resultSet.next()) {
            System.out.println("User already exists!");
        } else {
            String insertQuery = "INSERT INTO user (email, password, user_type, name) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = authConnection.prepareStatement(insertQuery);
            insertStmt.setString(1, email);
            insertStmt.setString(2, password);
            insertStmt.setString(3, role);
            insertStmt.setString(4, name);
            insertStmt.executeUpdate();

            System.out.println("User registered successfully!");
        }

        spmsDB.terminateConnection(authConnection);
    }
}
