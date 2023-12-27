package Dars49;

import java.sql.*;

public class CRUDMethod {
    public static void main(String[] args) {
        create(Timestamp.valueOf("2023-12-04 18:00:00"),Timestamp.valueOf("2023-12-04 19:12:00"));
        read();
        update(2,Timestamp.valueOf("2023-12-04 18:30:00"),Timestamp.valueOf("2023-12-04 19:47:00"));
        delete(2);
    }
    public static void create(Timestamp begin,Timestamp end){
        String sql = "INSERT INTO parking(start_time, end_time) VALUES(?,?);";
        try (Connection connection = ConnectionSQL.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setTimestamp(1,begin);
            preparedStatement.setTimestamp(2,end);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void read(){
        String sql = "SELECT * FROM parking;";
        try (Connection connection = ConnectionSQL.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                System.out.println(resultSet.getString("id")+"\t"
                        +resultSet.getString("start_time")+"\t"
                +resultSet.getString("end_time"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void update(int id,Timestamp begin,Timestamp end){
        String sql = "UPDATE parking SET start_time=?,end_time=? WHERE id=?;";
        try (Connection connection = ConnectionSQL.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setTimestamp(1,begin);
            preparedStatement.setTimestamp(2,end);
            preparedStatement.setInt(3,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delete(int id){
        String sql = "DELETE FROM parking WHERE id=?;";
        try (Connection connection = ConnectionSQL.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
