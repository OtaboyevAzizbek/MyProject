package Vazifa48;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Method {
    public static void insert(String name, String type, String meagure_type, Double quantity, Double price) throws SQLException {
        String query = "INSERT INTO foods (name, type, meagure_type, quantity, price) VALUES (?,?,?,?,?)";
        PreparedStatement statement = ConnectionPostgres.connection().prepareStatement(query);

        statement.setString(1, name);
        statement.setString(2, type);
        statement.setString(3, meagure_type);
        statement.setDouble(4, quantity);
        statement.setDouble(5, price);

        statement.executeUpdate();
    }

    public static void update(Integer id, String name, String type, String meagure_type, Double quantity, Double price) throws SQLException {
        String query = "UPDATE foods SET name = ?, type = ?, meagure_type = ?, quantity = ?, price = ? WHERE id = ?";

        PreparedStatement statement = ConnectionPostgres.connection().prepareStatement(query);

        statement.setString(1, name);
        statement.setString(2, type);
        statement.setString(3, meagure_type);
        statement.setDouble(4, quantity);
        statement.setDouble(5, price);
        statement.setInt(6, id);

        statement.executeUpdate();
    }

    public static void select() throws SQLException {
        String query = "SELECT * FROM foods";
        Statement statement = ConnectionPostgres.connection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            System.out.println(
                    resultSet.getString("id") +
                            " " + resultSet.getString("name") +
                            " " + resultSet.getString("type") +
                            " " + resultSet.getString("meagure_type") +
                            " " + resultSet.getString("quantity") +
                            " " + resultSet.getString("price")
            );
        }
    }

    public static void create(){
        String query = "CREATE TABLE IF NOT EXITS foods (\n" +
                "id SERIAL PRIMARY KEY,\n" +
                "name VARCHAR(50) NOT NULL,\n" +
                "type VARCHAR(30) NOT NULL,\n" +
                "meagure_type VARCHAR(30) NOT NULL,\n" +
                "quantity NUMERIC(10,2) NOT NULL,\n" +
                "price NUMERIC(10,2) NOT NULL\n" +
                ");\n";
    }

    public static void delete(Integer id) throws SQLException {
        String query = "DELETE FROM foods WHERE id = " + id;
        Statement statement = ConnectionPostgres.connection().createStatement();
        statement.execute(query);
    }

    public static void main(String[] args) throws SQLException {
        insert("Telefon", "electronics", "pieces", 50d, 5000000d);
        update(1,"Computer", "electronics", "pieces", 200d, 7500000d);
        delete(1);
        select();
    }
}
