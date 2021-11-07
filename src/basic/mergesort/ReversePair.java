package basic.mergesort;

/**
 * 逆序对问题：设有一个数组 [a1, a2, a3,... an]，对于数组中任意两个元素ai，aj，若i<j 且 ai>aj，则说明ai和aj是一对逆序对。
 * 求一个给定数组的逆序对个数。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class ReversePair {

    public static int reversePairNum(int[] arr) {
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
        // 辅助数组
        int[] help = new int[r - l + 1];
        // 辅助下标，由于从右往左合并，所以下标为数组最大值
        int i = help.length - 1;

        // 同理，左组第一个数位置为mid
        int pL = mid;
        // 右组第一个数为最后一个
        int pR = r;
        // 逆序对个数
        int num = 0;
        while (pL >= l && pR >= (mid + 1)) {
            // 找到右组第一个比左组小的数，则当前满足要求的逆序对个数为 (pR - (mid + 1) + 1) 即是 (pR - mid)
            num += arr[pL] > arr[pR] ? (pR - mid) : 0;
            // 从右往左拷贝，相等的拷贝右组的
            help[i--] = arr[pL] > arr[pR] ? arr[pL--] : arr[pR--];
        }
        // 左组和右组有且仅有一个未拷贝完，所以以下两个循环只会执行其中一个
        while (pL >= l) {
            help[i--] = arr[pL--];
        }
        while (pR > mid) {
            help[i--] = arr[pR--];
        }

        // 拷贝回原数组
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
                if (arr[j] < arr[i]) {
                    num++;
                }
            }
        }
        return num;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;

        boolean isSuccess = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (reversePairNum(arr1) != comparator(arr2)) {
                printArray(arr1);
                printArray(arr2);
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice" : "Error");
    }

    //--------------------------------------- 辅助测试的方法 ---------------------------------------------

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

        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

}
