import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.List;

public class Driver {
    static List<List<String>> testResults;

    public static void main(String[] args) {
        testResults = new LinkedList<>();
        List<String> resultHeader = new LinkedList<>();
        resultHeader.add("Size");
        resultHeader.add("Quick Sort");
        resultHeader.add("Parallel Quick Sort ");
        resultHeader.add("Thread Pooled Parallel Quick Sort ");
        testResults.add(resultHeader);

        int[] sizes = { 2, 4, 5, 10, 12 };
        // int[] sizes = { 100, 500, 1000, 2500, 5000 };
        for (int size : sizes) {
            int[] testData = fillUniqueArray(size);
            System.out.println(Arrays.toString(testData));
            QuickSort qs = new QuickSort<Integer>();
            qs.arr = new ArrayList<Integer>();
            for (int i : testData) {
                qs.arr.add(i);
            }
            qs.perform();
            qs.arr.forEach(val -> System.out.print(val + ","));
            System.out.println();
            // performThreeTest(testData);
        }

        for (List<String> row : testResults) {
            for (String item : row) {
                System.out.print(item + "$");
            }
            System.out.println();
        }
    }

    public static int[] fillUniqueArray(int n) {
        int[] arr = new int[n];
        Set<Integer> set = new HashSet<Integer>();
        Random rand = new Random();
        int i = 0;
        while (i < n) {
            int num = rand.nextInt(n + n) + 1;
            if (!set.contains(num)) {
                set.add(num);
                arr[i] = num;
                i++;
            }
        }
        return arr;
    }

    // static void performThreeTest(int[] testData) {
    // List<String> result = new LinkedList<>();
    // result.add("" + testData.length);
    // int[][] testDataQS = new int[3][testData.length];
    // for (int i = 0; i < 3; i++) {
    // testDataQS[i] = Arrays.copyOf(testData, testData.length);
    // }

    // MyFunction[] qs = new MyFunction[3];
    // qs[0] = (int[] x) -> QuickSort.perform(x);
    // qs[1] = (int[] x) -> ParallelQuickSort.perform(x);
    // qs[2] = (int[] x) -> ParallelQuickSortThreadPool.perform(x);

    // for (int i = 0; i < 3; i++) {
    // long QSStartTime = System.nanoTime();
    // qs[i].apply(testDataQS[i]);
    // long QSEndTime = System.nanoTime();
    // result.add("" + (QSEndTime - QSStartTime) * 1.0 / 1000000);
    // }
    // testResults.add(result);
    // }
}
