package basic.dynamicprogramming;

/**
 * 机器人移动问题
 * <p>
 * 对于N个格子（从1~N标号），机器人最开始在Start（1<=Start<=N）位置，
 * 要求在走过K（K>=1）步后（一次一格），来到aim位置（1<=aim<=N），
 * 问机器人有多少种走法？（注：在两端的格子只能往中间走，在中间的任意一个格子，可以选择左走或右走）
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class RobotWalk {

    /**
     * 1.从尝试开始
     */
    public static int way1(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        return process1(start, k, aim, n);
    }

    /**
     * 计算机器人满足条件的走法有多少种
     *
     * @param current   当前位置
     * @param remaining 剩余步数
     * @param aim       目标位置
     * @param n         格子数
     */
    private static int process1(int current, int remaining, int aim, int n) {
        // base case，不需要走时
        if (remaining == 0) {
            // 剩余步数为0时当前正好在aim位置，则这是一种走法
            return current == aim ? 1 : 0;
        }
        // 还有步数要走
        if (current == 1) {
            // 在最左侧，只能往右走
            return process1(2, remaining - 1, aim, n);
        } else if (current == n) {
            // 在最右侧，只能往左走
            return process1(n - 1, remaining - 1, aim, n);
        } else {
            // 在中间位置，左走或右走都可以，所以是两种情况产生的结果之和
            return process1(current - 1, remaining - 1, aim, n)
                    + process1(current + 1, remaining - 1, aim, n);
        }
    }

    /**
     * 2.傻缓存法
     */
    public static int way2(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        // 机器人当前位置范围是1~n，剩余步数范围是0~k，dp表(n + 1)*(k + 1)肯定是能够将所有的情况都包含的
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -1;
            }
        }
        // dp[current][remaining] == -1，表示没算过
        // dp[current][remaining] != -1，表示算过，保存的是产生的结果
        return process2(start, k, aim, n, dp);
    }

    /**
     * 计算机器人满足条件的走法有多少种，傻缓存法
     *
     * @param current   当前位置
     * @param remaining 剩余步数
     * @param aim       目标位置
     * @param n         格子数
     */
    private static int process2(int current, int remaining, int aim, int n, int[][] dp) {
        // 缓存表已经有值了，直接返回
        if (dp[current][remaining] != -1) {
            return dp[current][remaining];
        }

        // 之前没算过
        int answer;
        if (remaining == 0) {
            answer = current == aim ? 1 : 0;
        } else if (current == 1) {
            answer =  process2(2, remaining - 1, aim, n, dp);
        } else if (current == n) {
            answer =  process2(n - 1, remaining - 1, aim, n, dp);
        } else {
            answer =  process2(current - 1, remaining - 1, aim, n, dp)
                    + process2(current + 1, remaining - 1, aim, n, dp);
        }

        // 返回前更新缓存
        dp[current][remaining] = answer;
        return dp[current][remaining];
    }

    /**
     * 3.最终版的动态规划
     */
    public static int way3(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        // 机器人当前位置范围是1~n，剩余步数范围是0~k，dp表(n + 1)*(k + 1)肯定是能够将所有的情况都包含的
        int[][] dp = new int[n + 1][k + 1];
        // 剩余步数为0时，当前位置为aim时为1
        dp[aim][0] = 1;
        for (int remaining = 1; remaining <= k; remaining++) {
            // 第一行，依赖左下方的值
            dp[1][remaining] = dp[2][remaining - 1];
            // 第一行和第n行单独算后，此处就不用判断越界问题了
            for (int current = 2; current <= n - 1; current++) {
                // 非边上的行，依赖左下方和左上方的值
                dp[current][remaining] = dp[current - 1][remaining - 1] + dp[current + 1][remaining - 1];
            }
            // 第n行，依赖左上方的值
            dp[n][remaining] = dp[n - 1][remaining - 1];
        }
        return dp[start][k];
    }

    public static void main(String[] args) {
        // n=5，start=2，aim=4，k=6
        System.out.println("way1: " + way1(5, 2, 4, 6));
        System.out.println("way2: " + way2(5, 2, 4, 6));
        System.out.println("way3: " + way3(5, 2, 4, 6));
    }

}
