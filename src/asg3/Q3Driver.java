package asg3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Q3Driver {

    public static void main(String[] args) {

        int[] sizes = { 2, 4, 7, 9, 11 };

        System.out.println("Integer Test");
        for (int size : sizes) {
            Integer[] testData = Driver.fillUniqueIntArray(size);
            performThreeTest(testData);
        }
    }

    private static void performThreeTest(Integer[] testData) {
        ParallelQuickSortThreadPool<Integer> pqst = new ParallelQuickSortThreadPool<>();
        pqst.input = Collections.synchronizedList(new ArrayList<Integer>());
        for (Integer i : testData) {
            pqst.input.add(i);
        }
        pqst.perform();

        System.out.println("Before sort " + Arrays.toString(testData));
        System.out.print("After sort ");
        pqst.input.forEach(val -> System.out.print(val + ", "));
        System.out.println();
    }
}
