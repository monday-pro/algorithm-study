package basic.binarytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 求二叉树最宽的层有多少个节点
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class TreeMaxWidth {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static int treeMaxWidth(Node head) {
        if (head == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        Node currentLevelEnd = head;
        Node nextLevelEnd = null;
        int currentWidth = 0;
        int maxWidth = 0;

        queue.offer(head);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            // 每次从队列中弹出节点时，当前层的宽度都加一
            currentWidth++;

            // 有孩子节点入队时就修改下一层的结束节点
            if (current.left != null) {
                queue.offer(current.left);
                nextLevelEnd = current.left;
            }
            if (current.right != null) {
                queue.offer(current.right);
                nextLevelEnd = current.right;
            }

            // 如果当前出队的节点是当前层的结束节点
            if (current == currentLevelEnd) {
                // 修改最大宽度
                maxWidth = Math.max(maxWidth, currentWidth);
                // 当前层宽度重置
                currentWidth = 0;
                // 当前层的结束节点修改，表明下一次循环时开始统计下一层的宽度
                currentLevelEnd = nextLevelEnd;
                // 下一层结束节点重置
                nextLevelEnd = null;
            }
        }

        return maxWidth;
    }

    /**
     * 对数器方法
     */
    public static int treeMaxWidth1(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // key：节点，value：节点在哪一层
        HashMap<Node, Integer> levelMap = new HashMap<>(16);
        levelMap.put(head, 1);
        // 当前来到哪一层
        int curLevel = 1;
        // 当前层的宽度
        int curLevelWidth = 0;
        // 最大宽度
        int max = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            // 当前节点还是在当前层
            if (curNodeLevel == curLevel) {
                curLevelWidth++;
            } else {
                // 当前层已经遍历完毕
                max = Math.max(max, curLevelWidth);
                // 来到下一层
                curLevel++;
                // 当前节点已经是下一层的了，也就是下一层已经有一个节点了，宽度自然是1
                curLevelWidth = 1;
            }
        }
        // 最后一层的宽度没有和max比较，所以这里要再比较一次
        max = Math.max(max, curLevelWidth);
        return max;
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 100;
        int testTimes = 10000;
        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateTree(maxLevel, maxValue);
            int treeMaxWidth = treeMaxWidth(head);
            int comparator = treeMaxWidth1(head);
            if (comparator != treeMaxWidth) {
                isSuccess = false;
                printTree(head);
                break;
            }
        }

        System.out.println(isSuccess ? "Success" : "Error");
    }

    //--------------------------------- 辅助测试的方法 ---------------------------------

    public static Node generateTree(int maxLevel, int maxValue) {
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

    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            stringBuffer.append(space);
        }
        return stringBuffer.toString();
    }

}
