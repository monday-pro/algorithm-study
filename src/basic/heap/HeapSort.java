package basic.heap;

import java.util.Arrays;

/**
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class HeapSort {

    /**
     * 对于新加进来的index位置的数，请放到数组合适位置，使其成为一个大根堆
     */
    private static void heapInsert(int[] heap, int index) {
        // i = 0 或 i位置数小于父节点
        // while包含这两种终止条件
        while (heap[index] > heap[(index - 1) / 2]) {
            swap(heap, index, (index - 1) / 2);
            // 继续向上比较
            index = (index - 1) / 2;
        }
    }

    /**
     * 将index位置的数往下沉，直到较大的孩子都没自己大，或者没孩子了，最终调整为一个大根堆
     */
    private static void heapify(int[] arr, int index, int heapSize) {
        // 左子树位置
        int left = 2 * index + 1;
        while (left < heapSize) {
            // 找到左右子树哪个值更大
            int maxIndex = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            // 将左右子树中较大的和父节点比较
            maxIndex = arr[maxIndex] > arr[index] ? maxIndex : index;
            // 左右子树较大的值都小于父节点，停止循环
            if (maxIndex == index) {
                break;
            }
            // 将左右子树中较大的和父节点交换
            swap(arr, index, maxIndex);
            // 当前比较的节点位置来到较大的子节点
            index = maxIndex;
            // 重新获取当前节点的左子树
            left = 2 * index + 1;
        }
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 将整个数组调整为大根堆
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        // 当前堆大小
        int heapSize = arr.length;
        // 将堆0位置的数（即最大值）和数组最大位置的数交换，
        // 堆大小减一（当前交换位置后，堆最大值已经处于最终数组排好序的位置了）
        swap(arr, 0, --heapSize);
        // 交换后堆大小不是0，说明堆中还有数据
        while (heapSize > 0) {
            // 将上一步交换到0位置的数下沉，即将调整堆大小后的数组再次调整为大根堆
            heapify(arr, 0, heapSize);
            // 将此时堆0位置的数（即最大值）和数组最大位置的数交换，
            // 堆大小减一（当前交换位置后，此时堆最大值已经处于最终数组排好序的位置了）
            swap(arr, 0, --heapSize);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 比较器方法
     */
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 100;
        int maxValue = 10000;
        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                printArray(arr1);
                printArray(arr2);
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice" : "Error");
    }

    //------------------------------------ 辅助测试的方法 ---------------------------------------

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int v : arr) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

}
