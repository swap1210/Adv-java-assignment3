
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelQuickSort<T extends Comparable>
        extends RecursiveTask<Integer> {
    List<T> newList;
    int start, end;
    int[] arr;

    public ParallelQuickSort() {
        newList = Collections.synchronizedList(new ArrayList<T>());
    }

    public static void perform(int[] arr) {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        // Start the first thread in the range 0, n-1 of the fork join pool.
        pool.invoke(
                new ParallelQuickSort(
                        0, arr.length - 1, arr));
    }

    /**
     * locating random pivoted arrays and partitioning them on a pivot.
     * The algorithms used for partitioning come in numerous varieties.
     * 
     * @param start
     * @param end
     * @param arr
     * @return
     */
    private static int partition(int start, int end,
            int[] arr) {

        int i = start, j = end;

        // Decide random pivot
        int pivoted = new Random()
                .nextInt(j - i)
                + i;

        // Swap the pivoted with end
        // element of array;
        int t = arr[j];
        arr[j] = arr[pivoted];
        arr[pivoted] = t;
        j--;

        // Start partitioning
        while (i <= j) {

            if (arr[i] <= arr[end]) {
                i++;
                continue;
            }

            if (arr[j] >= arr[end]) {
                j--;
                continue;
            }

            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j--;
            i++;
        }

        // Swap pivoted to its
        // correct position
        t = arr[j + 1];
        arr[j + 1] = arr[end];
        arr[end] = t;
        return j + 1;
    }

    // Function to implement
    // QuickSort method
    public ParallelQuickSort(int start,
            int end,
            int[] arr) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // Base case
        if (start >= end)
            return null;

        // Find partition
        int p = partition(start, end, arr);

        // Divide array
        ParallelQuickSort left = new ParallelQuickSort(start,
                p - 1,
                arr);

        ParallelQuickSort right = new ParallelQuickSort(p + 1,
                end,
                arr);

        // Left subproblem as separate thread
        left.fork();
        right.compute();

        // Wait until left thread complete
        left.join();

        // We don't want anything as return
        return null;
    }

}
