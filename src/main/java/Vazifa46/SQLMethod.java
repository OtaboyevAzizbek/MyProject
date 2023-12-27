package Vazifa46;

import org.example.SQLConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLMethod {
    public static void main(String[] args) {
        insert(1,"Jahon yangiliklari","Jahonning so'nggi yangiliklari haqida...","Azizbek");
        read();
        update(1,"O'zbekistonda hafta ichidagi yangiliklar","Yangiliklar haqida obunachilarga batafsil ma'lumot berib boramiz","Alisher");
        delete(1);
    }
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
    static LocalDateTime now = LocalDateTime.now();

    public static void insert(int id1,String title1,String content1,String author1){
        String sql = "INSERT INTO yangilik(id,title,content,author,date) VALUES(?,?,?,?,?)";
        try (Connection connection = ConnectionPostgres.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id1);
            preparedStatement.setString(2,title1);
            preparedStatement.setString(3,content1);
            preparedStatement.setString(4,author1);
            preparedStatement.setString(5, dateTimeFormatter.format(now));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void update(int id1,String title1,String content1,String author1) {
        String sql = "UPDATE yangilik SET title=?,content=?,author=?,date=? WHERE id=?";
        try (Connection connection = SQLConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1,title1);
            preparedStatement.setString(2,content1);
            preparedStatement.setString(3,author1);
            preparedStatement.setString(4,dateTimeFormatter.format(now));
            preparedStatement.setInt(5,id1);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delete(int id1) {
        String sql = "DELETE FROM yangilik WHERE id=?";
        try (Connection connection = SQLConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1,id1);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void read() {
        String sql = "SELECT * FROM yangilik ORDER BY id ASC;";
        try (Connection connection = SQLConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()){
                System.out.println(resultSet.getString("id")+"\t"
                        +resultSet.getString("title")+"\t"
                        +resultSet.getString("content")+"\t"
                        +resultSet.getString("author")+"\t"
                        +resultSet.getString("date"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
