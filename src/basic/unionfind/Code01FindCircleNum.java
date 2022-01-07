package basic.unionfind;

/**
 * LeetCode547：https://leetcode-cn.com/problems/number-of-provinces/
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Code01FindCircleNum {

    public int findCircleNum(int[][] isConnected) {
        int length = isConnected.length;
        UnionFind unionFind = new UnionFind(length);
        // 因为整个 n*n 的二维矩阵是关于对角线对称的，而自己和自己是相连的，即对角线都是1，所以只需遍历一侧即可
        // 我们遍历的是右上方的数据
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (isConnected[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.getSet();
    }

    public static class UnionFind {
        // parent[i] = k，表示i的父亲是k
        private int[] parent;
        // size[i] = k，i为代表节点size[i]才有意义，否则无意义
        // 当然也可以在小连到大时（即union时），将小集合的size置为无效标识，由于不影响此题，顾没做处理
        // 表示i所在集合大小是k
        private int[] size;
        // 辅助数组：用于路径优化时临时存储数据
        private int[] help;
        // 一共有多少集合
        private int set;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            set = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        /**
         * 找到i所在集合的代表节点
         */
        private int findHead(int i) {
            int helpIndex = 0;
            // 父节点等于自己表示此时就是代表节点
            while (i != parent[i]) {
                help[helpIndex++] = i;
                // 一直往上找
                i = parent[i];
            }

            // 优化：路径压缩
            while (--helpIndex >= 0) {
                // 此路径上所有节点都连到父节点上
                parent[help[helpIndex]] = i;
            }

            return i;
        }

        public void union(int i, int j) {
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

}
