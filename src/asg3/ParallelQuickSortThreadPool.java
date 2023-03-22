package asg3;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelQuickSortThreadPool<T extends Comparable> {
    public List<T> input;

    // Get all possible thread count from the system
    private final int N_THREADS = Runtime.getRuntime().availableProcessors();

    // To employ several while deciding when to fall back.
    private final int FALLBACK = 2;

    // Thread pool used for executing sorting Runnables.
    private Executor pool = Executors.newFixedThreadPool(N_THREADS);

    /**
     * The starter method sorting. With the aid of several threads,
     * input is sorted in order.
     *
     * @param input The array to sort.
     */
    public void perform() {
        final AtomicInteger count = new AtomicInteger(1);
        pool.execute(new QuicksortRunnable<T>(input, 0, input.size() - 1, count));
        try {
            synchronized (count) {
                count.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Makes use of quicksort to sort a block of an array. The technique employed
    // merely generates new runnables and passes them to the ThreadPoolExecutor, so
    // it is not technically recursive.

    private class QuicksortRunnable<T extends Comparable> implements Runnable {
        // The array being sorted.
        private final List<T> values;
        // The starting index of the block of the array to be sorted.
        private final int left;
        // The ending index of the block of the array to be sorted.
        private final int right;
        // The number of threads currently executing.
        private final AtomicInteger count;

        /**
         * Default constructor. Sets up the runnable object for execution.
         *
         * @param values The array to sort.
         * @param left   The starting index of the block of the array to be sorted.
         * @param right  The ending index of the block of the array to be sorted.
         * @param count  The number of currently executing threads.
         */
        public QuicksortRunnable(List<T> values, int left, int right, AtomicInteger count) {
            this.values = values;
            this.left = left;
            this.right = right;
            this.count = count;
        }

        // Run logic for the thread. After this thread is finished, it checks to verify
        // if every other thread is also finished. If so, we inform the count object so
        // Sorter.quicksort will no longer block.

        @Override
        public void run() {
            quicksort(left, right);
            synchronized (count) {
                // AtomicInteger.getAndDecrement() returns the old value. If the old value is 1,
                // then we know that the actual value is 0.
                if (count.getAndDecrement() == 1)
                    count.notify();
            }
        }

        // Method which actually does the sorting. Falls back to recursion if there are
        // too many running tasks in the queue.

        // @param pLeft The left index of the sub array to sort.
        // @param pRight The right index of the sub array to sort.

        private void quicksort(int pLeft, int pRight) {
            if (pLeft < pRight) {
                int storeIndex = partition(pLeft, pRight);
                if (count.get() >= FALLBACK * N_THREADS) {
                    quicksort(pLeft, storeIndex - 1);
                    quicksort(storeIndex + 1, pRight);
                } else {
                    count.getAndAdd(2);
                    pool.execute(new QuicksortRunnable(values, pLeft, storeIndex - 1, count));
                    pool.execute(new QuicksortRunnable(values, storeIndex + 1, pRight, count));
                }
            }
        }

        // To divide the area of the array between indices left and right, inclusively,
        // moves all entries with values less than values[pivotIndex] before the pivot
        // and all elements with values equal to or more after it.
        // @param pLeft
        // @param pRight
        // @return The final index of the pivot value.

        private int partition(int pLeft, int pRight) {
            T pivotValue = values.get(pRight);
            int storeIndex = pLeft;
            for (int i = pLeft; i < pRight; i++) {
                if (values.get(i).compareTo(pivotValue) < 0) {
                    swap(i, storeIndex);
                    storeIndex++;
                }
            }
            swap(storeIndex, pRight);
            return storeIndex;
        }

        // Method to Swap integers
        private void swap(int left, int right) {
            T temp = values.get(left);
            values.set(left, values.get(right));
            values.set(right, temp);
        }
    }
}
