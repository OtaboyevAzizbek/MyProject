package Vazifa53;

import java.sql.*;

public class Task3 {
    public static void main(String[] args) {
        createTable();
        insertCustomer("John","john@example.com");
        insertCustomer("Sarah","sarah@example.com");
        insertCustomer("David","david@example.com");
        insertOrder(1,Date.valueOf("2023-01-01"));
        insertOrder(2,Date.valueOf("2023-01-02"));
        insertOrder(1,Date.valueOf("2023-01-02"));
        insertOrder(3,Date.valueOf("2023-01-03"));
        insertOrder(2,Date.valueOf("2023-01-03"));
        select();
    }
    private static void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS customer2(customer_id SERIAL PRIMARY KEY,customer_name VARCHAR NOT NULL,email VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS order2(order_id SERIAL PRIMARY KEY,customer_id INT NOT NULL REFERENCES customer2(customer_id) ON DELETE CASCADE,order_date DATE NOT NULL);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();){
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertCustomer(String customerName,String email){
        String query = "INSERT INTO customer2(customer_name, email) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setString(1,customerName);
             preparedStatement.setString(2,email);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertOrder(int customerId,Date orderDate){
        String query = "INSERT INTO order2(customer_id, order_date) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,customerId);
            preparedStatement.setDate(2,orderDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void select(){
        String query = "SELECT c.customer_id,c.customer_name,c.email,count(*) AS order_count FROM customer2 c LEFT JOIN order2 o on c.customer_id = o.customer_id GROUP BY c.customer_id ORDER BY c.customer_id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            System.out.println("----------------------------------------");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("customer_id")+"\t"
                        +resultSet.getString("customer_name")+"\t"
                        +resultSet.getString("email")+"\t"
                        +resultSet.getInt("order_count"));
            }
            System.out.println("----------------------------------------");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
