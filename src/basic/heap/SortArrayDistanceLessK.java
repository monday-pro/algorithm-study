package basic.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 已知一个几乎有序的数组，
 * 几乎有序是指，如果把数组排好序的话，每个元素移动的距离一定不超过K，
 * 并且K相对于数组长度来说是比较小的。
 * <p>
 * 请选择一个合适的排序策略，对这个数组进行排序
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class SortArrayDistanceLessK {

    public static void sortArrayDistanceLessK(int[] arr, int k) {
        if (arr == null || arr.length < 2 || k == 0) {
            return;
        }

        // 默认是小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 标记当前已经放到堆中元素的数组下标
        int index = 0;
        // 将前k个数放进数组中
        for (; index < k; index++) {
            heap.add(arr[index]);
        }
        // 标记当前数组中已经排好序的下标
        int i = 0;
        // 从第k+1个数一直到数组最后一个数，
        for (; index < arr.length; index++) {
            // 每次放进堆中一个数，
            heap.add(arr[index]);
            // 同时弹出此时堆顶元素放到数组中，排序下标后移
            arr[i++] = heap.poll();
        }
        // 将小根堆中剩余数据放到数组中
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int maxSize = 100;
        int maxValue = Integer.MAX_VALUE;
        int k = 5;
        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomMoveLessK(maxSize, maxValue, k);
            int[] arr2 = copyArray(arr1);
            sortArrayDistanceLessK(arr1, k);
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

    //--------------------------------------- 辅助测试的方法 ---------------------------------------

    public static int[] generateRandomMoveLessK(int maxSize, int maxValue, int k) {
        // 保证数组长度一定大于k
        int[] arr = new int[(int) ((maxSize + 1) * Math.random()) + (k + 1)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
        }
        Arrays.sort(arr);
        // 随意交换，保证交换距离不超过k
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min((int) (i + (k + 1) * Math.random()), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
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
        if ((arr1 == null && arr2 == null)) {
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
