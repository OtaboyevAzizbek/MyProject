package Dars54;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MyMethod {
    public static void main(String[] args) {
        createTable();
    }
    public static void createTable(){
        String query = "CREATE TABLE student(id SERIAL PRIMARY KEY,name VARCHAR NOT NULL,tasks VARCHAR[] NOT NULL);";
        try (Connection connection = PostgresqlConnect.connection();
             Statement statement = connection.createStatement()){
             statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insert(String name,String[] tasks){
        String query = "";
        try (Connection connection = PostgresqlConnect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setString(1,name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
