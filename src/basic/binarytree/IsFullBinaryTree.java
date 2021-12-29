package basic.binarytree;

/**
 * 判断二叉树是否是满二叉树
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class IsFullBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isFull(Node head) {
        if (head == null) {
            return true;
        }
        Info process = process(head);
        return (1 << process.height) - 1 == process.nodes;
    }

    public static class Info {
        public int height;
        public int nodes;

        public Info(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    public static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0);
        }

        // 获取左右子树的信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 拼凑自己的信息
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;

        return new Info(height, nodes);
    }

    /**
     * 对数器方法
     */
    public static boolean right(Node head) {
        if (head == null) {
            return true;
        }
        int height = height(head);
        int nodes = node(head);
        return (1 << height) - 1 == nodes;
    }

    private static int height(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(height(head.left), height(head.right)) + 1;
    }

    private static int node(Node head) {
        if (head == null) {
            return 0;
        }
        return node(head.left) + node(head.right) + 1;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 1000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomNode(maxLevel, maxValue);
            if (isFull(head) != right(head)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    //----------------------------------- 辅助测试的方法 --------------------------------

    public static Node generateRandomNode(int maxLevel, int maxValue) {
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

}
