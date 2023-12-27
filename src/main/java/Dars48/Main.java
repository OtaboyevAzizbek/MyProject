package Dars48;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        create("Azizbek","Otaboyev",5000000f,3);
        read();
        update(1,"Jumaniyoz","Otaboyev",10000000f,7);
        delete(5);
        read();
    }

    public static void create(String first_name,String last_name,float salary,int year_of_experiance){
        String sql = "INSERT INTO xodim(first_name, last_name, salary, year_of_experiance) VALUES (?,?,?,?);";
        try (Connection connection = ConnectionMethod.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.setString(1,first_name);
            preparedStatement.setString(2,last_name);
            preparedStatement.setFloat(3,salary);
            preparedStatement.setInt(4,year_of_experiance);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void read(){
        String sql = "SELECT * FROM xodim";
        try (Connection connection = ConnectionMethod.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                System.out.println(resultSet.getString("id")+"\t"
                +resultSet.getString("first_name")+"\t"
                        +resultSet.getString("last_name")+"\t"
                        +resultSet.getString("salary")+"\t"
                        +resultSet.getString("year_of_experiance"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void update(int id,String first_name,String last_name,float salary,int year_of_experiance){
        String sql = "UPDATE xodim SET first_name=?,last_name=?,salary=?,year_of_experiance=? WHERE id=?;";
        try (Connection connection = ConnectionMethod.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.setString(1,first_name);
            preparedStatement.setString(2,last_name);
            preparedStatement.setFloat(3,salary);
            preparedStatement.setInt(4,year_of_experiance);
            preparedStatement.setInt(5,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delete(int id){
        String sql = "DELETE FROM xodim WHERE id=?";
        try (Connection connection = ConnectionMethod.connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
