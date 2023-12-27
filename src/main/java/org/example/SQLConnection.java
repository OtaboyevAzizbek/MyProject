package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String url = "jdbc:postgresql://localhost/kutubxona";
    private static final String user = "postgres";
    private static final String password = "123";

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
