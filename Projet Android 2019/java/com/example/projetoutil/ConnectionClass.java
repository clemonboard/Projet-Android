package com.example.projetoutil;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String dbURL = "jdbc:microsoft:sqlserver://INFO-324-1A-103\\SQLEXPRESS;databaseName=ProjetProg";
        String user = "sa";
        String pass = "pass";

        Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

        try
        {
            Connection myConn = DriverManager.getConnection(dbURL);
            Statement myStmt = myConn.createStatement();
        }
        catch (SQLException e)
        {

        }
    }



}


