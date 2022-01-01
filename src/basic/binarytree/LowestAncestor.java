package basic.binarytree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个二叉树的头节点，和另外两个节点a、b，返回a、b的最低公共祖先。
 * <p>
 * 最低公共祖先：a、b往上找，第一个相同的祖先（这个公共祖先也可能是a或b自己）
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class LowestAncestor {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node lowestAncestor(Node head, Node a, Node b) {
        if (head == null) {
            return null;
        }
        return process(head, a, b).answer;
    }

    public static class Info {
        public boolean findA;
        public boolean findB;
        public Node answer;

        public Info(boolean findA, boolean findB, Node answer) {
            this.findA = findA;
            this.findB = findB;
            this.answer = answer;
        }
    }

    public static Info process(Node x, Node a, Node b) {
        if (x == null) {
            return new Info(false, false, null);
        }

        // 获取左右子树的信息
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);

        // 拼凑自己的信息
        // 不要忽略了自己是a或b的情况
        boolean findA = leftInfo.findA || rightInfo.findA || x == a;
        boolean findB = leftInfo.findB || rightInfo.findB || x == b;
        Node answer = null;
        if (leftInfo.answer != null) {
            // 左树中有答案，则此答案就是最终的答案
            answer = leftInfo.answer;
        } else if (rightInfo.answer != null) {
            // 右树中有答案，则此答案就是最终的答案
            answer = rightInfo.answer;
        } else {
            // 左树和右树都没有答案，但是找到了a和b，则答案就是当前节点X
            if (findA && findB) {
                answer = x;
            }
        }

        return new Info(findA, findB, answer);
    }

    public static Node comparator(Node head, Node a, Node b) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        Map<Node, Node> parentMap = new HashMap<>(16);
        parentMap.put(head, null);
        setParentMap(head, parentMap);

        Set<Node> aSet = new HashSet<>();
        aSet.add(a);
        Node current = a;
        while (current != null) {
            aSet.add(parentMap.get(current));
            current = parentMap.get(current);
        }

        current = b;
        while (!aSet.contains(current)) {
            current = parentMap.get(current);
        }

        return current;
    }

    private static void setParentMap(Node head, Map<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            setParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            setParentMap(head.right, parentMap);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 1000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBinaryTree(maxLevel, maxValue);
            Node a = getRandomOne(head);
            Node b = getRandomOne(head);
            if (lowestAncestor(head, a, b) != comparator(head, a, b)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    // ----------------------------------- 辅助测试的方法 ---------------------------------

    public static Node generateRandomBinaryTree(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.2) {
            return null;
        }
        Node head = new Node((int) ((maxValue + 1) * Math.random()));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static Node getRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        List<Node> list = new ArrayList<>();
        setBinaryTreeToList(head, list);
        int randomIndex = (int) (list.size() * Math.random());
        return list.get(randomIndex);
    }

    private static void setBinaryTreeToList(Node head, List<Node> list) {
        if (head == null) {
            return;
        }
        list.add(head);
        setBinaryTreeToList(head.left, list);
        setBinaryTreeToList(head.right, list);
    }

}
