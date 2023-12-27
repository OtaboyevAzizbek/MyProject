package Vazifa49;

import java.sql.*;

public class PostgresMethod {
    public static void main(String[] args) throws InterruptedException {
        insert("azizbek7735","123");  //begin_time=current_timestamp  end_time=current_timestamp+interval '1 minute'
        Thread.sleep(30000);
        checkUser(14);
        Thread.sleep(31000);
        checkUser(14);
    }
    public static void insert(String login, String password){
        String sql = "INSERT INTO book(login, password) VALUES (?,?);";
        try (Connection connection = ConnectionSQL.connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.executeUpdate();
            System.out.println("Saytimizga muvaffaqiyatli obuna bo'ldingiz!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void checkUser(int id){
        String sql = "SELECT extract(EPOCH FROM end_time-current_timestamp) AS duration FROM book WHERE id=?";
        try (Connection connection = ConnectionSQL.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getDouble("duration")<=0){
                    System.out.println("Obuna muddatingiz tugagan!");
                }else {
                    System.out.println("Siz saytimiz obunachisisiz!");
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
