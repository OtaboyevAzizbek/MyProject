package Vazifa52;

public class Main {
    public static void main(String[] args) {
        MyMethod.createDatabaseAndTable();

        MyMethod.insertProduct("MacBookAir","Description of macbook air");
        MyMethod.insertProduct("Acer E2","Description of acer e2");
        MyMethod.insertProduct("MacBookPro","Description of macbookpro");
        MyMethod.insertProduct("Samsung S8","Description of samsung s8");

        MyMethod.insertClient("Sanjar Olimov","+998901234567");
        MyMethod.insertClient("Uktamjon Komilov","+998934562781");
        MyMethod.insertClient("Ilhom Xoshimov","+998995673829");

        MyMethod.insertOrder(1,1);
        MyMethod.insertOrder(2,2);
        MyMethod.insertOrder(1,3);

        MyMethod.selectWithLeftJoin();
        MyMethod.selectWithRightJoin();
        MyMethod.selectWithFullJoin();
    }
}
