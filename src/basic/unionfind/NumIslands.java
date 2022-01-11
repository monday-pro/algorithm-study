package basic.unionfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 岛屿数量，LeetCode200： https://leetcode-cn.com/problems/number-of-islands/
 * <p>
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * <p>
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和竖直方向上相邻的陆地连接形成。
 * <p>
 * 此外，你可以假设该网格的四条边均被水包围。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class NumIslands {

    /**
     * 1、使用染色方法
     * <p>
     * 时间复杂度O(m*n)
     */
    public static int numIslands(char[][] grid) {
        int result = 0;
        // 挨个遍历整个二维数组
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 此时位置上是‘1’，则修改周围一片的‘1’，同时岛的数量加1
                if (grid[i][j] == '1') {
                    result++;
                    infect(grid, i, j);
                }
            }
        }
        return result;
    }

    /**
     * 从（i，j）位置开始，将周围连成一片的‘1’字符修改为2的ASCII
     */
    private static void infect(char[][] grid, int i, int j) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = 2;
        infect(grid, i - 1, j);
        infect(grid, i + 1, j);
        infect(grid, i, j - 1);
        infect(grid, i, j + 1);
    }

    /**
     * 2、使用最原始的并查集
     */
    public static int numIslands1(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        // 为了区分不同的'1'，使用Dot包一层
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    // dots非空表示陆地，空表示水
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind1<Dot> uf = new UnionFind1<>(dotList);
        // 单独合并第一行
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }
        // 单独合并第一列
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(dots[i - 1][0], dots[i][0]);
            }
        }
        // 经过前面两个循环后，就不需要判断是否越界了
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    // 和上边的‘1’合并
                    if (board[i][j - 1] == '1') {
                        uf.union(dots[i][j - 1], dots[i][j]);
                    }
                    // 和左边的‘1’合并
                    if (board[i - 1][j] == '1') {
                        uf.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        // 最后集合的大小就是岛的数量
        return uf.size();
    }

    public static class Dot {
    }

    public static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    /**
     * 最原始的并查集
     */
    public static class UnionFind1<V> {
        // 用户输入的V对应内部的Node<V>
        public HashMap<V, Node<V>> nodes;
        // Node<V>的父亲是谁
        public HashMap<Node<V>, Node<V>> parents;
        // Node<V>所在集合的大小（只有集合的代表节点<可以理解为头节点>才会放到sizeMap中）
        public HashMap<Node<V>, Integer> sizeMap;

        // 初始化时把用户给定的数据全部放到各个Map中
        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V current : values) {
                Node<V> node = new Node<>(current);
                nodes.put(current, node);
                // 初始化时node的父亲是自己
                parents.put(node, node);
                // 初始化时node的size是1
                sizeMap.put(node, 1);
            }
        }

        /**
         * 找到指定节点所在的代表节点
         */
        public Node<V> findHead(Node<V> node) {
            Node<V> current = node;
            Stack<Node<V>> stack = new Stack<>();
            // 当前节点的父节点不是自己，说明还没找到最顶
            while (current != parents.get(current)) {
                stack.push(current);
                current = parents.get(current);
            }

            // 优化：修改查找路径上的所有节点，将它们都指向根结点
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), current);
            }

            return current;
        }

        /**
         * 将两个节点所在集合合并为一个集合
         */
        public void union(V a, V b) {
            Node<V> aHead = findHead(nodes.get(a));
            Node<V> bHead = findHead(nodes.get(b));
            if (aHead != bHead) { // 说明a、b所在集合不是同一个集合
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                // 找到size更大的集合
                Node<V> big = aSize >= bSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                // 小的连到大的上面（这样是一个优化）
                parents.put(small, big);
                // 重新调整big所在集合的size
                sizeMap.put(big, aSize + bSize);
                // small所在集合已经连到big上，从sizeMap中移除
                sizeMap.remove(small);
            }
        }

        /**
         * 当前集合数量
         */
        public int size() {
            return sizeMap.size();
        }

    }

    /**
     * 3、使用优化后的并查集
     * 同时将原来（i，j）位置的数映射到数组（i * 列数 + j）的位置上
     */
    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;

        UnionFind2 uf2 = new UnionFind2(board);

        // 合并第一行
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf2.union(i - 1, 0, i, 0);
            }
        }
        // 合并第一列
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                uf2.union(0, j - 1, 0, j);
            }
        }
        // 合并其他行列数据
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    // 合并左边的
                    if (board[i - 1][j] == '1') {
                        uf2.union(i, j, i - 1, j);
                    }
                    // 合并上边的
                    if (board[i][j - 1] == '1') {
                        uf2.union(i, j, i, j - 1);
                    }
                }
            }
        }

        return uf2.set;
    }

    public static class UnionFind2 {
        // parent[i] = k，表示i的父亲是k
        public int[] parent;
        // size[i] = k，i为代表节点size[i]才有意义，否则无意义
        // 表示i所在集合大小是k
        public int[] size;
        // 辅助数组：用于路径优化时临时存储数据
        public int[] help;
        // 表示二维矩阵的列数
        public int column;
        // 一共有多少集合
        public int set;

        public UnionFind2(char[][] board) {
            int row = board.length;
            column = board[0].length;
            int length = row * column;
            parent = new int[length];
            size = new int[length];
            help = new int[length];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (board[i][j] == '1') {
                        // 将二维数组映射到一维数组上
                        int index = getIndex(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        set++;
                    }
                }
            }
        }

        /**
         * 根据二维数组的行和列计算应该映射到的一维数组的位置
         *
         * @param i 行
         * @param j 列
         */
        public int getIndex(int i, int j) {
            return i * column + j;
        }

        /**
         * 查找i位置所在集合的代表节点
         */
        public int findHead(int i) {
            // 路径优化辅助数组的下标
            int helpIndex = 0;
            while (i != parent[i]) {
                help[helpIndex++] = i;
                i = parent[i];
            }

            while (--helpIndex >= 0) {
                parent[help[helpIndex]] = i;
            }

            return i;
        }

        /**
         * 将原始二维矩阵中(r1, c1)和(r2, c2)所在位置映射到一维数组i，j位置
         * 将i和j所在的集合合并
         */
        public void union(int r1, int c1, int r2, int c2) {
            int i = getIndex(r1, c1);
            int j = getIndex(r2, c2);
            int iHead = findHead(i);
            int jHead = findHead(j);
            if (iHead != jHead) {
                // 小头连到大头上面
                if (size[iHead] >= size[jHead]) {
                    parent[jHead] = iHead;
                    // 代表节点大小修改
                    size[iHead] += size[jHead];
                } else {
                    parent[iHead] = jHead;
                    size[jHead] += size[iHead];
                }
                // 将两个集合合并后，总共的集合数量减少一个
                set--;
            }
        }

        public int getSet() {
            return set;
        }
    }

    public static void main(String[] args) {
        int row = 2000;
        int column = 1000;
        char[][] board1 = generateRandomBoard(row, column);
        char[][] board2 = copyBoard(board1);
        char[][] board3 = copyBoard(board1);

        long start;
        int result;
        long end;

        System.out.println("矩阵大小： " + row + " * " + column);

        start = System.currentTimeMillis();
        result = numIslands(board1);
        end = System.currentTimeMillis();
        System.out.println("染色方法， result： " + result + "， 时间： " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = numIslands1(board2);
        end = System.currentTimeMillis();
        System.out.println("原始并查集， result： " + result + "， 时间： " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = numIslands2(board3);
        end = System.currentTimeMillis();
        System.out.println("改进并查集， result： " + result + "， 时间： " + (end - start) + "ms");
    }

    // -------------------------------- 辅助测试的方法 --------------------------------

    public static char[][] generateRandomBoard(int row, int column) {
        char[][] result = new char[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return result;
    }

    public static char[][] copyBoard(char[][] board) {
        int row = board.length;
        int column = board[0].length;
        char[][] result = new char[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result[i][j] = board[i][j];
            }
        }
        return result;
    }

}
