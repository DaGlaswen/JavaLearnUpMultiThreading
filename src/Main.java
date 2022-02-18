import java.util.Arrays;
import java.util.Random;

public class Main {
    public static int[] randomInts = randomArr();
    public static volatile int[] addOns = new int[randomInts.length];
    public static Object obj = new Object();

    public static void main(String[] args) {
        System.out.println(Arrays.toString(randomInts));
        int[] divisors = new int[]{3, 5, 7, 19};
        Thread[] threads = new Thread[divisors.length];
        for (int i = 0; i < divisors.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < randomInts.length; j++) {
                    if (randomInts[j] % divisors[finalI] == 0) {
                        synchronized (obj) {
                            addOns[j] += 1;
                        }
                    }
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < randomInts.length; i++) {
            randomInts[i] += addOns[i];
        }

        System.out.println(Arrays.toString(randomInts));
    }


    public static int[] randomArr() {
        int[] randomInts = new int[1000];
        for (int i = 0; i < 1000; i++) {
            randomInts[i] = 100 + new Random().nextInt(100);
        }
        return randomInts;
    }
}
