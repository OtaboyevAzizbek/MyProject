package Vazifa51;

import java.sql.Time;

import static Vazifa51.Level.*;

public class Main {
    public static void main(String[] args) {
        CrudMethod.createDatabaseAndTable();
        CrudMethod.insertLevel();
        CrudMethod.insertGroup("Java Bootcamp Result", Time.valueOf("16:30:00"),YUQORI.name(),2300000f);
        CrudMethod.insertGroup("Flutter Bootcamp Result", Time.valueOf("17:00:00"),BOSHLANGICH.name(),2000000f);
        CrudMethod.insertGroup("Go Bootcamp Result", Time.valueOf("14:30:00"),ORTA.name(),2500000f);
        CrudMethod.insertStudent("Azizbek","+998907377735");
        CrudMethod.insertStudent("Alisher","+998937460936");
        CrudMethod.insertStudent("Azamat","+998957489302");
        CrudMethod.insertStudentGroup(1,1);
        CrudMethod.insertStudentGroup(2,3);
        CrudMethod.insertStudentGroup(3,2);
    }
}
