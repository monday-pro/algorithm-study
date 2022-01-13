package basic.graph;

/**
 * 自定义图的边信息
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Edge {

    /**
     * 权重
     */
    public int weight;

    /**
     * 边的起始节点
     */
    public Node from;

    /**
     * 边的终止节点
     */
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

}
