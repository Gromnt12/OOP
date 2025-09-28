package ru.nsu.bukhanov;
/**класс который реализует пирамидальную сортировку*/
public class HeapSort {

    /**
     * Основной метод для сортировки массива с помощью пирамидальной сортировки
     * @param arr массив для сортировки
     * @return отсортированный массив
     */
    public static int[] heapsort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }

        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }

        return arr;
    }

    /**
     * Преобразует поддерево с корнем в узле i в max-heap
     * @param arr массив
     * @param n размер кучи
     * @param i корневой узел
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }

    /**
     * Вспомогательный метод для вывода массива в консоль
     * @param arr массив для вывода
     */
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}