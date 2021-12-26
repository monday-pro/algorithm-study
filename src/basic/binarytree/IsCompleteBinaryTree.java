package basic.binarytree;

import java.util.LinkedList;

/**
 * 判断是否是完全二叉树
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class IsCompleteBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 经典解法
     */
    public static boolean isCompleteBinaryTree1(Node head) {
        if (head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 标记后续节点是否是叶节点，即当前是否遇到了左右不双全的节点
        boolean leaf = false;
        queue.add(head);
        Node left = null;
        Node right = null;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            left = current.left;
            right = current.right;
            // 有右无左非完全二叉树
            // 当遇到左右不双全的节点时，后续的节点必须是叶节点，否则也不是完全二叉树
            if ((left == null && right != null) || (leaf && (left != null || right != null))) {
                return false;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
            // 当遇到左右不双全的节点时，修改是否为叶节点标识
            if (left == null || right == null) {
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 递归套路解法
     */
    public static boolean isCompleteBinaryTree2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isComplete;
    }

    public static class Info {
        public boolean isFull;
        public boolean isComplete;
        public int height;

        public Info(boolean isFull, boolean isComplete, int height) {
            this.isFull = isFull;
            this.isComplete = isComplete;
            this.height = height;
        }
    }

    public static Info process(Node x) {
        if (x == null) {
            return new Info(true, true, 0);
        }

        // 获取左右子树
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 拼凑自己的信息
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isComplete = false;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            // 左满，右满，高度相等
            isComplete = true;
        } else if (leftInfo.isComplete && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            // 左完全，右满，高度差1
            isComplete = true;
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            // 左满、右满，高度差为1
            isComplete = true;
        } else if (leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height) {
            // 左满、右完全，高度相等
            isComplete = true;
        }
        // 左右子树高度最大的加上自己的高度1，即是此节点的高度
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        return new Info(isFull, isComplete, height);
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 1000;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBinaryTree(maxLevel, maxValue);
            if (isCompleteBinaryTree1(head) != isCompleteBinaryTree2(head)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    //--------------------------------- 辅助测试的的方法 --------------------------------

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
