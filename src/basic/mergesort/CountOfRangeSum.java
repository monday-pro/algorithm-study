package basic.mergesort;

/**
 * 描述：https://leetcode.com/problems/count-of-range-sum/。
 * 中文版：（327. 区间和的个数）https://leetcode-cn.com/problems/count-of-range-sum/
 * <p>
 * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 * <p>
 * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 * <p>
 * 结果直接在leetcode测试
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class CountOfRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length < 1) {
            return 0;
        }

        // 求原数组的前缀和
        long[] preSum = new long[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }

        return process(preSum, 0, preSum.length - 1, lower, upper);
    }

    private static int process(long[] preSum, int L, int R, int lower, int upper) {
        // L == R时，preSum[L] 表示原数组 [0, L]范围上的累加和
        // 在merge过程中，会忽略掉左组一个数也没有的这种情况，所以在这里补充这种情况
        if (L == R) {
            return preSum[L] >= lower && preSum[L] <= upper ? 1 : 0;
        }
        int mid = L + ((R - L) >> 1);
        // 返回左组和右组在合并过程中产生的满足条件的累加和个数
        return process(preSum, L, mid, lower, upper)
                + process(preSum, mid + 1, R, lower, upper)
                + merge(preSum, L, mid, R, lower, upper);
    }

    private static int merge(long[] arr, int L, int mid, int R, int lower, int upper) {
        // 累加和个数
        int ans = 0;
        // 左组寻找的左侧位置（肯定是从当前的L位置开始）
        int windowL = L;
        // 左组寻找的右侧位置（肯定也是从当前的L位置开始）
        int windowR = L;
        // 对于右组的每一个数X，在左组中寻找值在[X-upper, X-lower]之间的个数
        for (int i = mid + 1; i <= R; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;

            // 因为是在左组中寻找，所以下标不能超过mid
            // 寻找当前值比max大的第一个位置（因为等于max的时候右移了一位，所以不包含此位置）
            while (windowR <= mid && arr[windowR] <= max) {
                windowR++;
            }

            // 寻找当前值大于等于min的第一个位置（因为等于min的时候没有右移，所以包含此位置）
            while (windowL <= mid && arr[windowL] < min) {
                windowL++;
            }

            // 最后满足要求的累加和个数为 [windowL, windowR)，即 windowR - windowL，windowR是开区间，所以不 +1
            ans += windowR - windowL;
        }

        // 以下是经典的merge过程
        long[] help = new long[R - L + 1];
        int i = 0;
        int pL = L;
        int pR = mid + 1;
        while (pL <= mid && pR <= R) {
            help[i++] = arr[pL] <= arr[pR] ? arr[pL++] : arr[pR++];
        }
        while (pL <= mid) {
            help[i++] = arr[pL++];
        }
        while (pR <= R) {
            help[i++] = arr[pR++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }

        return ans;
    }

}
