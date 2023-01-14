package com.pengaduan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "management_rw";
        String databaseUser = "root";
        String databasePassword = "";

        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
           System.out.println("Database Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return databaseLink;

    };
}
