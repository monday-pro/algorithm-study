package basic.nocomparesort;

import java.util.Arrays;

/**
 * 计数排序：适用于排序的值范围较小（例如年龄）
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class CountSort {

    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // 统计
        int max = Integer.MIN_VALUE;
        for (int v : arr) {
            max = Math.max(max, v);
        }
        int[] bucket = new int[max + 1];
        for (int v : arr) {
            bucket[v]++;
        }

        // 排序
        int i = 0;
        for (int j = 0; j < bucket.length; j++) {
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        int maxSize = 10000;
        int maxValue = 200;
        int testTimes = 10000;
        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            countSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                isSuccess = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(isSuccess ? "Finish" : "Error");
    }

    //------------------------------------- 辅助测试的方法 -----------------------------------

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
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
