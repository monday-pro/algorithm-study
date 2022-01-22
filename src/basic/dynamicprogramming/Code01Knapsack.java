package basic.dynamicprogramming;

/**
 * 背包问题
 * <p>
 * 有n种物品，w数组表示物品重量、v数组表示物品的价值，物品 i 的重量为 w[i]，价值为 v[i]，
 * 假定所有物品的重量和价值都是非负的，背包所能承受的最大重量为bag，
 * 在背包承重范围内，如何挑选物品使得价值最大，返回最大的价值。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Code01Knapsack {

    public static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return process1(w, v, 0, bag);
    }

    /**
     * 当前考虑到了index位置的货物，index前的已经做好选择了不能改，index及以后的还能随意选择
     * 返回不超过背包容量的最大价值
     */
    public static int process1(int[] w, int[] v, int index, int bag) {
        // 背包已经装不下了
        if (bag < 0) {
            return -1;
        }
        // 当前已经没有货物可以选择了
        if (index == w.length) {
            return 0;
        }
        // 不要当前位置的货物
        int p1 = process1(w, v, index + 1, bag);
        // 要当前位置的货物
        int p2 = 0;
        // 需要判断当前背包减去当前货物后的容量，小于0则也不能要当前货物
        int next = process1(w, v, index + 1, bag - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }

    public static int dp(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;
        // 根据可变参数index和bag定义动态规划数组
        int[][] dp = new int[N + 1][bag + 1];
        // bag < 0 时，返回值-1表示无效值可以不用管，因为bag最小值就是0，在数组下标就不会小于0
        // index == w.length时，返回值0，因为int默认就是0，所以不用手动设置
        // 根据递归过程可知，当前index位置的值总是依赖index+1（下一行）位置的值，所以行index从下往上
        // 同一行的bag之间相互不依赖，所以从任意位置遍历bag（列）均可
        for (int index = N - 1; index >= 0; index--) {
            for (int restBag = 0; restBag <= bag; restBag++) {
                // 以下直接由暴力递归修改即可
                int p1 = dp[index + 1][restBag];
                int p2 = 0;
                int next = restBag - w[index] < 0 ? -1 : dp[index + 1][restBag - w[index]];
                if (next != -1) {
                    p2 = v[index] + next;
                }
                dp[index][restBag] = Math.max(p1, p2);
            }
        }
        // 根据暴力递归的主函数调用process1(w, v, 0, bag)可得返回(0, bag)位置的值
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] w = {3, 2, 4, 7, 3, 1, 7};
        int[] v = {5, 6, 3, 19, 12, 4, 2};
        int bag = 15;
        System.out.println(maxValue(w, v, bag));
        System.out.println(dp(w, v, bag));
    }

}
