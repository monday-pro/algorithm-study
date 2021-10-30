package basic.mergesort;

/**
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class MergeSort {

    /**
     * 1.递归方法实现
     */
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        // 左组递归
        process(arr, l, mid);
        // 右组递归
        process(arr, mid + 1, r);
        // 合并
        merge(arr, l, mid, r);
    }

    private static void merge(int[] arr, int l, int m, int r) {
        // 辅助数组
        int[] help = new int[r - l + 1];
        int i = 0;

        // 左组位置
        int pL = l;
        // 右组位置
        int pR = m + 1;

        while (pL <= m && pR <= r) {
            // 谁小拷贝谁，相等的拷贝左组
            help[i++] = arr[pL] <= arr[pR] ? arr[pL++] : arr[pR++];
        }
        // pL和pR有且只有一个会越界，也就是下面两个while只有一个会执行
        while (pL <= m) {
            help[i++] = arr[pL++];
        }
        while (pR <= r) {
            help[i++] = arr[pR++];
        }
        // 拷贝回原数组
        for (int j = 0; j < help.length; j++) {
            arr[l + j] = help[j];
        }
    }

    /**
     * 2.迭代方式实现归并排序
     * <p>
     * 步长调整次数 复杂度是logN，每次调整步长都会遍历一遍整个数组 复杂度是N，整个的时间复杂度是O(N*logN)
     */
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 步长初始值
        int mergeSize = 1;

        // 步长不能超过数组长度
        while (mergeSize < N) {
            // 当前左组的第一个位置
            int L = 0;
            // 左组也不能超过数组长度
            while (L < N) {
                // 左组最后一个位置
                int M = L + mergeSize - 1;
                // 如果左组最后一个位置越界，表明左组都不够则不需要merge
                if (M >= N) {
                    break;
                }

                // 右组的最后一个位置
                // 右组第一个位置是 M + 1，满足个数要求则右组大小是mergeSize，所以最后一个位置是 (M + 1) + mergeSize - 1
                // 不满足则是数组的最大位置 N - 1
                int R = Math.min(M + mergeSize, N - 1);

                // 目前 左组：L......M，右组：M+1......R
                merge(arr, L, M, R);

                // 下一次左组的位置（所以才需要判断L < N）
                L = R + 1;
            }

            // 防止溢出
            // 当步长很靠近N的最大值时，乘以2扩大步长后，步长就溢出了
            // 不能取 >=，假设最大值是9，mergeSize = 4时就不会扩大步长了，但是mergeSize = 8时还有一次merge，所以不能取 =
            if (mergeSize > N / 2) {
                break;
            }

            // 步长每次扩大2倍
            mergeSize <<= 1;
        }

    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;

        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);

            mergeSort1(arr1);
            mergeSort2(arr2);

            if (!isEqual(arr1, arr2)) {
                System.out.println("测试失败");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

    // -------------------------------- TEST METHODS ------------------------------------

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
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

}
