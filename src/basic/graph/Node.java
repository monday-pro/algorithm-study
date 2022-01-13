package basic.graph;

import java.util.ArrayList;

/**
 * 自定义图的节点信息
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Node {
    /**
     * 节点值
     */
    public int value;

    /**
     * 入度
     */
    public int in;

    /**
     * 出度
     */
    public int out;

    /**
     * 从当前节点出发直接连接的节点
     */
    public ArrayList<Node> nexts;

    /**
     * 从当前节点出发直接连接的边
     */
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.nexts = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

}
