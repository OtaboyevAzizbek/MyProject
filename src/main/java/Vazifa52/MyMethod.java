package Vazifa52;

import java.sql.*;

public class MyMethod {
    public static void createDatabaseAndTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product(id SERIAL PRIMARY KEY,product_name VARCHAR NOT NULL,product_description VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS client(id SERIAL PRIMARY KEY,fullname VARCHAR NOT NULL,tel_number VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS orders(id SERIAL PRIMARY KEY,client_id INT NOT NULL REFERENCES client(id) ON DELETE CASCADE,product_id INT NOT NULL REFERENCES product(id) ON DELETE CASCADE);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertProduct(String productName,String description){
        String sql = "INSERT INTO product(product_name, product_description) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2,description);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertClient(String fullName,String telNumber){
        String sql = "INSERT INTO client(fullname, tel_number) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2,telNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertOrder(int clientId,int productId){
        String sql = "INSERT INTO orders(client_id, product_id) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2,productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void selectWithLeftJoin(){
        String sql = "SELECT fullname,tel_number,product_name,product_description FROM orders LEFT JOIN client c on c.id = orders.client_id LEFT JOIN product p on orders.product_id = p.id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
             while (resultSet.next()){
                 System.out.println(resultSet.getString("fullname")+"\t"
                 +resultSet.getString("tel_number")+"\t"
                 +resultSet.getString("product_name")+"\t"
                 +resultSet.getString("product_description"));
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void selectWithRightJoin(){
        String sql = "SELECT product_name,product_description,fullname,tel_number FROM orders RIGHT JOIN client c on c.id = orders.client_id RIGHT JOIN product p on orders.product_id = p.id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                System.out.println(resultSet.getString("product_name")+"\t"
                        +resultSet.getString("product_description")+"\t"
                        +resultSet.getString("fullname")+"\t"
                        +resultSet.getString("tel_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void selectWithFullJoin(){
        String sql = "SELECT product_name,product_description,fullname,tel_number FROM orders FULL JOIN client c on c.id = orders.client_id FULL JOIN product p on orders.product_id = p.id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                System.out.println(resultSet.getString("fullname")+"\t"
                        +resultSet.getString("tel_number")+"\t"
                        +resultSet.getString("product_name")+"\t"
                        +resultSet.getString("product_description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
