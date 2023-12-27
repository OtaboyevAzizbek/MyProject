package Vazifa53;

import java.sql.*;

public class Task5 {
    public static void main(String[] args) {
        createTable();
        insertProduct("Laptop","Electronics");
        insertProduct("T-Shirt","Clothing");
        insertProduct("Smartphone","Electronics");
        insertProduct("Watch","Accessories");
        insertProduct("Jeans","Clothing");
        insertSale(1,500);
        insertSale(2,300);
        insertSale(1,200);
        insertSale(4,150);
        insertSale(2,400);
        select();
    }
    private static void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS product2(product_id SERIAL PRIMARY KEY,product_name VARCHAR NOT NULL,category VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS sale2(sale_id SERIAL PRIMARY KEY,product_id INT NOT NULL REFERENCES product2(product_id) ON DELETE CASCADE,sale_amount NUMERIC NOT NULL);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();){
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertProduct(String productName,String category){
        String query = "INSERT INTO product2(product_name, category) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,productName);
            preparedStatement.setString(2,category);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertSale(int productId,float amount){
        String query = "INSERT INTO sale2(product_id, sale_amount) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,productId);
            preparedStatement.setFloat(2,amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void select(){
        String query = "SELECT p.category,sum(s.sale_amount) AS total_revenue FROM product2 p RIGHT JOIN sale2 s on p.product_id = s.product_id GROUP BY p.product_id ORDER BY p.product_id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            System.out.println("----------------------------------------");
            while (resultSet.next()){
                System.out.println(resultSet.getString("category")+"\t"
                        +resultSet.getFloat("total_revenue"));
            }
            System.out.println("----------------------------------------");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
