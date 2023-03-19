class QuickSort {

    // a function that switches two elements
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // This function uses the final element as the pivot, moves the pivot element to
    // its proper position in the sorted array, and then moves all smaller elements
    // to the left of the pivot and all larger elements to the right of the pivot.
    static int partition(int[] arr, int low, int high) {

        // pivot
        int pivot = arr[high];

        // A smaller element's index that also identifies the pivot's proper location as
        // of this point.
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // if pivot is smaller than current element
            if (arr[j] < pivot) {

                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    static void perform(int[] arr) {
        perform(arr, 0, arr.length - 1);
    }

    // recurrent method to perform quick sort
    static void perform(int[] arr, int low, int high) {
        if (low < high) {

            // pi is dividing the index, and arr[p] is now in the proper position.
            int pi = partition(arr, low, high);

            // Sort the components before and after the partition separately.
            perform(arr, low, pi - 1);
            perform(arr, pi + 1, high);
        }
    }
}
