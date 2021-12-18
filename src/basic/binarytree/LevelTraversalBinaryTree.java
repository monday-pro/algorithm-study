package basic.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树按层遍历
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class LevelTraversalBinaryTree {

    public static class Node{
        public String value;
        public Node left;
        public Node right;

        public Node(String v) {
            value = v;
        }
    }

    /**
     * 二叉树按层遍历
     */
    public static void levelTraversal(Node head) {
        if (head == null) {
            return;
        }
        // 准备一个队列
        Queue<Node> queue = new LinkedList<>();
        // 头节点入队
        queue.offer(head);
        while (!queue.isEmpty()) {
            // 从队列中弹出一个节点
            Node current = queue.poll();
            // 打印
            System.out.print(current.value + " ");
            // 有左孩子则左孩子入队
            if (current.left != null) {
                queue.offer(current.left);
            }
            // 有右孩子则右孩子入队
            if (current.right != null) {
                queue.offer(current.right);
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

        levelTraversal(head);
    }

}
