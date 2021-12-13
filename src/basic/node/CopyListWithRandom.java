package basic.node;

import java.util.HashMap;

/**
 * LeetCode 138. 复制带随机指针的链表
 * https://leetcode-cn.com/problems/copy-list-with-random-pointer/
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class CopyListWithRandom {

    public static class Node {
        public int val;
        public Node next;
        public Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomListByMap(Node head) {
        // Key：老节点，Value：新节点
        HashMap<Node, Node> map = new HashMap<>();
        Node current = head;
        while (current != null) {
            // 构造老节点对应的新节点
            map.put(current, new Node(current.val));
            current = current.next;
        }

        current = head;
        while (current != null) {
            // 调整新节点的next指针
            map.get(current).next = map.get(current.next);
            // 调整新节点的random指针
            map.get(current).random = map.get(current.random);
            current = current.next;
        }

        return map.get(head);
    }

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        // 1.将新节点插入到老节点后面
        // 调整前：1 -> 2 -> 3 -> null，调整后：1 -> 1` -> 2 -> 2` -> 3 -> 3` -> null
        Node current = head;
        Node next = null;
        while (current != null) {
            // 标记原链表中当前节点的next节点
            next = current.next;
            // 在原链表当前节点的后面插入新节点
            current.next = new Node(current.val);
            // 调整新插入节点的next节点
            current.next.next = next;
            // 当前节点后移
            current = next;
        }

        // 2.调整新插入节点的random指针
        Node copy = null;
        current = head;
        while (current != null) {
            // 新节点
            copy = current.next;
            // 标记原链表中当前节点的next节点
            next = current.next.next;
            // 调整新节点的random节点
            copy.random = current.random != null ? current.random.next : null;
            // 当前节点后移
            current = next;
        }

        // 3.分离新老节点
        current = head;
        // 记录新链表的头节点
        Node res = head.next;
        while (current != null) {
            // 新节点
            copy = current.next;
            // 标记原链表中当前节点的next节点
            next = current.next.next;
            // 当前节点的next节点指向原链表中当前节点的next节点（即将老节点的引用调整回去）
            current.next = next;
            // 新节点的next节点指向 原链表中当前节点next节点 的next节点（即抽出新节点的引用）
            copy.next = next != null ? next.next : null;
            // 当前节点后移
            current = next;
        }

        // 返回新链表的头节点
        return res;
    }

}
