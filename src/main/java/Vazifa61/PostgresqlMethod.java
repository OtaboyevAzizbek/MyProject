package Vazifa61;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresqlMethod {
    public static void main(String[] args) {

    }
    private static void createTables(){
        String query = "CREATE TABLE permission(per_id SERIAL PRIMARY KEY,per_name VARCHAR NOT NULL UNIQUE);" +
                "CREATE TABLE role(role_id SERIAL PRIMARY KEY,role_name VARCHAR NOT NULL UNIQUE,per_id INT[] NOT NULL);" +
                "CREATE TABLE user_table(user_id SERIAL PRIMARY KEY,user_login VARCHAR NOT NULL UNIQUE,user_password VARCHAR NOT NULL,user_role INT NOT NULL REFERENCES role(role_id) ON DELETE CASCADE,registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);" +
                "INSERT INTO permission(per_name) VALUES('User yaratish'),('User o''chirish'),('Test tuzish'),('Test o''chirish'),('Test ishlash'),('Test natijalarini ko''rish'),('Role yaratish'),('Role o''chirish');" +
                "INSERT INTO role(role_name, per_id) VALUES('Admin',array[1,2,3,4,5,6,7,8]);" +
                "INSERT INTO user_table(user_login, user_password, user_role) VALUES('Admin','12345',1);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement()) {
             statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
