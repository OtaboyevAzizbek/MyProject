package Vazifa53;

import java.sql.*;

public class Task1 {
    public static void main(String[] args) {
        createTable();
        insertCustomer("John","North");
        insertCustomer("Sarah","South");
        insertCustomer("David","East");
        insertOrder(1,100);
        insertOrder(2,150);
        insertOrder(1,200);
        insertOrder(3,75);
        insertOrder(2,120);
        select();
    }
    private static void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS customer(customer_id SERIAL PRIMARY KEY,customer_name VARCHAR NOT NULL,region VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS order1(order_id SERIAL PRIMARY KEY,customer_id INT NOT NULL REFERENCES customer(customer_id) ON DELETE CASCADE,order_amount NUMERIC NOT NULL);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();){
             statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertCustomer(String customerName,String region){
        String query = "INSERT INTO customer(customer_name, region) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setString(1,customerName);
             preparedStatement.setString(2,region);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertOrder(int customerId,float amount){
        String query = "INSERT INTO order1(customer_id, order_amount) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,customerId);
            preparedStatement.setFloat(2,amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void select(){
        String query = "SELECT c.customer_id,customer_name,sum(o1.order_amount) AS amount FROM customer c LEFT JOIN order1 o1 ON o1.customer_id = c.customer_id WHERE region='North' GROUP BY c.customer_id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            System.out.println("----------------------------------------");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("customer_id")+"\t"
                +resultSet.getString("customer_name")+"\t"
                +resultSet.getFloat("amount"));
            }
            System.out.println("----------------------------------------");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
