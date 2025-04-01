package com.spms.login;

import java.sql.*;

public class spmsDB {

    private static String DBlink = "jdbc:mysql://195.235.211.197:3306/pii2_NewLifeSystems";
    private static String DBpassword = "secure_password";
    private static String DBuser = "pii2_NewLifeSystems";

    public static Connection connectToDB() throws SQLException {
        System.out.println("Connecting to the database...");
        Connection connection = DriverManager.getConnection(DBlink,
                DBuser, DBpassword);
        System.out.println("Connected to the database!");

        return connection;

    }

    public static void terminateConnection(Connection connection) throws SQLException {
        System.out.println("Terminating the connection...");
        connection.close();
        System.out.println("Connection terminated!");
    }




}
