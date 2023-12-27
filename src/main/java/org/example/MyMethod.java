package org.example;

import java.sql.*;

public class MyMethod {
    public static void main(String[] args) {
        insertXodim();
        showBookList();
    }

    public static void insertXodim() {
        String sql = "INSERT INTO xodim(id,ismi,yoshi,email,tel) VALUES (?,?,?,?,?)";
        try (Connection connection = SQLConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1,3);
            preparedStatement.setString(2,"Azizbek");
            preparedStatement.setInt(3,20);
            preparedStatement.setString(4,"azizbek7735@gmail.com");
            preparedStatement.setString(5,"+998907377735");
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateXodim() {
        String sql = "UPDATE xodim SET ismi=?,yoshi=?,email=?,tel=? WHERE id=?";
        try (Connection connection = SQLConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1,"Alisher");
            preparedStatement.setInt(2,20);
            preparedStatement.setString(3,"alisher@gmail.com");
            preparedStatement.setString(4,"+998934672819");
            preparedStatement.setInt(5,1);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteXodim() {
        String sql = "DELETE FROM xodim";
        try (Connection connection = SQLConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showBookList() {
        String sql = "SELECT * FROM xodim ORDER BY id ASC;";
        try (Connection connection = SQLConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()){
                System.out.println(resultSet.getString("id")+"\t"
                +resultSet.getString("ismi")+"\t"
                +resultSet.getString("yoshi")+"\t"
                +resultSet.getString("email")+"\t"
                +resultSet.getString("tel"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
