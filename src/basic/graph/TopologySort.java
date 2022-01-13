package basic.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 图的拓扑排序
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class TopologySort {

    public static List<Node> topologySort(Graph graph) {
        // Key：节点，Value：剩余入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 存放入度为0的节点
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            // 将最初给定的图中所有节点放到inMap中
            inMap.put(node, node.in);
            if (node.in == 0) {
                // 最初入度为0的节点
                zeroInQueue.add(node);
            }
        }

        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node current = zeroInQueue.poll();
            result.add(current);
            for (Node next : current.nexts) {
                // 将已经遍历过的节点的邻居节点的入度减一（可以理解为从图中移除current节点）
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    // 修改后的节点入度为0，放到zeroInQueue队列中
                    zeroInQueue.add(next);
                }
            }
        }

        return result;
    }

}
