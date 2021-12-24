package basic.binarytree;

/**
 * 查找后继节点
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class SuccessorNode {

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 查找后继节点
     */
    public static Node successorNode(Node node) {
        if (node == null) {
            return null;
        }

        // 有右孩子
        if (node.right != null) {
            // 找到右树上最左的孩子
            return getMaxLeft(node.right);
        } else {
            // 无右孩子
            // 根据parent指针往上找
            Node parent = node.parent;
            // 没有找到最顶上，且当前节点是父节点的右孩子则一直往上找
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            // parent包含两种情况
            // 1）找到最顶上了，即parent是null
            // 2）找到某个节点是父节点的左孩子了
            return parent;
        }
    }

    /**
     * 得到某个节点最左的孩子
     */
    private static Node getMaxLeft(Node right) {
        if (right == null) {
            return null;
        }
        while (right.left != null) {
            right = right.left;
        }
        return right;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;
        printTree(head);

        Node test = head.left.left;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + successorNode(test).value);
        test = head.right.right;
        System.out.println(test.value + " next: " + successorNode(test));
    }
    
    //----------------------------------- 辅助测试的方法 -------------------------------

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
