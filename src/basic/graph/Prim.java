package basic.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最小生成树算法-Prim算法
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Prim {

    public static Set<Edge> prim(Graph graph) {
        Collection<Node> nodes = graph.nodes.values();
        if (nodes.isEmpty()) {
            return null;
        }
        // 解锁的边按照权重值标准放到小根堆中
        PriorityQueue<Edge> smallEdgeQueue = new PriorityQueue<>((a, b) -> a.weight - b.weight);
        // 已经解锁的点
        Set<Node> nodeSet = new HashSet<>();
        Set<Edge> result = new HashSet<>();
        // 1.从任意节点出发来寻找最小生成树
        for (Node node : nodes) {
            if (!nodeSet.contains(node)) {
                // 点解锁
                nodeSet.add(node);
                // 2.此点连接的所有边解锁
                for (Edge edge : node.edges) {
                    smallEdgeQueue.offer(edge);
                }
                // 3.在所有解锁的边中选最小的边，然后看这个边加入到被选取的点中后会不会形成环
                while (!smallEdgeQueue.isEmpty()) {
                    // 从解锁的边中弹出最小的边
                    Edge currentEdge = smallEdgeQueue.poll();
                    Node toNode = currentEdge.to;
                    // 该边的指向点未解锁，则解锁
                    if (!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);
                        result.add(currentEdge);
                        // 指向点连接的所有边解锁
                        for (Edge edge : toNode.edges) {
                            smallEdgeQueue.offer(edge);
                        }
                    }
                    // 该边的指向点已经解锁，直接舍弃此边
                }

                // 为了防止森林，所以不break
                // 如果明确知道不会出现森林（或不需要防止森林），可以break
                // break;
            }
        }
        return result;
    }

}
