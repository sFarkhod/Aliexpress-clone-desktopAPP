package com.example.aliexpress_clone;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "aliexpress-clone";
        String databaseUser = "root";
        String databasePassword = "HDp)3;Tkq~@X:.v@";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
