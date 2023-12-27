package Vazifa50;

import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
    private static final String url = "jdbc:postgresql://localhost/jobhunter";
    private static final String user = "postgres";
    private static final String password = "123";

    public static java.sql.Connection connection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
