import java.util.List;

class QuickSort<T extends Comparable> {
    public List<T> arr;

    // a function that switches two elements
    void swap(int i, int j) {
        T temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    // This function uses the final element as the pivot, moves the pivot element to
    // its proper position in the sorted array, and then moves all smaller elements
    // to the left of the pivot and all larger elements to the right of the pivot.
    int partition(int low, int high) {

        // pivot
        T pivot = arr.get(high);

        // A smaller element's index that also identifies the pivot's proper location as
        // of this point.
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // if pivot is smaller than current element
            if (arr.get(j).compareTo(pivot) < 0) {

                // Increment index of
                // smaller element
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return (i + 1);
    }

    void perform() {
        perform(0, arr.size() - 1);
    }

    // recurrent method to perform quick sort
    void perform(int low, int high) {
        if (low < high) {
            // pi is dividing the index, and arr[p] is now in the proper position.
            int pi = partition(low, high);

            // Sort the components before and after the partition separately.
            perform(low, pi - 1);
            perform(pi + 1, high);
        }
    }
}
