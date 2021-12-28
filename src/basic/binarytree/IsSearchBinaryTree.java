package basic.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断是否是搜索二叉树
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class IsSearchBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            this.value = v;
        }
    }

    public static boolean isSearchBinaryTree(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isSearch;
    }

    public static class Info {
        public boolean isSearch;
        public int max;
        public int min;

        public Info(boolean isSearch, int max, int min) {
            this.isSearch = isSearch;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(Node x) {
        // 为空时，max和min无法赋值，所以此处返回null
        if (x == null) {
            return null;
        }

        // 获取左右子树的数据
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 拼凑自己的数据
        boolean isSearch = true;
        // 当前节点不是搜索二叉树的情况
        // 1.左子树不是搜索二叉树
        // 2.右子树不是搜索二叉树
        // 3.左子树最大值大于当前节点
        // 4.右子树最小值小于当前节点
        if (leftInfo != null && (!leftInfo.isSearch || leftInfo.max >= x.value)) {
            isSearch = false;
        }
        if (rightInfo != null && (!rightInfo.isSearch || rightInfo.min <= x.value)) {
            isSearch = false;
        }

        int max = x.value;
        int min = x.value;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }

        return new Info(isSearch, max, min);
    }

    /**
     * 比较器方法，二叉树的中序遍历
     */
    public static boolean comparator(Node head) {
        if (head == null) {
            return true;
        }

        List<Node> list = new ArrayList<>();
        // 获取中序遍历的结果
        in(head, list);
        // 判断值是否一直递增
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).value <= list.get(i - 1).value) {
                return false;
            }
        }

        return true;
    }

    private static void in(Node head, List<Node> list) {
        if (head == null) {
            return;
        }
        in(head.left, list);
        list.add(head);
        in(head.right, list);
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 1000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBinaryTree(maxLevel, maxValue);
            if (isSearchBinaryTree(head) != comparator(head)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    //------------------------------------- 辅助测试的方法 ----------------------------------

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
