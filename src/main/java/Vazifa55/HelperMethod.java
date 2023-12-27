package Vazifa55;

import java.sql.*;
import java.util.Scanner;

import static Vazifa55.Status.*;

public class HelperMethod {
    static Scanner scannerr = new Scanner(System.in);
    public static void createTables() {
        String query = "CREATE TABLE IF NOT EXISTS product_category(id SERIAL PRIMARY KEY,name VARCHAR NOT NULL UNIQUE,created_date DATE DEFAULT current_date);" +
                "CREATE TABLE IF NOT EXISTS mahsulot(id SERIAL PRIMARY KEY,product_name VARCHAR NOT NULL UNIQUE,yaroqlilik_muddati DATE NOT NULL,category_id INT NOT NULL REFERENCES product_category(id) ON DELETE CASCADE,soni NUMERIC NOT NULL,narxi NUMERIC NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS organization(id SERIAL PRIMARY KEY,name VARCHAR NOT NULL UNIQUE,owner VARCHAR NOT NULL,address VARCHAR NOT NULL UNIQUE,tel VARCHAR NOT NULL UNIQUE);" +
                "CREATE TYPE transaction_status AS ENUM('IMPORT','EXPORT');" +
                "CREATE TABLE IF NOT EXISTS transaction_document(id SERIAL PRIMARY KEY,from_organization INT NOT NULL CHECK (from_organization<>to_organization) REFERENCES organization(id) ON DELETE CASCADE,to_organization INT NOT NULL CHECK (to_organization<>from_organization) REFERENCES organization(id) ON DELETE CASCADE,doc_date DATE NOT NULL,tranzaksiya_status transaction_status);" +
                "CREATE TABLE IF NOT EXISTS transaction_document_item(id SERIAL PRIMARY KEY,doc_id INT NOT NULL UNIQUE REFERENCES transaction_document(id) ON DELETE CASCADE,mahsulot_id INT NOT NULL REFERENCES mahsulot(id) ON DELETE CASCADE);";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createCategory() {
        String query = "INSERT INTO product_category(name, created_date) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            System.out.println("Kategoriya nomini kiriting:");
            String category = scannerr.next() + scannerr.nextLine();
            System.out.println("Kategoriya yaratilgan sanasini kiriting:(Format -> 2023-12-12)");
            String date = scannerr.next();
            preparedStatement.setString(1, category);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.executeUpdate();
            System.out.println("Kategoriya muvaffaqiyatli yaratildi!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void readCategory() {
        String query = "SELECT * FROM product_category;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            int countRow = 0;
            while (resultSet.next()) {
                countRow++;
                System.out.println("-----------------------------------------");
                System.out.println(resultSet.getInt("id") + "\t"
                        + resultSet.getString("name") + "\t"
                        + resultSet.getDate("created_date"));
                System.out.println("-----------------------------------------");
            }
            System.out.println("Jami kategoriyalar soni: " + countRow);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkCategory(int categoryId) {
        String query = "SELECT * FROM product_category WHERE id=?";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
             preparedStatement.setInt(1,categoryId);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 return true;
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public static int helperCategory(){
        System.out.println("Mahsulot kategoriyasini tanlang:");
        readCategory();
        int category = scannerr.nextInt();
        if (!checkCategory(category)){
            System.out.println("Bunday kategoriya menuda mavjud emas!");
        }
        return checkCategory(category) ? category:helperCategory();
    }
    public static void createProduct() {
        String query = "INSERT INTO mahsulot(product_name, yaroqlilik_muddati, category_id, soni, narxi) VALUES(?,?,?,?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
             System.out.println("Mahsulot nomini kiriting:");
             String productName = scannerr.next() + scannerr.nextLine();
             System.out.println("Mahsulot yaroqlilik muddatini kiriting:(Format -> 2023-12-12)");
             String date = scannerr.next();
             int category = helperCategory();
             System.out.println("Mahsulot miqdorini kiriting:");
             float amount = scannerr.nextFloat();
             System.out.println("Mahsulot narxini kiriting:");
             float price = scannerr.nextFloat();
             preparedStatement.setString(1, productName);
             preparedStatement.setDate(2, Date.valueOf(date));
             preparedStatement.setInt(3,category);
             preparedStatement.setFloat(4,amount);
             preparedStatement.setFloat(5,price);
             preparedStatement.executeUpdate();
             System.out.println("Mahsulot muvaffaqiyatli yaratildi!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void readProduct() {
        String query = "SELECT m.id,m.product_name,m.yaroqlilik_muddati,p.name,m.soni,m.narxi FROM mahsulot m LEFT JOIN product_category p ON m.category_id = p.id;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
             int countRow = 0;
             while (resultSet.next()) {
                countRow++;
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(resultSet.getInt("id")+"\t"
                        + resultSet.getString("product_name")+"\t"
                        + resultSet.getDate("yaroqlilik_muddati")+"\t"
                        + resultSet.getString("name")+"\t"
                        + resultSet.getFloat("soni")+"\t"
                        + resultSet.getFloat("narxi"));
                System.out.println("----------------------------------------------------------------------------------");
             }
             System.out.println("Jami mahsulotlar soni: " + countRow);
         } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkProduct(int productId) {
        String query = "SELECT * FROM mahsulot WHERE id=?;";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
             preparedStatement.setInt(1,productId);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                return true;
             }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
         return false;
     }
    public static int helperProduct(){
        System.out.println("Mahsulotni tanlang:");
        readProduct();
        int product = scannerr.nextInt();
        if (!checkProduct(product)){
            System.out.println("Bunday mahsulot menuda mavjud emas!");
        }
        return checkProduct(product) ? product:helperProduct();
    }
    public static void createOrganization() {
        String query = "INSERT INTO organization(name, owner, address, tel) VALUES(?,?,?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
             System.out.println("Tashkilot nomini kiriting:");
             String organization = scannerr.next() + scannerr.nextLine();
             System.out.println("Tashkilot rahbari F.I.O kiriting:");
             String fio = scannerr.next() + scannerr.nextLine();
             System.out.println("Tashkilot manzilini kiriting:");
             String address = scannerr.next() + scannerr.nextLine();
             System.out.println("Tashkilot telefon raqamini kiriting:");
             String tel = scannerr.next() + scannerr.nextLine();
             preparedStatement.setString(1, organization);
             preparedStatement.setString(2, fio);
             preparedStatement.setString(3, address);
             preparedStatement.setString(4, tel);
             preparedStatement.executeUpdate();
             System.out.println("Tashkilot muvaffaqiyatli yaratildi!");
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
     }
    public static void readOrganization() {
        String query = "SELECT * FROM organization;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            int countRow = 0;
            while (resultSet.next()) {
                countRow++;
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(resultSet.getInt("id") + "\t"
                        + resultSet.getString("name") + "\t"
                        + resultSet.getString("owner") + "\t"
                        + resultSet.getString("address") + "\t"
                        + resultSet.getString("tel"));
                System.out.println("----------------------------------------------------------------------------------");
            }
            System.out.println("Jami tashkilotlar soni: " + countRow);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkOrganization(int organizationId) {
        String query = "SELECT * FROM organization WHERE id=?;";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
             preparedStatement.setInt(1,organizationId);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                return true;
             }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
         return false;
    }
    public static int helperOrganization(){
        System.out.println("Tashkilotni tanlang:");
        readOrganization();
        int organization = scannerr.nextInt();
        if (!checkOrganization(organization)){
            System.out.println("Bunday tashkilot menuda mavjud emas!");
        }
        return checkOrganization(organization) ? organization:helperOrganization();
    }
    public static int checkNotEqualOrganizationId(int organizationId){
        System.out.println("Tashkilotni tanlang:");
        readOrganization();
        int organization = scannerr.nextInt();
        if (!checkOrganization(organization)){
            System.out.println("Bunday tashkilot menuda mavjud emas!");
        }else {
            if (organization==organizationId){
                System.out.println("Tashkilot o'ziga mahsulot export yoki import qila olmaydi!");
            }
        }
        return checkOrganization(organization)?(organization!=organizationId?organization:checkNotEqualOrganizationId(organizationId)):checkNotEqualOrganizationId(organizationId);
    }
    public static int maxTransactionDocument(){
        String query = "SELECT max(id) AS id FROM transaction_document;";
        try (Connection connection = PostgresqlConnection.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
             while (resultSet.next()){
               return resultSet.getInt("id");
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static void productExportAndImport(int status){
        String query = "INSERT INTO transaction_document(from_organization, to_organization, doc_date, tranzaksiya_status) VALUES(?,?,?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
             int fromOrganization = helperOrganization();
             int toOrganization = checkNotEqualOrganizationId(fromOrganization);
             String action = status==1?"export":"import";
             System.out.println("Mahsulot "+action+" qilingan sanani kiriting:(Format -> 2023-12-12)");
             String date = scannerr.next();
             int product = helperProduct();
             preparedStatement.setInt(1,fromOrganization);
             preparedStatement.setInt(2,toOrganization);
             preparedStatement.setDate(3,Date.valueOf(date));
             preparedStatement.setObject(4,status==1?EXPORT:IMPORT,Types.OTHER);
             preparedStatement.executeUpdate();
             createTransactionDocumentItem(maxTransactionDocument(),product);
             System.out.println("Muvaffaqiyatli bajarildi!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createTransactionDocumentItem(int docId,int productId){
        String query = "INSERT INTO transaction_document_item(doc_id, mahsulot_id) VALUES(?,?);";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             preparedStatement.setInt(1,docId);
             preparedStatement.setInt(2,productId);
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getExportProductListByIntervalDate(int status){
        String query = "SELECT o.name AS from_organization,o1.name AS to_organization,doc_date,m.product_name FROM transaction_document_item ti LEFT JOIN transaction_document td on td.id = ti.doc_id LEFT JOIN mahsulot m on m.id = ti.mahsulot_id LEFT JOIN organization o ON o.id=td.to_organization LEFT JOIN organization o1 ON o1.id=td.from_organization WHERE tranzaksiya_status=? AND doc_date between ? AND ?;";
        try (Connection connection = PostgresqlConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
             String action = status==1?"export":"import";
             System.out.println("Mahsulot "+action+" qilingan sanani kiriting:(Format -> 2023-12-12) dan");
             String begin = scannerr.next();
             System.out.println("Mahsulot "+action+" qilingan sanani kiriting:(Format -> 2023-12-12) gacha");
             String end = scannerr.next();
             preparedStatement.setObject(1,status==1?EXPORT:IMPORT,Types.OTHER);
             preparedStatement.setDate(2,Date.valueOf(begin));
             preparedStatement.setDate(3,Date.valueOf(end));
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 System.out.println("----------------------------------------------------------------------------------");
                 System.out.println(resultSet.getString("from_organization")+"\t"
                         +resultSet.getString("to_organization")+"\t"
                         +resultSet.getDate("doc_date")+"\t"
                         +resultSet.getString("product_name")
                 );
                 System.out.println("----------------------------------------------------------------------------------");
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
