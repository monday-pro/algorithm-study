package basic.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的宽度优先遍历
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class BFS {

    public static void bfs(Node node) {
        if (node == null) {
            return;
        }
        Node current = node;
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(current);
        set.add(current);

        while (!queue.isEmpty()) {
            current = queue.poll();
            System.out.println(current.value);

            for (Node next : current.nexts) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

}
