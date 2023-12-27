package Dars58;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> arrayList = List.of(1, 3, 6, 8, 10, 18, 36);
        System.out.println(arrayList.stream()
                .mapToDouble(x -> x).average().getAsDouble());

        AtomicInteger atomicInteger = new AtomicInteger(0);

        Thread thread1 = new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                atomicInteger.incrementAndGet();
            }
        });
        thread1.start();
        Thread thread2 = new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                atomicInteger.incrementAndGet();
            }
        });
        thread2.start();
        Thread.sleep(3000);
        System.out.println(atomicInteger.get());
    }
}
