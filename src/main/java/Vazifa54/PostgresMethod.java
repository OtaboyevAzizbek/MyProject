package Vazifa54;

import java.sql.*;
import java.util.HashMap;

public class PostgresMethod {
    public static void main(String[] args) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Diagonali displey:","6,74 dyum 1600x720,260ppi");
        hashMap.put("Protsessor:","MediaTek Helio G85");
        hashMap.put("Yangilanish tezligi","90 HZ gacha");
        hashMap.put("Ekran","Corning Gorilla Glass");

        HashMap<String,String> hashMap1 = new HashMap<>();
        hashMap1.put("Balandligi","168mm");
        hashMap1.put("Kengligi","78mm");

        HashMap<String,String> hashMap2 = new HashMap<>();
        hashMap.put("Diagonali displey:","7,8 dyum 1800x820");
        hashMap.put("Protsessor:","MediaTek H850");

        HashMap<String,String> hashMap3 = new HashMap<>();
        hashMap1.put("Balandligi","180mm");
        hashMap1.put("Kengligi","87mm");

        createTable();
        createCategory("Elektronika");
        createProduct(hashMap,hashMap1,1);
        read();
        updateCategory("Maishiy texnika",1);
        updateProduct(hashMap2,hashMap3,1,1);
        delete(1);
    }
    public static void createTable(){
        String query = "CREATE EXTENSION hstore;" +
                "CREATE TABLE IF NOT EXISTS category(id SERIAL PRIMARY KEY,category_name VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS product1(id SERIAL PRIMARY KEY,mahsulot_tavsifi hstore NOT NULL,olcham hstore NOT NULL,product_category INT NOT NULL REFERENCES category(id) ON DELETE CASCADE);";
        try (Connection connection = PostgresqlConnect.connection();
             Statement statement = connection.createStatement()){
             statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createCategory(String categoryName){
        String query = "INSERT INTO category(category_name) VALUES(?);";
        try (Connection connection = PostgresqlConnect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setString(1,categoryName);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createProduct(HashMap<String,String> tavsif,HashMap<String,String> olcham,int categoryId){
        String query = "INSERT INTO product1(mahsulot_tavsifi, olcham, product_category) VALUES(?,?,?);";
        try (Connection connection = PostgresqlConnect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setObject(1,tavsif);
             preparedStatement.setObject(2,olcham);
             preparedStatement.setInt(3,categoryId);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void read(){
        String query = "SELECT p.id,p.mahsulot_tavsifi,p.olcham,c.category_name FROM product1 p LEFT JOIN category c ON c.id = p.product_category;";
        try (Connection connection = PostgresqlConnect.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
             while (resultSet.next()){
                 System.out.println(resultSet.getInt("id")+"\t"
                         +resultSet.getObject("mahsulot_tavsifi")+"\t"
                         +resultSet.getObject("olcham")+"\t"
                         +resultSet.getString("category_name")
                 );
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateCategory(String categoryName,int id){
        String query = "UPDATE category SET category_name=? WHERE id=?;";
        try (Connection connection = PostgresqlConnect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setString(1,categoryName);
             preparedStatement.setInt(2,id);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateProduct(HashMap<String,String> tavsif,HashMap<String,String> olcham,int categoryId,int productId){
        String query = "UPDATE product1 SET mahsulot_tavsifi=?,olcham=?,product_category=? WHERE id=?;";
        try (Connection connection = PostgresqlConnect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setObject(1,tavsif);
            preparedStatement.setObject(2,olcham);
            preparedStatement.setInt(3,categoryId);
            preparedStatement.setInt(4,productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delete(int productId){
        String query = "DELETE FROM product1 WHERE id=?;";
        try (Connection connection = PostgresqlConnect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setInt(1,productId);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
