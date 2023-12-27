package Vazifa53;

import java.sql.*;

public class Task4 {
    public static void main(String[] args) {
        createTable();
        insertStudent("John");
        insertStudent("Sarah");
        insertStudent("David");
        insertExamScore(1,"Math",80);
        insertExamScore(2,"Science",90);
        insertExamScore(3,"Math",75);
        insertExamScore(1,"Science",85);
        insertExamScore(2,"Math",95);
        select();
    }
    private static void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS student(student_id SERIAL PRIMARY KEY,student_name VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS examScore(exam_id SERIAL PRIMARY KEY,student_id INT NOT NULL REFERENCES student(student_id) ON DELETE CASCADE,subject VARCHAR NOT NULL,score NUMERIC NOT NULL);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();){
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertStudent(String studentName){
        String query = "INSERT INTO student(student_name) VALUES(?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,studentName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertExamScore(int studentId, String subject,float score){
        String query = "INSERT INTO examScore(student_id, subject, score) VALUES(?,?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setInt(1,studentId);
             preparedStatement.setString(2,subject);
             preparedStatement.setFloat(3,score);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void select(){
        String query = "SELECT e.subject,max(e.score) AS highest_score FROM examScore e LEFT JOIN student s ON s.student_id = e.student_id GROUP BY e.subject;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            System.out.println("----------------------------------------");
            while (resultSet.next()){
                System.out.println(resultSet.getString("subject")+"\t"
                        +resultSet.getFloat("highest_score"));
            }
            System.out.println("----------------------------------------");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
