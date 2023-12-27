package Vazifa55;

import java.util.Scanner;

public class Controller {
    public static void main(String[] args) {
        HelperMethod.createTables();
        menu();
    }
    private static void menu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kerakli bo'limni tanlang:");
        System.out.println("1.Yangi kategoriya qo'shish");
        System.out.println("2.Yangi mahsulot qo'shish");
        System.out.println("3.Yangi tashkilot qo'shish");
        System.out.println("4.Mahsulot export qilish");
        System.out.println("5.Mahsulot import qilish");
        System.out.println("6.Export qilingan mahsulotlarni ko'rish (sana oralig'i bo'yicha)");
        System.out.println("7.Import qilingan mahsulotlarni ko'rish (sana oralig'i bo'yicha)");
        System.out.println("8.Chiqish");
        int department = scanner.nextInt();
        switch (department){
            case 1 -> {
                HelperMethod.createCategory();
                menu();
            }
            case 2 -> {
                HelperMethod.createProduct();
                menu();
            }
            case 3 -> {
                HelperMethod.createOrganization();
                menu();
            }
            case 4 -> {
                HelperMethod.productExportAndImport(1);
                menu();
            }
            case 5 -> {
                HelperMethod.productExportAndImport(2);
                menu();
            }
            case 6 -> {
                HelperMethod.getExportProductListByIntervalDate(1);
                menu();
            }
            case 7 -> {
                HelperMethod.getExportProductListByIntervalDate(2);
                menu();
            }
            case 8 -> System.out.println("Dastur o'z ishini muvaffaqiyatli yakunladi!");
            default -> {
                System.out.println("Faqat menuda mavjud bo'limni tanlashingiz mumkin!");
                menu();
            }
        }
    }
}
