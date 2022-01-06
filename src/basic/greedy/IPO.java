package basic.greedy;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * LeetCode：502. IPO问题
 * 链接：https://leetcode-cn.com/problems/ipo
 *
 * 假设 力扣（LeetCode）即将开始 IPO 。为了以更高的价格将股票卖给风险投资公司，力扣希望在 IPO 之前开展一些项目以增加其资本。
 * 由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目（串行做项目）。帮助力扣设计完成最多 k 个不同项目后得到最大总资本的方式。
 *
 * 给你 n 个项目。对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。
 *
 * 最初，你的资本为 w 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。
 *
 * 总而言之，从给定项目中选择 最多 k 个不同项目的列表，以最大化最终资本 ，并输出最终可获得的最多资本。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class IPO {

    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // 准备一个小根堆
        Queue<Program> capitalSmallQueue = new PriorityQueue<>((a, b) -> a.capital - b.capital);
        // 以启动项目需要的资本为标准，将所有项目放到小根堆中
        for (int i = 0; i < profits.length; i++) {
            capitalSmallQueue.offer(new Program(profits[i], capital[i]));
        }

        // 准备一个大根堆
        // 放入的项目按项目的利润为标准
        Queue<Program> profitsBigQueue = new PriorityQueue<>((a, b) -> b.profit - a.profit);
        for (int i = 0; i < k; i++) {
            // 当前拥有的资本 >= 项目的启动资本，将项目从小根堆中弹出放到大根堆中
            while (!capitalSmallQueue.isEmpty() && w >= capitalSmallQueue.peek().capital) {
                profitsBigQueue.offer(capitalSmallQueue.poll());
            }

            // 可能资本不足，造成现在还能做项目但是大根堆没有项目给你做了
            if (profitsBigQueue.isEmpty()) {
                return w;
            }

            w += profitsBigQueue.poll().profit;
        }

        return w;
    }

    public static class Program {
        public int profit;
        public int capital;

        public Program(int profit, int capital) {
            this.profit = profit;
            this.capital = capital;
        }
    }

}