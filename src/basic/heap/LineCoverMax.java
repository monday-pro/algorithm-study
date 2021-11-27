package basic.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 最大线段重合问题
 * 给定很多线段，每个线段都有两个数[start, end]，表示线段开始位置和结束位置，左右都是闭区间。
 * 规定：
 * 1）线段的开始位置和结束位置一定都是整数
 * 2）两个线段重合：线段重合区域的长度必须>=1才表示线段重合
 * 返回线段最多重合区域中，包含了几条线段。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class LineCoverMax {

    /**
     * 1.查找所有线段的最小开始下标，最大结束下标
     * 2.从最小开始下标开始，统计以后每个0.5下标所包含的线段个数，最大的即为结果
     * <p>
     * 时间复杂度为O((max-min)*N)
     */
    public static int lineCoverMax(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        // 查找所有线段最小开始值，最大结束值
        for (int[] line : lines) {
            min = Math.min(min, line[0]);
            max = Math.max(max, line[1]);
        }
        // 重合区域线段最多的个数
        int cover = 0;
        // 从所有线段最小开始值开始，统计每个0.5下标所包含的线段个数
        // 比如：min = 5，max = 10，则统计：5.5、6.5、7.5、8.5、9.5
        for (double i = min + 0.5; i < max; i++) {
            // 当前穿过此点的线段数
            int cur = 0;
            for (int[] line : lines) {
                // 开始值小于此点，结束值大于此点，表明此线段穿过该点
                if (line[0] < i && line[1] > i) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    public static class Line {
        private int start;
        private int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 时间复杂度 O(N*logN)
     */
    public static int lineCoverMaxByHeap(int[][] n) {
        Line[] lines = new Line[n.length];
        for (int i = 0; i < n.length; i++) {
            lines[i] = new Line(n[i][0], n[i][1]);
        }
        // 将所有线段按照开始下标从小到大排序
        Arrays.sort(lines, (o1, o2) -> o1.start - o2.start);
        // 准备一个小根堆（存放线段的结束值）
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int cover = 0;
        for (Line line : lines) { // O(N)
            // 将小根堆中所有小于等于当前线段开始值的数据弹出
            while (!heap.isEmpty() && heap.peek() <= line.start) { // O(logN)
                heap.poll();
            }
            // 将当前线段的结束值放到小根堆中
            heap.add(line.end);
            cover = Math.max(cover, heap.size());
        }
        return cover;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 100;
        int l = 0;
        int r = 1000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateRandomLine(maxSize, l, r);
            int lineCoverMax = lineCoverMax(lines);
            int lineCoverMaxByHeap = lineCoverMaxByHeap(lines);
            if (lineCoverMax != lineCoverMaxByHeap) {
                System.out.println("error");
                System.out.println(lineCoverMax);
                System.out.println(lineCoverMaxByHeap);
                printLine(lines);
                break;
            }
        }
        System.out.println("finish");
    }

    //-------------------------------------- 辅助测试的方法 -----------------------------------------

    public static int[][] generateRandomLine(int maxSize, int l, int r) {
        int size = (int) ((maxSize + 1) * Math.random());
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = l + (int) ((r - l + 1) * Math.random());
            int b = l + (int) ((r - l + 1) * Math.random());
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void printLine(int[][] lines) {
        if (lines == null) {
            return;
        }
        for (int[] line : lines) {
            System.out.print("[" + line[0] + "," + line[1] + "] ");
        }
        System.out.println();
    }

}
