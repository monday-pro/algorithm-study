package basic.greedy;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 一块金条切成两半，是需要花费和长度数值一样的铜板的。比如长度为20的金条，不管怎么切，都要花费20个铜板。
 * 一群人想整分整块金条，怎么分最省铜板？输入一个数组，返回分割的最小代价。
 * <p>
 * 例如：
 * 给定数组{10,20,30}，代表一共三个人， 整块金条长度为10 + 20 + 30 = 60，金条要分成10, 20, 30三个部分（不考虑顺序）。
 * 如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50;一共花费110铜板。
 * 但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花费90铜板。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class LessMoneySplitGold {

    public static int lessMoneySplitGold(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // 准备一个小根堆
        Queue<Integer> queue = new PriorityQueue<>();
        // 将所有数放到小根堆中
        for (Integer s : arr) {
            queue.offer(s);
        }

        int result = 0;
        int current;
        while (queue.size() > 1) {
            // 每次弹出堆顶两个数求和
            current = queue.poll() + queue.poll();
            result += current;
            queue.offer(current);
        }

        return result;
    }

    /**
     * 对数器方法
     */
    public static int lessMoneySplitGold1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    /**
     * arr中只剩一个数字的时候，停止合并
     *
     * @param arr 当前数组中等待合并的数
     * @param pre 之前的合并行为产生了多少总代价（需要花费的铜板数）
     * @return 最小的总代价
     */
    private static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                answer = Math.min(answer, process(copyMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return answer;
    }

    private static int[] copyMergeTwo(int[] arr, int i, int j) {
        int length = arr.length - 1;
        int[] ans = new int[length];
        int resultIndex = 0;
        for (int k = 0; k < arr.length; k++) {
            if (k != i && k != j) {
                ans[resultIndex++] = arr[k];
            }
        }
        ans[length - 1] = arr[i] + arr[j];
        return ans;
    }

    public static void main(String[] args) {
        int[] a = {10, 20, 30, 40, 50};
        System.out.println(lessMoneySplitGold(a));

        int maxLength = 6;
        int maxValue = 1000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            if (lessMoneySplitGold(arr) != lessMoneySplitGold1(arr)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    // ------------------------------- 辅助测试的方法 ---------------------------------

    public static int[] generateRandomArray(int maxLength, int maxValue) {
        int[] ans = new int[(int) ((maxLength + 1) * Math.random())];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) ((maxValue + 1) * Math.random());
        }
        return ans;
    }

}
