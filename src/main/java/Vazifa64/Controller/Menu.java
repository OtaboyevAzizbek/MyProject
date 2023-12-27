package Vazifa64.Controller;

import Vazifa64.Entity.User;
import Vazifa64.Repository.Repository;

import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    public static void menu(){
        Repository.createTables();
        System.out.println("Tizimga kirish uchun login va parolingizni kiritishingiz kerak!");
        User user = Repository.checkUser();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        if (user!=null){
            if (user.getRole().equalsIgnoreCase("COMPANY")){
                System.out.println("Kerakli bo'limni tanlang:");
                System.out.println("1.Kompaniya ma'lumotlarini yaratish");
                System.out.println("2.Vakansiya yaratish");
                int department = scanner.nextInt();
                switch (department){
                    case 1 -> Repository.addCompanyDescription(user.getId());
                    case 2 -> {
                        if (Repository.checkCompanyIdByCandidateId(user.getId())>0){
                            Repository.addVacancy(Repository.checkCompanyIdByCandidateId(user.getId()));
                        }else {
                            System.out.println("Vakansiya yaratishdan oldin kompaniya yaratishingiz kerak!");
                            menu();
                        }
                    }
                    default -> {
                        System.out.println("Ushbu bo'lim menuda mavjud emas!");
                        menu();
                    }
                }
            }else {
                System.out.println("Kerakli bo'limni tanlang:");
                System.out.println("1.Candidate resume yaratish");
                System.out.println("2.Candidate skills yaratish");
                System.out.println("3.Candidate education yaratish");
                System.out.println("4.Candidate job history yaratish");
                System.out.println("5.Vakansiyalarni ko'rish");
                System.out.println("6.Vakansiyaga topshirish");
                System.out.println("7.Chiqish");
                int department = scanner.nextInt();
                switch (department){
                    case 1-> Repository.addResume(user.getId());
                    case 2-> {
                        if (Repository.checkResumeByCandidateId(user.getId())>0){
                            Repository.addSkills(Repository.checkResumeByCandidateId(user.getId()));
                        }else {
                            System.out.println("Skill yaratishdan oldin resume yaratishingiz kerak!");
                            menu();
                        }
                    }
                    case 3-> {
                        if (Repository.checkResumeByCandidateId(user.getId())>0){
                            Repository.addEducation(Repository.checkResumeByCandidateId(user.getId()));
                        }else {
                            System.out.println("Education yaratishdan oldin resume yaratishingiz kerak!");
                            menu();
                        }
                    }
                    case 4-> {
                        if (Repository.checkResumeByCandidateId(user.getId()) > 0) {
                            Repository.addJobHistory(Repository.checkResumeByCandidateId(user.getId()));
                        } else {
                            System.out.println("Job history yaratishdan oldin resume yaratishingiz kerak!");
                            menu();
                        }
                    }
                    case 5 -> {
                        Repository.showVacancyList(Repository.getVacancyList());
                        menu();
                    }
                    case 6 -> {

                    }
                    case 7-> menu();
                    default -> {
                        System.out.println("Ushbu bo'lim menuda mavjud emas!");
                        menu();
                    }
                }
            }
        }else {
            System.out.println("Ushbu login parol bazada mavjud emas!");
        }
    }
}
