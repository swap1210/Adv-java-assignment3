package asg3;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelQuickSort<T extends Comparable<T>>
        extends RecursiveTask<Integer> implements MySorters {
    public List<T> arr;
    int start, end;

    public void perform() {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        // Start the first thread in the range 0, n-1 of the fork join pool.
        pool.invoke(
                new ParallelQuickSort<T>(
                        0, arr.size() - 1, arr));
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
    private int partition(int start, int end) {

        int i = start, j = end;

        // Decide random pivot
        int pivoted = new Random()
                .nextInt(j - i)
                + i;

        // Swap the pivoted with end
        // element of array;
        T t = arr.get(j);
        arr.set(j, arr.get(pivoted));
        arr.set(pivoted, t);
        j--;

        // Start partitioning
        while (i <= j) {

            if (arr.get(i).compareTo(arr.get(end)) <= 0) {
                i++;
                continue;
            }
            // arr[j] >= arr[end]
            if (arr.get(j).compareTo(arr.get(end)) >= 0) {
                j--;
                continue;
            }

            t = arr.get(j);
            arr.set(j, arr.get(i));
            arr.set(i, t);
            j--;
            i++;
        }

        // Swap pivoted to its
        // correct position
        t = arr.get(j + 1);
        arr.set(j + 1, arr.get(end));
        arr.set(end, t);
        return j + 1;
    }

    // Function to implement
    // QuickSort method
    public ParallelQuickSort(int start,
            int end, List<T> arr) {
        this.start = start;
        this.end = end;
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        // Base case
        if (start >= end)
            return null;

        // Find partition
        int p = partition(start, end);

        // Divide array
        ParallelQuickSort<T> left = new ParallelQuickSort<T>(start,
                p - 1,
                arr);

        ParallelQuickSort<T> right = new ParallelQuickSort<T>(p + 1,
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
