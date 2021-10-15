package basic.node;

/**
 * 单链表和双链表反转
 *
 * @author Java和算法学习--周一
 */
public class ReverseNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int value) {
            this.value = value;
        }
    }

    /**
     * a    ->   b    ->  c  ->  null
     * c    ->   b    ->  a  ->  null
     *
     * @return 返回头节点
     */
    public static Node reverseNode(Node head) {
        // 标记前一个节点
        Node pre = null;
        // 标记下一个节点
        Node next = null;
        while (head != null) {
            // 获取当前节点的下一关节点
            next = head.next;
            // 当前节点指向前一个节点
            head.next = pre;
            // 前一个节点修改为当前节点
            pre = head;
            // 当前节点后移
            head = next;
        }
        // 最后返回反转后的头节点
        return pre;
    }

    public static DoubleNode reverseDoubleNode(DoubleNode head) {
        // 标记前一个节点
        DoubleNode pre = null;
        // 标记后一个节点
        DoubleNode next = null;

        while (head != null) {
            // 获取当前节点的下一个节点
            next = head.next;
            // 当前节点的next指针指向前一个节点
            head.next = pre;
            // 当前节点的last指针指向后一个节点
            head.last = next;
            // 前一个节点移动到当前节点
            pre = head;
            // 当前节点移动到下一个节点
            head = next;
        }

        // 返回反转后的头节点
        return pre;
    }

}
