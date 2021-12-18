package basic.binarytree;

/**
 * 二叉树遍历递归写法
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class RecursiveTraversalBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 二叉树：先序遍历。根-左-右
     *
     * 经典递归写法
     */
    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    /**
     * 二叉树：中序遍历。左-根-右
     *
     * 经典递归写法
     */
    public static void in(Node head) {
        if (head == null) {
            return;
        }
        in(head.left);
        System.out.println(head.value);
        in(head.right);
    }

    /**
     * 二叉树：后序遍历。左-右-根
     *
     * 经典递归写法
     */
    public static void pos(Node head) {
        if (head == null) {
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.value);
    }

    /**
     * 递归序
     */
    public static void f(Node head) {
        if (head == null) {
            return;
        }
        // 1
        System.out.println(head.value);

        f(head.left);

        //2
        System.out.println(head.value);

        f(head.right);

        // 3
        System.out.println(head.value);
    }

}
