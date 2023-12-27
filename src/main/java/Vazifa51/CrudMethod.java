package Vazifa51;

import java.sql.*;
public class CrudMethod {
    public static void createDatabaseAndTable() {
        String sql = "CREATE TABLE IF NOT EXISTS level(id SERIAL PRIMARY KEY,name VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS group1(id SERIAL PRIMARY KEY,name VARCHAR NOT NULL,start_time TIME NOT NULL,level_id INT NOT NULL REFERENCES level(id) ON DELETE CASCADE,price NUMERIC NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS student(id SERIAL PRIMARY KEY,fullname VARCHAR NOT NULL,telnumber VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS student_group(id SERIAL PRIMARY KEY,student_id INT UNIQUE NOT NULL REFERENCES student(id) ON DELETE CASCADE,group_id INT UNIQUE NOT NULL REFERENCES group1(id) ON DELETE CASCADE);";
        try (Connection connection = PostgresConnection.connection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertLevel(){
        String sql = "INSERT INTO level(name) VALUES(?);" +
                "INSERT INTO level(name) VALUES(?);" +
                "INSERT INTO level(name) VALUES(?);";
        try (Connection connection = PostgresConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
             preparedStatement.setString(1,Level.BOSHLANGICH.name());
             preparedStatement.setString(2,Level.ORTA.name());
             preparedStatement.setString(3,Level.YUQORI.name());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertGroup(String groupName, Time startTime,String level,float price){
        String sql = "INSERT INTO group1(name, start_time, level_id, price) VALUES(?,?,(SELECT id FROM level WHERE name=?),?);";
        try (Connection connection = PostgresConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
             preparedStatement.setString(1,groupName);
             preparedStatement.setTime(2,startTime);
             preparedStatement.setString(3,level);
             preparedStatement.setFloat(4,price);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertStudent(String fullName,String telNumber){
        String sql = "INSERT INTO student(fullname, telnumber) VALUES(?,?);";
        try (Connection connection = PostgresConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
             preparedStatement.setString(1,fullName);
             preparedStatement.setString(2,telNumber);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertStudentGroup(int studentId,int groupId){
        String sql = "INSERT INTO student_group(student_id, group_id) VALUES(?,?);";
        try (Connection connection = PostgresConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,groupId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
