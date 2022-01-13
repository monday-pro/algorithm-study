package basic.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * 最小生成树算法-Kruskal算法
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Kruskal {

    public static class UnionFind {
        private HashMap<Node, Node> parents;
        private HashMap<Node, Integer> sizeMap;

        public UnionFind(Collection<Node> nodes) {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();

            for (Node n : nodes) {
                parents.put(n, n);
                sizeMap.put(n, 1);
            }
        }

        public Node findHead(Node node) {
            Node current = node;
            Stack<Node> stack = new Stack<>();
            while (current != parents.get(current)) {
                stack.push(current);
                current = parents.get(current);
            }
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), current);
            }
            return current;
        }

        public boolean isSameSet(Node a, Node b) {
            return findHead(a) == findHead(b);
        }

        public void union(Node a, Node b) {
            Node aHead = findHead(a);
            Node bHead = findHead(b);
            if (aHead != bHead) {
                int aSize = sizeMap.get(a);
                int bSize = sizeMap.get(b);
                Node big = aSize >= bSize ? aHead : bHead;
                Node small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }

    }

    public static Set<Edge> kruskal(Graph graph) {
        UnionFind unionFind = new UnionFind(graph.nodes.values());
        // 以权重值为标准的小根堆
        PriorityQueue<Edge> smallEdgeQueue = new PriorityQueue<>((a, b) -> a.weight - b.weight);
        // 小根堆放入所有的边
        for (Edge edge : graph.edges) {
            smallEdgeQueue.offer(edge);
        }
        Set<Edge> result = new HashSet<>();
        while (!smallEdgeQueue.isEmpty()) {
            Edge edge = smallEdgeQueue.poll();
            // 小根堆堆顶的边对应的节点不会形成环（即两个点不在同一个集合中），才往结果集中添加，否则舍弃
            if (!unionFind.isSameSet(edge.from, edge.to)) {
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }

}
