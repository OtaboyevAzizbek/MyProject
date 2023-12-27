package Vazifa47;

import java.sql.*;

public class Product {
    public static void main(String[] args) {
        getProductList();
    }
    public static void getProductList(){
        String sql = "SELECT nomi ||' '||turi ||' '||olchov_birlik AS product,miqdori,birlik_narxi FROM product ORDER BY birlik_narxi DESC;";
        try (Connection connection = ConnectionSQL.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("product")+"\t"+
                        resultSet.getString("miqdori")+"\t"+
                        resultSet.getString("birlik_narxi"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
