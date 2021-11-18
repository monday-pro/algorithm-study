package basic.mergesort;

/**
 * 当前数 大于 右侧数的两倍 的个数
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class BiggerThanRightTwice {

    public static int biggerThanRightTwice(int[] arr) {
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
        int num = 0;
        // l...mid, mid+1...r, 目前右组寻找的范围 [mid+1, windowR)
        int windowR = mid + 1;
        for (int i = l; i <= mid; i++) {
            while (windowR <= r && arr[i] > (arr[windowR] << 1)) {
                windowR++;
            }
            // 此时，左组的数 > 右组的数 * 2，所以符合条件的个数为 (windowR - (mid+1))
            // 因为此时windowR不满足要求，所以不是(windowR - (mid+1)) +1
            num += windowR - mid - 1;
        }

        int[] help = new int[r - l + 1];
        int i = 0;
        int pL = l;
        int pR = mid + 1;
        while (pL <= mid && pR <= r) {
            // 谁小拷贝谁（相等的拷贝左组的）
            help[i++] = arr[pL] <= arr[pR] ? arr[pL++] : arr[pR++];
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

        return num;
    }

    /**
     * 对数器用于测试
     */
    public static int comparator(int[] arr) {
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    num++;
                }
            }
        }
        return num;
    }

    public static void main(String[] args) {
        int testTimes = 1000000;
        int maxSize = 100;
        int maxValue = 100;

        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (biggerThanRightTwice(arr1) != comparator(arr2)) {
                printArray(arr1);
                printArray(arr2);
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice" : "Error");
    }

    //------------------------------------------- 辅助测试的方法 ---------------------------------------------

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
