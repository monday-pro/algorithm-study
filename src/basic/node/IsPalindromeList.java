package basic.node;

import java.util.Stack;

/**
 * 判断链表是不是回文结构
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class IsPalindromeList {

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 额外空间复杂度为O(1)
     *
     * 全程只使用了n1、n2、n3三个有限变量
     */
    public static boolean isPalindromeList(Node head) {
        // 只有一个节点的链表肯定是回文结构
        if (head == null || head.next == null) {
            return true;
        }

        // 1.找到此链表的中点（偶数个节点则找上中点）
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next;
            n2 = n2.next.next;
        }
        // 此时n1即为中点（偶数个节点则为上中点）

        // 2.调整链表结构
        // 例如： 1 --> 2 --> 3 --> 3 --> 2 --> 1，调整为： 1 --> 2 --> 3 <-- 3 <-- 2 <-- 1，上中点3的next节点为null
        // n2修改为右半部分的第一个节点
        n2 = n1.next;
        // 中点（或上中点）的next节点置为null
        n1.next = null;
        Node n3 = null;
        while (n2 != null) {
            // 记录此时右半部分节点的next节点
            n3 = n2.next;
            // 将n2的next节点指向前一个节点
            n2.next = n1;
            // 调整n1，即n1后移一个节点
            n1 = n2;
            // 调整n2，即n2到原本n2的后一个节点
            n2 = n3;
        }
        // 此时n1为链表最后一个节点，n2和n3都为null

        // 3.判断是否为回文结构
        // 记录链表最后一个节点，判断完是否为回文结构后将链表调整回去时需要
        n3 = n1;
        // n2从左边第一个节点开始，n1从最后一个节点开始
        n2 = head;
        // 记录是否为回文结构
        boolean res = true;
        while (n1 != null && n2 != null) {
            if (n1.value != n2.value) {
                res = false;
                // 此处不能直接返回是否为回文结构，还需要将链表调整为原始结构
                break;
            }
            // n1和n2都往中间移动
            n1 = n1.next;
            n2 = n2.next;
        }

        // 4.将链表调整为原来的结构
        n1 = n3.next;
        // 链表最后一个节点指向null
        n3.next = null;
        while (n1 != null) {
            // 记录next节点
            n2 = n1.next;
            // n1的next节点指向后一个节点（即将链表调整为原来的结构）
            n1.next = n3;
            // n3前移
            n3 = n1;
            // n1前移
            n1 = n2;
        }

        // 调整完后才返回最后的结果
        return res;
    }

    /**
     * 采用栈来作为对数器。
     *
     * 额外空间复杂度为 O(N)
     */
    public static boolean comparator(Node head) {
        Stack<Node> stack = new Stack<>();
        Node current = head;
        while (current != null) {
            stack.push(current);
            current = current.next;
        }

        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static void main(String[] args) {
        Node head = null;
        printNode(head);
        System.out.println("isPalindromeList: " + isPalindromeList(head));
        System.out.println("Comparator: " + comparator(head));
        printNode(head);
        System.out.println("=========================");

        head = new Node(1);
        printNode(head);
        System.out.println("isPalindromeList: " + isPalindromeList(head));
        System.out.println("Comparator: " + comparator(head));
        printNode(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printNode(head);
        System.out.println("isPalindromeList: " + isPalindromeList(head));
        System.out.println("Comparator: " + comparator(head));
        printNode(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printNode(head);
        System.out.println("isPalindromeList: " + isPalindromeList(head));
        System.out.println("Comparator: " + comparator(head));
        printNode(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printNode(head);
        System.out.println("isPalindromeList: " + isPalindromeList(head));
        System.out.println("Comparator: " + comparator(head));
        printNode(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printNode(head);
        System.out.println("isPalindromeList: " + isPalindromeList(head));
        System.out.println("Comparator: " + comparator(head));
        printNode(head);
        System.out.println("=========================");

    }

    public static void printNode(Node head) {
        System.out.print("Linked is: ");
        while (head != null) {
            System.out.print(head.value + " --> ");
            head = head.next;
        }
        System.out.print("null");
        System.out.println();
    }

}
