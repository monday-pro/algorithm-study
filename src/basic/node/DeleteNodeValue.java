package basic.node;

/**
 * 单链表删除指定的值
 *
 * @author Java和算法学习--周一
 */
public class DeleteNodeValue {

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node removeValue(Node head, int num) {
        // 找到第一个不等于删除值的节点
        while (head != null) {
            if (head.value != num) {
                // 找到了
                break;
            }
            // 没找到，则一直往后找
            head = head.next;
        }

        // 标记前一个节点
        Node pre = head;
        // 标记当前节点
        Node cur = head;
        while (cur != null) {
            // 当前节点的值是否要删除的值
            if (cur.value == num) {
                // 是，则前一个节点指向当前节点的后一个节点，即删除当前节点
                pre.next = cur.next;
            } else {
                // 否，前节点后移到当前节点
                pre = cur;
            }
            // 不管如何，循环一次，当前节点都后移
            cur = cur.next;
        }

        // 返回头节点
        return head;
    }

}
