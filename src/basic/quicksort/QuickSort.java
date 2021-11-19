package basic.quicksort;

/**
 * 荷兰国旗问题，以及对应的快排写法
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class QuickSort {

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 荷兰国旗问题
     * <p>
     * 把数组arr中，[l, r]范围内的数，小于arr[r]放到数组最左边，等于arr[r]放到数组中间，大于arr[r]放到数组最右边
     *
     * @return 返回等于arr[r]的左, 右下标值
     */
    public static int[] netherlandsFlag(int[] arr, int l, int r) {
        if (l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, r};
        }
        // 小于arr[r]的右边界，从L的左边一位开始
        int less = l - 1;
        // 大于arr[r]的左边界，从r开始，即当前右边界里已经有arr[r]
        int more = r;
        // 当前正在比较的下标
        int index = l;
        // 不能与 大于arr[r]的左边界 撞上
        while (index < more) {
            if (arr[index] < arr[r]) {
                // 小于时，将当前位置的数和小于arr[r]的右边界的下一个位置交换
                // 当前位置后移一位
                swap(arr, index++, ++less);
            } else if (arr[index] == arr[r]) {
                // 等于时，当前位置后移一位即可
                index++;
            } else {
                // 大于时，当前位置的数和大于arr[r]的左边界的前一个位置交换
                // 当前位置不动
                swap(arr, index, --more);
            }
        }
        // 将arr[r]位置的数和大于arr[r]的左边界的数交换
        // 此时整个数组就按照 小于、等于、大于arr[r] 分成了左中右三块
        swap(arr, more, r);

        // 最后整个数组中，等于arr[r]的左右边界分别是less + 1, more
        return new int[]{less + 1, more};
    }

    /**
     * 随机快排
     */
    public static void quickSortRandom(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        processRandom(arr, 0, arr.length - 1);
    }

    private static void processRandom(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        // 随机将数组中的某个数放到比较位置上（即数组最右边位置）
        // 这一步是保证快排时间复杂度是O(N*logN)的关键，不然，快排的时间复杂度是O(N^2)
        swap(arr, l + (int) ((r - l + 1) * Math.random()), r);
        // 将数组划分为 小于、等于、大于arr[r] 的左中右三块
        int[] equalArea = netherlandsFlag(arr, l, r);
        // 此时等于区域的值已经处于最后排序结果的位置了
        // 递归将左半部分的排好序
        processRandom(arr, l, equalArea[0] - 1);
        // 递归将右半部分的排好序
        processRandom(arr, equalArea[1] + 1, r);
    }

    /**
     * 将数组arr[l, r]分成 <= 和 > arr[r]两部分
     *
     * @return 返回 <= 区的最后一个位置
     */
    public static int partition(int[] arr, int l, int r) {
        // 小于等于区的最右边位置
        int less = l - 1;
        // 当前比较位置
        int index = l;
        // 所有数都和r位置的数比较一遍
        while (index < r) {
            if (arr[index] <= arr[r]) {
                // 如果小于等于，则将该位置的数和小于等于区最右边位置的下一个位置交换
                swap(arr, index, ++less);
            }
            // 如果小于等于，交换后当前位置后移
            // 如果大于，则当前位置直接后移
            index++;
        }
        // 将r位置的数和小于等于区最右边位置的下一个位置交换
        // 此时less左侧即是小于等于arr[r]，右侧即是大于arr[r]
        swap(arr, ++less, r);
        return less;
    }

    public static void quickSortComparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        // 获取当前小于等于arr[r]的最右侧位置
        int m = partition(arr, l, r);
        // arr[m]已经在最终排序的位置了
        // 左侧部分递归求解
        process(arr, l, m - 1);
        // 右侧部分递归求解
        process(arr, m + 1, r);
    }

    public static void main(String[] args) {
        int testTime = 100;
        int maxSize = 100000;
        int maxValue = 100;
        boolean isSuccess = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            quickSortComparator(arr1);
            quickSortRandom(arr2);
            if (!isEqual(arr1, arr2)) {
                printArray(arr1);
                printArray(arr2);
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "nice" : "error");
    }

    //------------------------------------------- 辅助测试的方法 -------------------------------------------

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
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
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

}
