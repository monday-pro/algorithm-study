package basic.nocomparesort;

import java.util.Arrays;

/**
 * 基数排序
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class RadixSort {

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        sort(arr, 0, arr.length - 1, maxBit(arr));
    }

    /**
     * 查找数组最大十进制位数
     */
    public static int maxBit(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int value : arr) {
            max = Math.max(max, value);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    private static void sort(int[] arr, int l, int r, int digit) {
        int radix = 10;
        // 辅助数组
        int[] help = new int[r - l + 1];
        // 有多少位就进桶几次、出桶几次
        for (int d = 1; d <= digit; d++) {
            // 前缀和数组：count[i]当前位（d位）是[0-i]的数有多少个
            int[] count = new int[radix];
            // 统计此时数组d位上的数各出现了几次，
            for (int i = l; i <= r; i++) {
                // 得到d位上的值，范围[0,9]
                int v = getDigit(arr[i], d);
                count[v]++;
            }
            // 此时，count[i]表示当前位（d位）是[0-i]的数有多少个
            for (int i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }
            // 从右到左出桶
            for (int i = r; i >= l; i--) {
                // 得到d位上的值
                int v = getDigit(arr[i], d);
                help[--count[v]] = arr[i];
            }
            // 拷贝回原数组
            for (int i = 0; i < help.length; i++) {
                arr[l + i] = help[i];
            }
        }
    }

    public static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 10000;
        int testTimes = 10000;
        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
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
