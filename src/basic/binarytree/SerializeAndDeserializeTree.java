package basic.binarytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树的序列化和反序列化
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class SerializeAndDeserializeTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 先序方式序列化
     */
    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pre(head, ans);
        return ans;
    }

    private static void pre(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pre(head.left, ans);
            pre(head.right, ans);
        }
    }

    /**
     * 先序方式反序列化
     */
    public static Node buildByPre(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        return preB(queue);
    }

    private static Node preB(Queue<String> queue) {
        String value = queue.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.left = preB(queue);
        head.right = preB(queue);
        return head;
    }

    /**
     * 后序方式序列化
     */
    public static Queue<String> posSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pos(head, ans);
        return ans;
    }

    public static void pos(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            pos(head.left, ans);
            pos(head.right, ans);
            ans.add(String.valueOf(head.value));
        }
    }

    /**
     * 后序方式反序列化
     */
    public static Node buildByPos(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        return posB(stack);
    }

    public static Node posB(Stack<String> posStack) {
        String value = posStack.pop();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.right = posB(posStack);
        head.left = posB(posStack);
        return head;
    }

    /**
     * 按层方式序列化
     */
    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.offer(null);
        } else {
            // 往结果队列放入头节点的值
            ans.add(String.valueOf(head.value));
            // 准备一个辅助队列
            Queue<Node> help = new LinkedList<>();
            // 辅助队列放入头节点
            help.offer(head);
            while (!help.isEmpty()) {
                Node current = help.poll();
                // 辅助队列头节点的左孩子不为空
                if (current.left != null) {
                    // 往结果队列放左孩子的值
                    ans.offer(String.valueOf(current.left.value));
                    // 往辅助队列放左孩子
                    help.offer(current.left);
                } else {
                    // 往结果队列放null，辅助队列不变
                    ans.offer(null);
                }

                // 右孩子同理
                if (current.right != null) {
                    ans.offer(String.valueOf(current.right.value));
                    help.offer(current.right);
                } else {
                    ans.offer(null);
                }
            }
        }
        return ans;
    }

    /**
     * 按层方式反序列化
     */
    public static Node buildByLevel(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        // 反序列化构建头节点
        Node head = generateNode(queue.poll());
        // 准备一个辅助队列
        Queue<Node> help = new LinkedList<>();
        // 防止队列里就只有一个null，如果队列里就只有一个null，则整个方法就直接结束了
        if (head != null) {
            // 辅助队列放入头节点
            help.offer(head);
        }
        Node current;
        while (!help.isEmpty()) {
            // 弹出辅助队列的第一个节点
            current = help.poll();
            // 反序列化构建左孩子
            current.left = generateNode(queue.poll());
            // 反序列化构建右孩子
            current.right = generateNode(queue.poll());

            if (current.left != null) {
                // 左孩子不为空，往辅助队列放入左孩子
                help.offer(current.left);
            }
            if (current.right != null) {
                // 右孩子不为空，往辅助队列放入右孩子
                help.offer(current.right);
            }
        }
        return head;
    }

    private static Node generateNode(String value) {
        if (value == null) {
            return null;
        }
        return new Node(Integer.parseInt(value));
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildByPre(pre);
            Node posBuild = buildByPos(pos);
            Node levelBuild = buildByLevel(level);
            if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("Error!");
                break;
            }
        }
        System.out.println("test finish!");
    }

    //-------------------------------- 辅助测试的方法 ---------------------------------

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
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
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

}
