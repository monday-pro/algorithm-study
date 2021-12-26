package basic.binarytree;

/**
 * 判断是否是平衡二叉树
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class IsBalancedBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node (int v) {
            value = v;
        }
    }

    public static boolean isBalancedBinaryTree(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBalanced;
    }

    public static class Info{
        public boolean isBalanced;
        public int height;

        public Info(boolean b, int h) {
            this.isBalanced = b;
            this.height = h;
        }
    }

    /**
     * 判断以某个节点为头的子树是否是平衡二叉树
     */
    public static Info process(Node x) {
        if (x == null) {
            return new Info(true, 0);
        }

        // 拿到左右子树的信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 拼凑我自己的信息
        // 默认是平衡的
        boolean isBalanced = true;
        // 哪些情况会造成不平衡： 1.左树不平衡 2.右树不平衡 3.左右子树的高度差大于1
        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        // 左右子树中最大高度，加上到自己的高度
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        // 返回自己的信息
        return new Info(isBalanced, height);
    }

    /**
     * 比较器方法
     */
    public static boolean comparator(Node head) {
        boolean[] ans = {true};
        comparatorProcess(head, ans);
        return ans[0];
    }

    private static int comparatorProcess(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = comparatorProcess(head.left, ans);
        int rightHeight = comparatorProcess(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 100;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBinaryTree(maxLevel, maxValue);
            if (isBalancedBinaryTree(head) != comparator(head)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    //---------------------------------- 辅助测试的方法 --------------------------------

    public static Node generateRandomBinaryTree(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.2) {
            return null;
        }
        Node head = new Node((int) (Math.random() * (maxValue + 1)));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

}
