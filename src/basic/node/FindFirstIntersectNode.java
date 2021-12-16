package basic.node;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
 *
 * 要求：如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class FindFirstIntersectNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    public static Node findFirstIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        // 两个链表都无环
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }

        // 两个链表都有环
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }

        return null;
    }

    /**
     * 找到单链表的入环节点，无环则返回null
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // 快指针
        Node fast = head.next.next;
        // 慢指针
        Node slow = head.next;
        while (fast != slow) {
            // 如果某次快指针走着走着变为空了，则此单链表必定无环
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }

        // 当快慢指针相遇时，快指针再次从头节点开始走
        fast = head;
        while (fast != slow) {
            // 此时就不需要再判断为空了，能走到这里则此单链表必定是有环的
            // 此时快指针一次走一个节点
            fast = fast.next;
            // 慢指针依然一次走一个节点
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 现在已经知道两个链表无环，找到它们的第一个相交节点
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        Node current1 = head1;
        Node current2 = head2;
        int n = 0;
        // 链表一遍历一遍得到长度、最后一个节点
        while (current1.next != null) {
            n++;
            current1 = current1.next;
        }

        // 链表二遍历一遍得到长度差、最后一个节点
        while (current2.next != null) {
            n--;
            current2 = current2.next;
        }

        // 如果两个链表的最后一个节点不等，则一定不相交
        if (current1 != current2) {
            return null;
        }

        // 长链表的头节点给current1
        current1 = n > 0 ? head1 : head2;
        // 短链表的头节点给current2
        current2 = current1 == head1 ? head2 : head1;

        n = Math.abs(n);
        // 长链表先走n个节点
        while (n != 0) {
            n--;
            current1 = current1.next;
        }

        // 两个链表挨个往下走，第一次相等的节点即是第一个相交的节点
        while (current1 != current2) {
            current1 = current1.next;
            current2 = current2.next;
        }

        return current1;
    }

    /**
     * 两个链表都有环
     * 分三种情况，各自有环但是不相交；入环节点是同一个；入环节点有两个
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node current1 = null;
        Node current2 = null;
        if (loop1 == loop2) {
            current1 = head1;
            current2 = head2;
            int n = 0;
            // 计算head1链表有多少个节点
            while (current1 != loop1) {
                n++;
                current1 = current1.next;
            }

            // 计算两个链表节点数的差值
            while (current2 != loop2) {
                n--;
                current2 = current2.next;
            }

            // 长链表的头节点给current1
            current1 = n > 0 ? head1 : head2;
            // 短链表的头节点给current2
            current2 = current1 == head1 ? head2 : head1;

            n = Math.abs(n);
            // 长链表先走n个节点
            while (n != 0) {
                current1 = current1.next;
                n--;
            }

            // 两个链表挨个往下走，第一次相等的节点即是第一个相交的节点
            while (current1 != current2) {
                current1 = current1.next;
                current2 = current2.next;
            }

            return current1;
        } else {
            // 两个链表的入环节点不等，则是各自有环但是不相交或者入环节点有两个
            current1 = loop1.next;
            // 从其中一个入环节点开始，遍历一圈，如果在回到自己时遇到了第二个入环节点，则是 入环节点有两个
            while (current1 != loop1) {
                if (current1 == loop2) {
                    // 此时两个入环节点都是第一个相交节点，返回其中一个即可
                    return loop1;
                }
                current1 = current1.next;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        // 0 -> 9 -> 8 -> 6 -> 7 -> null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(findFirstIntersectNode(head1, head2).value);

        // 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 4 -> 5 -> 6 -> 7 ...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4
        // 0 -> 9 -> 8 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 4 -> 5 -> 6 -> 7 ...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(findFirstIntersectNode(head1, head2).value);
    }

}
