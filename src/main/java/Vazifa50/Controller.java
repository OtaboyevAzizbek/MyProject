package Vazifa50;

import java.util.Scanner;

public class Controller {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        menu();
    }
    public static void menu() {
        System.out.println("Kerakli bo'limni tanlang:");
        System.out.println("1.Ro'yxatdan o'tish");
        System.out.println("2.Yangi e'lonlar qo'shish");
        System.out.println("3.E'lonlar ro'yxatini ko'rish");
        System.out.println("4.Yangi foydalanuvchi qo'shish");
        System.out.println("5.Chiqish");
        int bolim = scanner.nextInt();
        switch (bolim){
            case 1-> signUp();
            case 2-> {
                System.out.print("Login kiriting: ");
                String login = scanner.next();
                System.out.print("Password kiriting: ");
                String password = scanner.next();
                int role = MyMethod.checkUser(login,password);
                if (role>0){
                    if (role<=2){
                        System.out.println("Kompaniya nomini kiriting:");
                        String company = scanner.nextLine()+scanner.next();
                        System.out.println("Kompaniya talabini kiriting:");
                        String requirement = scanner.next()+scanner.nextLine();
                        System.out.println("Dasturchi maoshini kiriting:");
                        float salary = scanner.nextFloat();
                        System.out.println("Kompaniya manzilini kiriting:");
                        String address = scanner.next()+scanner.nextLine();
                        System.out.println("Bog'lanish:");
                        String contact_us = scanner.next()+scanner.nextLine();
                        System.out.println("Dasturchi turini kiriting:");
                        String position = scanner.next()+scanner.nextLine();
                        MyMethod.insertVacancy(company,requirement,salary,address,contact_us,position);
                    }else {
                        System.out.println("Sizga ushbu bo'limdan foydalanish mumkin emas!");
                        menu();
                    }
                }else {
                    System.out.println("Ushbu login va parol bazada mavjud emas!");
                    menu();
                }
            }
            case 3-> MyMethod.select();
            case 4-> {
                System.out.print("Login kiriting: ");
                String login = scanner.next();
                System.out.print("Password kiriting: ");
                String password = scanner.next();
                int role = MyMethod.checkUser(login,password);
                if (role==1){
                    signUp();
                } else if (role==-1) {
                    System.out.println("Ushbu login va parol bazada mavjud emas!");
                    menu();
                } else {
                    System.out.println("Sizga ushbu bo'limdan foydalanish mumkin emas!");
                    menu();
                }
            }
            case 5-> System.out.println("Dastur o'z ishini muvaffaqiyatli yakunladi!");
            default -> {
                System.out.println("Faqat menuda mavjud bo'limni tanlashingiz mumkin!");
                menu();
            }
        }
    }
    public static void signUp(){
        System.out.println("Foydalanuvchi nomini kiriting: ");
        String user = scanner.nextLine()+scanner.next();
        System.out.println("Foydalanuvchi turini tanlang:");
        System.out.println("1.Admin");
        System.out.println("2.Recruiter");
        System.out.println("3.Developer");
        int type = scanner.nextInt();
        switch (type){
            case 1, 2, 3 -> checkLogin(user,type);
            default -> {
                System.out.println("Faqat menuda mavjud bo'limni tanlashingiz mumkin!");
                signUp();
            }
        }
    }
    public static void checkLogin(String user,int role){
        System.out.print("Login yarating: ");
        String login = scanner.next();
        int isCheck = MyMethod.checkLogin(login);
        if (isCheck>0){
            System.out.println("Ushbu login bazada mavjud!");
            checkLogin(user, role);
        }
        System.out.print("Password yarating: ");
        String password = scanner.next();
        MyMethod.insertUser(user,login,password,role);
    }
}