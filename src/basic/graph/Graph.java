package basic.graph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 自定义图的信息
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Graph {

    /**
     * 点集，Key：用户给的点，Value：自定义点信息
     */
    public HashMap<Integer, Node> nodes;

    /**
     * 边集
     */
    public HashSet<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }

    /**
     * 将用户输入的表示边的 N*3 的矩阵转换为自定义的图
     *
     * @param matrix N*3 的矩阵，[3, 0, 5], [2, 2, 5]
     */
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int[] m : matrix) {
            // 拿到用户给的边的权重信息、边的from、to节点
            int weight = m[0];
            int from = m[1];
            int to = m[2];

            // 添加图的点集信息
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }

            // 根据点生成边的信息
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge edge = new Edge(weight, fromNode, toNode);

            // 节点信息处理
            // 添加 从当前节点出发直接连接的节点、从当前节点出发直接连接的边
            fromNode.nexts.add(toNode);
            fromNode.edges.add(edge);
            // 入度、出度修改
            fromNode.out++;
            toNode.in++;

            // 添加图的边集信息
            graph.edges.add(edge);
        }
        return graph;
    }

}
