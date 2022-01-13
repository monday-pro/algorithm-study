package basic.graph;

import java.util.HashSet;
import java.util.Stack;

/**
 * 图的深度优先遍历
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class DFS {

    public static void dfs(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        Node current = node;
        stack.add(current);
        set.add(current);
        // 入栈就打印
        System.out.println(current.value);

        while (!stack.isEmpty()) {
            current = stack.pop();
            for (Node next : current.nexts) {
                if (!set.contains(next)) {
                    stack.add(current);
                    stack.add(next);
                    set.add(next);
                    System.out.println(next.value);
                    // 只入栈一个Set中不包含的节点
                    break;
                }
            }
        }
    }

}
