package basic.binarytree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 给定一个二叉树，任何两个节点之间都存在距离，返回整个二叉树的最大距离
 * <p>
 * 两个节点的距离：表示从一个节点到另一个节点最短路径上的节点数（包含两个节点自己）
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class MaxDistance {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 递归套路解法
     */
    public static int maxDistance(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxDistance;
    }

    public static class Info {
        public int height;
        public int maxDistance;

        public Info(int height, int maxDistance) {
            this.height = height;
            this.maxDistance = maxDistance;
        }
    }

    public static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0);
        }

        // 获取左右子树的信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 拼凑处理自己的信息
        // 最大高度是 左右子树最大高度 + 1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 最大距离是 左子树最大距离、右子树最大距离、（左树高度 + 右树高度 + 1） 三个的最大值
        int maxDistance = Math.max((leftInfo.height + rightInfo.height + 1),
                Math.max(leftInfo.maxDistance, rightInfo.maxDistance));

        return new Info(height, maxDistance);
    }

    /**
     * 对数器方法
     */
    public static int comparator(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPrelist(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    public static ArrayList<Node> getPrelist(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }

    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 10;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBinaryTree(maxLevel, maxValue);
            if (maxDistance(head) != comparator(head)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    //------------------------------------ 辅助测试的方法 -----------------------------------

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
