package basic.mergesort;

/**
 * 小和问题：在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和。要求时间复杂度O(N*logN)
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class SmallSum {

    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    private static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;

        int pL = l;
        int pR = mid + 1;
        int res = 0;
        while (pL <= mid && pR <= r) {
            // 当左组的数小于右组的数时， 当前右组的个数*当前数 的累加和 即是小和的结果
            // 仔细和归并排序比较，发现就多了此处的代码。唯一的区别是，
            // 等于的时候拷贝右组的，因为要在右组中找出比左组大的个数，肯定不能先拷贝左组的，不然咋找出个数
            res += arr[pL] < arr[pR] ? (r - pR + 1) * arr[pL] : 0;
            help[i++] = arr[pL] < arr[pR] ? arr[pL++] : arr[pR++];
        }
        while (pL <= mid) {
            help[i++] = arr[pL++];
        }
        while (pR <= r) {
            help[i++] = arr[pR++];
        }

        for (int j = 0; j < help.length; j++) {
            arr[l + j] = help[j];
        }
        return res;
    }

    /**
     * 对数器方法
     */
    public static int comparator(int[] arr) {
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100;
        int testTimes = 100000;

        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (smallSum(arr1) != comparator(arr2)) {
                printArray(arr1);
                printArray(arr2);
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice" : "Error");
    }

    //------------------------------------------ TEST METHODS ----------------------------------------------

    public static int[] generateArray(int maxSize, int maxValue) {
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

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

}
