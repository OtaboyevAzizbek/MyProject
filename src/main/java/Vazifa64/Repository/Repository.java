package Vazifa64.Repository;

import Vazifa64.ConnectionClass.PostgresqlCon;
import Vazifa64.Entity.Company;
import Vazifa64.Entity.Condidate;
import Vazifa64.Entity.User;
import Vazifa64.Entity.Vacancy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Repository {
    private static Scanner scanner = new Scanner(System.in);
    public static void createTables(){
        String query = "CREATE TYPE enum_type AS ENUM('COMPANY','CANDIDATE');" +
                "CREATE TABLE IF NOT EXISTS users_table(id SERIAL PRIMARY KEY,username VARCHAR NOT NULL UNIQUE,password VARCHAR NOT NULL CHECK (length(password)>2),phone VARCHAR NOT NULL CHECK (length(phone)=13),email VARCHAR NOT NULL,role enum_type NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS company_description(id SERIAL PRIMARY KEY,company_name VARCHAR NOT NULL CHECK (length(company_name)>3),company_id INT NOT NULL REFERENCES users_table(id) ON DELETE CASCADE,information TEXT NOT NULL,web_sayt_link VARCHAR NOT NULL,main_office_location VARCHAR NOT NULL,number_of_worker INT NOT NULL CHECK (number_of_worker>0),owner VARCHAR NOT NULL CHECK (length(owner)>2));" +
                "CREATE TABLE IF NOT EXISTS candidate_resume(id SERIAL PRIMARY KEY,candidate_id INT NOT NULL REFERENCES users_table(id) ON DELETE CASCADE,first_name VARCHAR NOT NULL,last_name VARCHAR NOT NULL,information TEXT NOT NULL,linkedin_link VARCHAR NOT NULL,address VARCHAR NOT NULL,age INT NOT NULL CHECK (age>0));" +
                "CREATE TABLE IF NOT EXISTS candidate_skills(id SERIAL PRIMARY KEY,candidate_resume_id INT NOT NULL REFERENCES candidate_resume(id) ON DELETE CASCADE,name VARCHAR NOT NULL,level INT NOT NULL CHECK (level>0 AND level<6));" +
                "CREATE TABLE IF NOT EXISTS candidate_education(id SERIAL PRIMARY KEY,candidate_resume_id INT NOT NULL REFERENCES candidate_resume(id) ON DELETE CASCADE,school_name VARCHAR NOT NULL,from_date DATE NOT NULL,to_date DATE NOT NULL,about_courses VARCHAR NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS candidate_job_history(id SERIAL PRIMARY KEY,candidate_resume_id INT NOT NULL REFERENCES candidate_resume(id) ON DELETE CASCADE,job_title VARCHAR NOT NULL,campany_name VARCHAR NOT NULL,from_date DATE NOT NULL,to_date DATE NOT NULL,information_about_job TEXT NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS vacancy(id SERIAL PRIMARY KEY,company_id INT NOT NULL REFERENCES company_description(id) ON DELETE CASCADE,title VARCHAR NOT NULL,needed_tech VARCHAR[] NOT NULL,location VARCHAR NOT NULL,work_time VARCHAR NOT NULL,maosh FLOAT NOT NULL CHECK (maosh>0),is_active BOOLEAN NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS vacancy_candidate(id SERIAL PRIMARY KEY,vacancy_id INT NOT NULL REFERENCES vacancy(id) ON DELETE CASCADE,candidate_id INT NOT NULL REFERENCES users_table(id) ON DELETE CASCADE,date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,status VARCHAR NOT NULL);" +
                "INSERT INTO users_table(username, password, phone, email, role) VALUES('Admin','12345','+998907377735','admin@gmail.com','COMPANY');" +
                "INSERT INTO users_table(username, password, phone, email, role) VALUES('User','123','+998997708839','user@gmail.com','CANDIDATE');";
        try (Connection connection = PostgresqlCon.connection();
             Statement statement = connection.createStatement()) {
             statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addCompanyDescription(int userId){
        Company company = new Company();
        company.setCompanyId(userId);
        System.out.println("Kompaniya nomini kiriting:");
        company.setCompanyName(scanner.next()+scanner.nextLine());
        System.out.println("Kompaniya haqida qisqacha ma'lumot kiriting:");
        company.setInformation(scanner.next()+scanner.nextLine());
        System.out.println("Kompaniya web-sayti manzilini kiriting:");
        company.setWebSaytLink(scanner.next()+scanner.nextLine());
        System.out.println("Kompaniya manzilini kiriting:");
        company.setMainOfficeLocation(scanner.next()+scanner.nextLine());
        System.out.println("Kompaniya xodimlari sonini kiriting:");
        company.setNumberOfWorker(scanner.nextInt());
        System.out.println("Kompaniya rahbari F.I.O kiriting:");
        company.setOwner(scanner.next()+scanner.nextLine());
        insertCompanyDescription(company);
        System.out.println("Ma'lumotlar muvaffaqiyatli kiritildi!");
    }
    private static void insertCompanyDescription(Company company){
        String query = "INSERT INTO company_description(COMPANY_NAME, COMPANY_ID, INFORMATION, WEB_SAYT_LINK, MAIN_OFFICE_LOCATION, NUMBER_OF_WORKER, OWNER) VALUES(?,?,?,?,?,?,?);";
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setString(1,company.getCompanyName());
             preparedStatement.setInt(2,company.getCompanyId());
             preparedStatement.setString(3,company.getInformation());
             preparedStatement.setString(4,company.getWebSaytLink());
             preparedStatement.setString(5,company.getMainOfficeLocation());
             preparedStatement.setInt(6,company.getNumberOfWorker());
             preparedStatement.setString(7,company.getOwner());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static User returnUser(String login,String password){
        String query = "SELECT * FROM users_table WHERE username=? AND password=?;";
        User user = new User();
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setString(1,login);
             preparedStatement.setString(2,password);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 user.setId(resultSet.getInt("id"));
                 user.setUsername(resultSet.getString("username"));
                 user.setPassword(resultSet.getString("password"));
                 user.setPhone(resultSet.getString("phone"));
                 user.setEmail(resultSet.getString("email"));
                 user.setRole(resultSet.getString("role"));
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public static User checkUser(){
        System.out.println("Login kiriting:");
        String login = scanner.next();
        System.out.println("Parol kiriting:");
        String password = scanner.next();
        return returnUser(login,password);
    }

    public static void addResume(int userId){
        Condidate condidate = new Condidate();
        condidate.setUserId(userId);
        System.out.println("Ismingizni kiriting:");
        condidate.setFirstName(scanner.next()+scanner.nextLine());
        System.out.println("Familiyangizni kiriting:");
        condidate.setLastName(scanner.next()+scanner.nextLine());
        System.out.println("O'zingiz haqingizda qisqacha ma'lumot kiriting:");
        condidate.setInformation(scanner.next()+scanner.nextLine());
        System.out.println("Linkedin linkini kiriting:");
        condidate.setLinkedinLink(scanner.next()+scanner.nextLine());
        System.out.println("Manzilingizni kiriting:");
        condidate.setAddress(scanner.next()+scanner.nextLine());
        System.out.println("Yoshingizni kiriting:");
        condidate.setAge(scanner.nextInt());
        insertResume(condidate);
        System.out.println("Ma'lumotlar muvaffaqiyatli kiritildi!");
    }
    private static void insertResume(Condidate condidate){
        String query = "INSERT INTO candidate_resume(candidate_id, first_name, last_name, information, linkedin_link, address, age) VALUES(?,?,?,?,?,?,?);";
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,condidate.getUserId());
             preparedStatement.setString(2,condidate.getFirstName());
             preparedStatement.setString(3,condidate.getLastName());
             preparedStatement.setString(4,condidate.getInformation());
             preparedStatement.setString(5,condidate.getLinkedinLink());
             preparedStatement.setString(6,condidate.getAddress());
             preparedStatement.setInt(7,condidate.getAge());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int checkResumeByCandidateId(int candidateId){
        String query = "SELECT id FROM candidate_resume WHERE candidate_id=?;";
        int resumeId = 0;
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,candidateId);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 resumeId=resultSet.getInt("id");
                 break;
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resumeId;
    }
    public static int checkCompanyIdByCandidateId(int candidateId){
        String query = "SELECT id FROM company_description WHERE company_id=?;";
        int companyId = 0;
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,candidateId);
             ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                companyId=resultSet.getInt("id");
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return companyId;
    }
    public static void addSkills(int resumeId){
        Condidate condidate = new Condidate();
        condidate.setCandidateResumeId(resumeId);
        System.out.println("Code yozadigan dasturlash tilingizni kiriting:");
        condidate.setCodeLanguage(scanner.next()+scanner.nextLine());
        System.out.println("Darajangizni kiriting: (1,2,3,4,5)");
        condidate.setLevel(scanner.nextInt());
        insertSkills(condidate);
        System.out.println("Ma'lumotlar muvaffaqiyatli kiritildi!");
    }
    private static void insertSkills(Condidate condidate){
        String query = "INSERT INTO candidate_skills(CANDIDATE_RESUME_ID, NAME, LEVEL) VALUES(?,?,?);";
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,condidate.getCandidateResumeId());
             preparedStatement.setString(2,condidate.getCodeLanguage());
             preparedStatement.setInt(3,condidate.getLevel());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEducation(int resumeId){
        Condidate condidate = new Condidate();
        condidate.setCandidateResumeId(resumeId);
        System.out.println("Tahsil olgan muassasangizni nomini kiriting:");
        condidate.setSchoolName(scanner.next()+scanner.nextLine());
        System.out.println("Sanani kiriting: Format-> 2013-12-12 dan");
        condidate.setFromDate(scanner.next()+scanner.nextLine());
        System.out.println("Sanani kiriting: Format-> 2023-12-12 gacha");
        condidate.setToDate(scanner.next()+scanner.nextLine());
        System.out.println("Tahsil jarayoni haqida qisqacha ma'lumot kiriting:");
        condidate.setAboutCourses(scanner.next()+scanner.nextLine());
        insertEducation(condidate);
        System.out.println("Ma'lumotlar muvaffaqiyatli kiritildi!");
    }
    private static void insertEducation(Condidate condidate){
        String query = "INSERT INTO candidate_education(candidate_resume_id, school_name, from_date, to_date, about_courses) VALUES(?,?,?,?,?);";
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,condidate.getCandidateResumeId());
             preparedStatement.setString(2,condidate.getSchoolName());
             preparedStatement.setString(3,condidate.getFromDate());
             preparedStatement.setString(4,condidate.getToDate());
             preparedStatement.setString(5,condidate.getAboutCourses());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addJobHistory(int resumeId){
        Condidate condidate = new Condidate();
        condidate.setCandidateResumeId(resumeId);
        System.out.println("IT bo'yicha yo'nalishingizni kiriting:");
        condidate.setJobTitle(scanner.next()+scanner.nextLine());
        System.out.println("Oldin ishlagan kompaniyangiz nomini kiriting:");
        condidate.setCompanyName(scanner.next()+scanner.nextLine());
        System.out.println("Sanani kiriting: Format-> 2013-12-12 dan");
        condidate.setFromDate(scanner.next()+scanner.nextLine());
        System.out.println("Sanani kiriting: Format-> 2023-12-12 gacha");
        condidate.setToDate(scanner.next()+scanner.nextLine());
        System.out.println("Ish jarayoningiz haqida qisqacha ma'lumot kiriting:");
        condidate.setInformationAboutJob(scanner.next()+scanner.nextLine());
        insertJobHistory(condidate);
        System.out.println("Ma'lumotlar muvaffaqiyatli kiritildi!");
    }
    private static void insertJobHistory(Condidate condidate){
        String query = "INSERT INTO candidate_job_history(CANDIDATE_RESUME_ID, JOB_TITLE, CAMPANY_NAME, FROM_DATE, TO_DATE, INFORMATION_ABOUT_JOB) VALUES(?,?,?,?,?,?);";
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,condidate.getCandidateResumeId());
             preparedStatement.setString(2,condidate.getJobTitle());
             preparedStatement.setString(3,condidate.getCompanyName());
             preparedStatement.setString(4,condidate.getFromDate());
             preparedStatement.setString(5,condidate.getToDate());
             preparedStatement.setString(6,condidate.getInformationAboutJob());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addVacancy(int companyId){
        Vacancy vacancy = new Vacancy();
        vacancy.setCompanyId(companyId);
        System.out.println("Dasturchi turini kiriting: (Java Middle)");
        vacancy.setTitle(scanner.next()+scanner.nextLine());
        System.out.println("Dasturchi bilishi kerak bo'lgan texnologiyalarni kiriting: (java, postgresql, git, gihub, html, css, javascript)");
        vacancy.setNeededTech(scanner.next()+scanner.nextLine());
        System.out.println("Kompaniya manzilini kiriting:");
        vacancy.setLocation(scanner.next()+scanner.nextLine());
        System.out.println("Ish vaqtini kiriting: (08:00-17:30)");
        vacancy.setWorkTime(scanner.next()+scanner.nextLine());
        System.out.println("Xodim maoshini kiriting:");
        vacancy.setSalary(scanner.nextFloat());
        vacancy.setIs_active(true);
        insertVacancy(vacancy);
        System.out.println("Ma'lumotlar muvaffaqiyatli kiritildi!");
    }
    private static void insertVacancy(Vacancy vacancy){
        String query = "INSERT INTO vacancy(company_id, title, needed_tech, location, work_time, maosh, is_active) VALUES(?,?,?,?,?,?,?);";
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,vacancy.getCompanyId());
             preparedStatement.setString(2,vacancy.getTitle());
             preparedStatement.setString(3,vacancy.getNeededTech());
             preparedStatement.setString(4,vacancy.getLocation());
             preparedStatement.setString(5,vacancy.getWorkTime());
             preparedStatement.setFloat(6,vacancy.getSalary());
             preparedStatement.setBoolean(7,vacancy.isIs_active());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Vacancy> getVacancyList(){
        String query = "SELECT v.id,company_name,title,needed_tech,v.location,v.work_time,v.maosh FROM vacancy v LEFT JOIN company_description cd on cd.id = v.company_id;";
        List<Vacancy> vacancyList = new ArrayList<>();
        try (Connection connection = PostgresqlCon.connection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery(query);
             while (resultSet.next()){
                 Vacancy vacancy = new Vacancy();
                 vacancy.setVacancyId(resultSet.getInt("id"));
                 vacancy.setTitle(resultSet.getString("title"));
                 vacancy.setCompanyName(resultSet.getString("company_name"));
                 vacancy.setNeededTech(resultSet.getString("needed_tech"));
                 vacancy.setLocation(resultSet.getString("location"));
                 vacancy.setWorkTime(resultSet.getString("work_time"));
                 vacancy.setSalary(resultSet.getFloat("maosh"));
                 vacancyList.add(vacancy);
             }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
         return vacancyList;
     }
     public static void showVacancyList(List<Vacancy> vacancyList){
        if (vacancyList.size()>0){
            System.out.println("Mavjud vakansiyalar ro'yxati:");
            for (int i = 0; i < vacancyList.size(); i++) {
                System.out.println("Vakansiya id: "+vacancyList.get(i).getVacancyId());
                System.out.println("Kompaniya nomi: "+vacancyList.get(i).getCompanyName());
                System.out.println("Dasturchi darajasi: "+vacancyList.get(i).getTitle());
                System.out.println("Talablar (bilishi shart bo'lgan texnologiyalar) : "+vacancyList.get(i).getNeededTech());
                System.out.println("Kompaniya manzili: "+vacancyList.get(i).getLocation());
                System.out.println("Ish vaqti: "+vacancyList.get(i).getWorkTime());
                System.out.println("Maosh: "+vacancyList.get(i).getSalary());
            }
        }else {
            System.out.println("Vakansiyalar mavjud emas!");
        }
     }
     private static boolean checkVacancyById(int vacancyId){
         boolean check = false;
         List<Vacancy> vacancyList = getVacancyList();
         if (vacancyList.size()>0){
             for (int i = 0; i < vacancyList.size(); i++) {
                 if (vacancyList.get(i).getVacancyId()==vacancyId){
                     check=true;
                     break;
                 }
             }
         }
         return check;
     }
    public static void addVacancyCandidate(int candidateId){
        Vacancy vacancy = new Vacancy();
        vacancy.setCandidateId(candidateId);
        System.out.println("Dasturchi turini kiriting: (Java Middle)");
        vacancy.setTitle(scanner.next()+scanner.nextLine());
        System.out.println("Dasturchi bilishi kerak bo'lgan texnologiyalarni kiriting: (java, postgresql, git, gihub, html, css, javascript)");
        vacancy.setNeededTech(scanner.next()+scanner.nextLine());
        System.out.println("Kompaniya manzilini kiriting:");
        vacancy.setLocation(scanner.next()+scanner.nextLine());
        System.out.println("Ish vaqtini kiriting: (08:00-17:30)");
        vacancy.setWorkTime(scanner.next()+scanner.nextLine());
        System.out.println("Xodim maoshini kiriting:");
        vacancy.setSalary(scanner.nextFloat());
        vacancy.setIs_active(true);
        insertVacancyCandidate(vacancy);
        System.out.println("Ma'lumotlar muvaffaqiyatli kiritildi!");
    }
    private static void insertVacancyCandidate(Vacancy vacancy){
        String query = "INSERT INTO vacancy(company_id, title, needed_tech, location, work_time, maosh, is_active) VALUES(?,?,?,?,?,?,?);";
        try (Connection connection = PostgresqlCon.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1,vacancy.getCompanyId());
             preparedStatement.setString(2,vacancy.getTitle());
             preparedStatement.setString(3,vacancy.getNeededTech());
             preparedStatement.setString(4,vacancy.getLocation());
             preparedStatement.setString(5,vacancy.getWorkTime());
             preparedStatement.setFloat(6,vacancy.getSalary());
             preparedStatement.setBoolean(7,vacancy.isIs_active());
             preparedStatement.executeUpdate();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
    }
}
