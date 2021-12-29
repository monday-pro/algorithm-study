package basic.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 求一个二叉树中节点数最多的子搜索二叉树的节点数。
 *
 * 给定的二叉树整体可能不是搜索二叉树，但是它的某几个子树是搜索二叉树，
 * 要找到节点数最多的子搜索二叉树的节点数
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class MaxSubSearchBinaryTreeSize {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxSubSearchBinaryTreeSize(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxSubSize;
    }

    public static class Info {
        // 最大满足搜索二叉树条件的子树大小
        public int maxSubSize;

        public int max;

        public int min;

        // 整个子树的节点数
        public int allSize;

        public Info(int maxSubSize, int max, int min, int allSize) {
            this.maxSubSize = maxSubSize;
            this.max = max;
            this.min = min;
            this.allSize = allSize;
        }
    }

    public static Info process(Node x) {
        if (x == null) {
            return null;
        }

        // 获取左右子树信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 拼凑自己的信息
        int max = x.value;
        int min = x.value;
        int allSize = 1;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
            allSize += leftInfo.allSize;
        }
        if ((rightInfo != null)) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
            allSize += rightInfo.allSize;
        }

        // 左树 最大搜索二叉树大小
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxSubSize;
        }

        // 右树 最大搜索二叉树大小
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxSubSize;
        }

        // 最大子树包含头节点
        int p3 = -1;
        // 左树是否是搜索二叉树
        boolean leftSearch = leftInfo == null || leftInfo.maxSubSize == leftInfo.allSize;
        // 右树是否是搜索二叉树
        boolean rightSearch = rightInfo == null || rightInfo.maxSubSize == rightInfo.allSize;
        if (leftSearch && rightSearch) {
            // 左树最大值是否比当前节点值小（空也认为比当前节点小）
            boolean lessMaxLessX = leftInfo == null || leftInfo.max < x.value;
            // 右树最小值是否比当前节点值大（空也认为比当前节点大）
            boolean rightMinMoreX = rightInfo == null || rightInfo.min > x.value;

            // 都满足，才能修改p3的值
            if (lessMaxLessX && rightMinMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }

        // 最后修改，当前子树最大搜索二叉子树的大小
        int maxSubSize = Math.max(p1, Math.max(p2, p3));

        return new Info(maxSubSize, max, min, allSize);
    }

    /**
     * 对数器方法
     */
    public static int right(Node head) {
        if (head == null) {
            return 0;
        }
        int node = getSearchSize(head);
        if (node != 0) {
            return node;
        }
        return Math.max(right(head.left), right(head.right));
    }

    private static int getSearchSize(Node head) {
        if (head == null) {
            return 0;
        }
        List<Node> array = new ArrayList<>();
        in(head, array);
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i).value <= array.get(i - 1).value) {
                return 0;
            }
        }
        return array.size();
    }

    private static void in(Node head, List<Node> array) {
        if (head == null) {
            return;
        }
        in(head.left, array);
        array.add(head);
        in(head.right, array);
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 1000;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomNodeArray(maxLevel, maxValue);
            if (maxSubSearchBinaryTreeSize(head) != right(head)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    // --------------------------------- 辅助测试的的方法 --------------------------------

    public static Node generateRandomNodeArray(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.2) {
            return null;
        }
        Node head = new Node((int) ((maxValue + 1) * Math.random()));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

}
