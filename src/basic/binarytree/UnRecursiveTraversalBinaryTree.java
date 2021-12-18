package basic.binarytree;

import java.util.Stack;

/**
 * 二叉树遍历非递归写法
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class UnRecursiveTraversalBinaryTree {

    public static class Node {
        public String value;
        public Node left;
        public Node right;

        public Node(String v) {
            value = v;
        }
    }

    /**
     * 非递归先序遍历（根左右）
     */
    public static void pre(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            // 压入当前节点
            stack.push(head);
            while (!stack.isEmpty()) {
                // 弹出栈顶元素
                Node current = stack.pop();
                System.out.print(current.value + " ");
                // 先压入右孩子，再压入左孩子
                if (current.right != null) {
                    stack.push(current.right);
                }
                if (current.left != null) {
                    stack.push(current.left);
                }
            }
        }
    }

    /**
     * 非递归中序遍历（左根右）
     */
    public static void in(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            Node current = head;
            while (!stack.isEmpty() || current != null) {
                if (current != null) {
                    // 将当前节点的整个左子树入栈
                    stack.push(current);
                    current = current.left;
                } else {
                    // 左子树入栈完后，弹出栈顶元素
                    Node pop = stack.pop();
                    System.out.print(pop.value + " ");
                    // 以当前弹出元素的右孩子为current节点，继续循环
                    current = pop.right;
                }
            }
        }
    }

    /**
     * 非递归后序遍历（左右根）
     */
    public static void pos(Node head) {
        if (head != null) {
            Stack<Node> stackA = new Stack<>();
            Stack<Node> stackB = new Stack<>();
            stackA.push(head);
            while (!stackA.isEmpty()) {
                // stackA出栈顺序是 根 右 左
                Node current = stackA.pop();
                // stackB入栈顺序是 根 右 左
                stackB.push(current);
                // stackA先左孩子入栈，再右孩子入栈
                if (current.left != null) {
                    stackA.push(current.left);
                }
                if (current.right != null) {
                    stackA.push(current.right);
                }
            }

            // stackB出栈顺序是 左 右 根
            while (!stackB.isEmpty()) {
                System.out.print(stackB.pop().value + " ");
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node("a");
        head.left = new Node("b");
        head.right = new Node("c");
        head.left.left = new Node("d");
        head.left.right = new Node("e");
        head.right.left = new Node("f");
        head.right.right = new Node("g");

        pre(head);
        System.out.println();
        in(head);
        System.out.println();
        pos(head);
        System.out.println();
    }

}
