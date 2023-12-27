package Vazifa50;

import java.sql.*;

public class MyMethod {
    public static void insertUser(String user,String login, String password,int role) {
        String sql = "INSERT INTO user_table(user_name, user_login, user_password, user_role) VALUES (?,?,?,?);";
        try (Connection connection = PostgresConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,user);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4,role);
            preparedStatement.executeUpdate();
            System.out.println("Ro'yxatdan muvaffaqiyatli o'tdingiz!");
            Controller.menu();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertVacancy(String company_name,String requirement, float salary,String address,String contact_us,String position) {
        String sql = "INSERT INTO vacancy(company_name, requirement, salary, address, contact_us, position) VALUES(?,?,?,?,?,?);";
        try (Connection connection = PostgresConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,company_name);
            preparedStatement.setString(2, requirement);
            preparedStatement.setFloat(3, salary);
            preparedStatement.setString(4,address);
            preparedStatement.setString(5,contact_us);
            preparedStatement.setString(6,position);
            preparedStatement.executeUpdate();
            System.out.println("Yangi vakansiya muvaffaqiyatli yaratildi!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void select(){
        String query = "SELECT * FROM vacancy";
        try (Connection connection = PostgresConnection.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)){
            while (resultSet.next()){
                System.out.println(
                        resultSet.getString("id") +
                                " " + resultSet.getString("company_name") +
                                " " + resultSet.getString("requirement") +
                                " " + resultSet.getFloat("salary") +
                                " " + resultSet.getString("address") +
                                " " + resultSet.getString("contact_us") +
                                " " + resultSet.getString("position")
                );
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int checkLogin(String login) {
        String sql = "SELECT count(*) FROM user_table WHERE user_login=?;";
        try (Connection connection = PostgresConnection.connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
    public static int checkUser(String login,String password) {
        String sql = "SELECT * FROM user_table WHERE user_login=? AND user_password=?;";
        try (Connection connection = PostgresConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt("user_role");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
