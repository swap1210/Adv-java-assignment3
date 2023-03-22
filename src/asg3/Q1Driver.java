package asg3;

import java.util.Arrays;

public class Q1Driver {
    public static void main(String[] args) {

        int[] sizes = { 2, 4, 7, 9, 11 };

        System.out.println("Integer Test");
        for (int size : sizes) {
            Integer[] testData = Driver.fillUniqueIntArray(size);
            performThreeTest(testData);
        }
    }

    private static void performThreeTest(Integer[] testData) {
        QuickSort<Integer> qs = new QuickSort<Integer>();
        for (Integer i : testData) {
            qs.arr.add(i);
        }
        qs.perform();

        System.out.println("Before sort " + Arrays.toString(testData));
        System.out.print("After sort ");
        qs.arr.forEach(val -> System.out.print(val + ", "));
        System.out.println();
    }

}
