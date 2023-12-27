package DoExercise;

import Vazifa53.PostgresqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

    }
    private static void myMethod(){
        String query = "";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
