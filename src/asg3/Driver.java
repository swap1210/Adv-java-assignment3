package asg3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        int[] sizes = { 100, 500, 1000, 2500, 5000 };

        // INTEGER START
        System.out.println("Integer Test");
        for (int size : sizes) {
            Integer[] testData = fillUniqueIntArray(size);
            performThreeTest(testData);
        }

        for (List<String> row : testResults) {
            for (String item : row) {
                System.out.print(item + "$");
            }
            System.out.println();
        }
        // remove last test results
        for (int x = 1; x <= sizes.length; x++) {
            testResults.remove(1);
        }
        // INTEGER END

        // FLOAT START
        System.out.println("Float Test");
        for (int size : sizes) {
            Float[] testData = fillUniqueFloatArray(size);
            performThreeTest(testData);
        }

        for (List<String> row : testResults) {
            for (String item : row) {
                System.out.print(item + "$");
            }
            System.out.println();
        }
        // remove last test results
        for (int x = 1; x <= sizes.length; x++) {
            testResults.remove(1);
        }
        // FLOAT END

        // CHARACTER START
        System.out.println("Character Test");
        for (int size : sizes) {
            Character[] testData = fillCharArray(size);
            performThreeTest(testData);
        }

        for (List<String> row : testResults) {
            for (String item : row) {
                System.out.print(item + "$");
            }
            System.out.println();
        }
        // remove last test results
        for (int x = 1; x <= sizes.length; x++) {
            testResults.remove(1);
        }
        // CHARACTER END
    }

    // generate an array with n Integer values
    public static Integer[] fillUniqueIntArray(int n) {
        Integer[] arr = new Integer[n];
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

    // generate an array with n float values
    public static Float[] fillUniqueFloatArray(int n) {
        Float[] arr = new Float[n];
        Set<Float> set = new HashSet<Float>();
        Random rand = new Random();
        int i = 0;
        int min = 1;
        int max = n + n;
        while (i < n) {
            float num = rand.nextFloat() * (max - min) + min;
            if (!set.contains(num)) {
                set.add(num);
                arr[i] = num;
                i++;
            }
        }
        return arr;
    }

    // generate a array with n characters values
    public static Character[] fillCharArray(int n) {
        Character[] arr = new Character[n];
        Random rand = new Random();
        int i = 0;
        while (i < n) {
            char num = (char) (rand.nextInt(26) + 'a');
            arr[i] = num;
            i++;
        }
        return arr;
    }

    static <K extends Comparable<K>> void performThreeTest(
            K[] testData) {

        QuickSort<K> qs = new QuickSort<K>();
        List<K> tempData = Collections.synchronizedList(new ArrayList<K>());
        ParallelQuickSortThreadPool<K> pqst = new ParallelQuickSortThreadPool<>();
        pqst.input = Collections.synchronizedList(new ArrayList<K>());
        for (K i : testData) {
            qs.arr.add(i);
            pqst.input.add(i);
            tempData.add(i);
        }
        ParallelQuickSort<K> pqs = new ParallelQuickSort<K>(0, testData.length - 1, tempData);

        MySorters[] sorters = new MySorters[3];
        sorters[0] = qs;
        sorters[1] = pqs;
        sorters[2] = pqst;

        List<String> result = new LinkedList<>();
        result.add("" + testData.length);
        for (int i = 0; i < 3; i++) {
            long QSStartTime = System.nanoTime();
            sorters[i].perform();
            long QSEndTime = System.nanoTime();
            result.add("" + (QSEndTime - QSStartTime) * 1.0 / 1000000);
        }

        // System.out.println(Arrays.toString(testData));
        // pqs.arr.forEach(val -> System.out.print(val + ","));
        testResults.add(result);
    }

    // static void performThreeTest(int[] testData) {
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
