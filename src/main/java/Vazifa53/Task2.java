package Vazifa53;

import java.sql.*;

public class Task2 {
    public static void main(String[] args) {
        createTable();
        insertDepartment("Sales");
        insertDepartment("Marketing");
        insertEmployee("John",1,5000);
        insertEmployee("Sarah",2,6000);
        insertEmployee("David",1,4500);
        insertEmployee("Emma",2,5500);
        insertEmployee("Michael",1,4000);
        select();

    }
    private static void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS department(department_id SERIAL PRIMARY KEY,department_name VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS employee(employee_id SERIAL PRIMARY KEY,employee_name VARCHAR NOT NULL,department_id INT NOT NULL REFERENCES department(department_id) ON DELETE CASCADE,salary NUMERIC NOT NULL);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();){
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertDepartment(String departmentName){
        String query = "INSERT INTO department(department_name) VALUES(?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,departmentName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertEmployee(String employeeName,int departmentId,float salary){
        String query = "INSERT INTO employee(employee_name, department_id, salary) VALUES(?,?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,employeeName);
            preparedStatement.setInt(2,departmentId);
            preparedStatement.setFloat(3,salary);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void select(){
        String query = "SELECT d.department_id,d.department_name,avg(salary) AS average_salary FROM department d LEFT JOIN employee e ON d.department_id = e.department_id GROUP BY d.department_id ORDER BY d.department_id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            System.out.println("----------------------------------------");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("department_id")+"\t"
                        +resultSet.getString("department_name")+"\t"
                        +resultSet.getFloat("average_salary"));
            }
            System.out.println("----------------------------------------");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
