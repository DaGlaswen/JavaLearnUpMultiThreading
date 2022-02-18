import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static int[] randomInts = randomArr();

    public static void main(String[] args) {
        int[] divisors = new int[]{3, 5, 7, 9, 11};
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<HashMap<String, Integer>>> futureList = new ArrayList<>();
        for (int divisor : divisors) {
            Future<HashMap<String, Integer>> future = service.submit(() -> {
                HashMap<String, Integer> result = new HashMap<>();
                int sum = 0;
                for (int i = 0; i < randomInts.length; i++) {
                    if (i % divisor == 0) {
                        sum += i;
                    }
                }
                result.put("divisor", divisor);
                result.put("sum", sum);
                return result;
            });
            futureList.add(future);
        }
        service.shutdown();

        int maxSum = Integer.MIN_VALUE;
        int maxDiv = 0;
        for (Future<HashMap<String, Integer>> future : futureList) {
            HashMap<String, Integer> x = null;
            try {
                x = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            int currSum = x.get("sum");
            if (currSum > maxSum) {
                maxSum = currSum;
                maxDiv = x.get("divisor");
            }
        }


        System.out.println("Сумма чисел делящихся на " + maxDiv + " максимальная и равна " + maxSum);
    }

    public static int[] randomArr() {
        int[] randomInts = new int[1000];
        for (int i = 0; i < 1000; i++) {
            randomInts[i] = 100 + new Random().nextInt(100);
        }
        return randomInts;
    }
}
