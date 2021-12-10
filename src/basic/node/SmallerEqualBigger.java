package basic.node;

/**
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class SmallerEqualBigger {

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    public static Node smallerEqualBigger(Node head, int pivot) {
        // 小于区头节点
        Node sH = null;
        // 小于区尾节点
        Node sT = null;
        // 等于区头节点
        Node eH = null;
        // 等于区尾节点
        Node eT = null;
        // 大于区头节点
        Node bH = null;
        // 大于区尾节点
        Node bT = null;
        // 标记当前遍历的next节点
        Node next = null;

        // 将链表分成 小于区 等于区 大于区 三块
        while (head != null) {
            // 记录next节点
            next = head.next;
            // 当前节点next节点置空
            head.next = null;

            if (head.value < pivot) {
                // 小于区
                if (sH == null) {
                    // 如果是小于区的第一个节点，则小于区的头尾节点都指向当前节点
                    sH = head;
                    sT = head;
                } else {
                    // 如果不是小于区的第一个节点，则小于区的尾结点指向当前节点，调整尾结点
                    // 即在当前区内将各节点再次连接起来
                    sT.next = head;
                    sT = head;
                }
            } else if (head.value == pivot) {
                // 等于区
                if (eH == null) {
                    // 如果是等于区的第一个节点，则等于区的头尾节点都指向当前节点
                    eH = head;
                    eT = head;
                } else {
                    // 如果不是等于区的第一个节点，则等于区的尾结点指向当前节点，调整尾结点
                    eT.next = head;
                    eT = head;
                }
            } else {
                // 大于区
                if (bH == null) {
                    // 如果是大于区的第一个节点，则大于区的头尾节点都指向当前节点
                    bH = head;
                    bT = head;
                } else {
                    // 如果不是大于区的第一个节点，则大于区的尾结点指向当前节点，调整尾结点
                    bT.next = head;
                    bT = head;
                }
            }

            // 当前节点移动到next节点
            head = next;
        }

        // 将小于区 等于区 大于区 三块头尾连接起来
        // 小于区存在
        if (sT != null) {
            // 则小于区尾节点连接等于区头节点
            sT.next = eH;
            // 如果此时等于区尾结点为空（即整个等于区为空），那么等于区的尾结点则为小于区尾结点
            // 如果此时等于区尾结点不为空，那么等于区的尾结点就是之前的尾结点
            eT = eT == null ? sT : eT;
        }
        // 有等于区，eT 就是等于区的尾结点
        // 无等于区，有小于区，eT 就是小于区的尾结点
        // 无等于区，无小于区，eT 就是空
        if (eT != null) {
            // 则等于区的尾结点连接大于区头节点
            eT.next = bH;
        }

        // 最后返回当前的头节点
        // 有小于区，返回小于区头节点
        // 无小于区，有等于区，返回等于区头节点
        // 无小于区，无等于区，有大于区，返回大于区头节点
        return sH != null ? sH : (eH != null ? eH : bH);
    }

    public static Node comparator(Node head, int pivot) {
        if (head == null) {
            return null;
        }

        // 获取链表节点数
        Node current = head;
        int i = 0;
        while (current != null) {
            i++;
            current = current.next;
        }

        // 将链表放到数组中
        Node[] arr = new Node[i];
        current = head;
        for (i = 0; i < arr.length; i++) {
            arr[i] = current;
            current = current.next;
        }

        // 将数组分为小于区、等于区、大于区（核心就是荷兰国旗问题）
        partition(arr, pivot);

        // 将数组连成链表
        for (i = 1; i < arr.length; i++) {
            arr[i - 1].next = arr[i];
        }
        arr[i - 1].next = null;

        // 返回头节点
        return arr[0];
    }

    private static void partition(Node[] arr, int pivot) {
        int small = -1;
        int big = arr.length;
        int index = 0;
        while (index < big) {
            if (arr[index].value < pivot) {
                swap(arr, ++small, index++);
            } else if (arr[index].value == pivot) {
                index++;
            } else {
                swap(arr, --big, index);
            }
        }
    }

    private static void swap(Node[] arr, int i, int j) {
        Node tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        Node test = new Node(2);
        test.next = new Node(7);
        test.next.next = new Node(8);
        test.next.next.next = new Node(5);
        test.next.next.next.next = new Node(2);
        test.next.next.next.next.next = new Node(1);
        test.next.next.next.next.next.next = new Node(0);
        test.next.next.next.next.next.next.next = new Node(2);
        test.next.next.next.next.next.next.next.next = new Node(9);

        int pivot = 2;

        Node node1 = smallerEqualBigger(test, pivot);
        System.out.print("node1 is: ");
        printNodeArray(node1);

//        Node node2 = comparator(test, pivot);
//        System.out.print("node2 is: ");
//        printNodeArray(node2);

    }

    public static void printNodeArray(Node head) {
        while (head != null) {
            System.out.print(head.value + " --> ");
            head = head.next;
        }
        System.out.print("null");
        System.out.println();
    }

}
