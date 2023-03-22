package asg3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Q2Driver {

    public static void main(String[] args) {

        int[] sizes = { 2, 4, 7, 9, 11 };

        System.out.println("Integer Test");
        for (int size : sizes) {
            Integer[] testData = Driver.fillUniqueIntArray(size);
            performThreeTest(testData);
        }
    }

    private static void performThreeTest(Integer[] testData) {
        List<Integer> tempData = Collections.synchronizedList(new ArrayList<Integer>());
        for (Integer i : testData) {
            tempData.add(i);
        }
        ParallelQuickSort<Integer> pqs = new ParallelQuickSort<Integer>(0, testData.length - 1, tempData);
        pqs.perform();

        System.out.println("Before sort " + Arrays.toString(testData));
        System.out.print("After sort ");
        pqs.arr.forEach(val -> System.out.print(val + ", "));
        System.out.println();
    }
}
