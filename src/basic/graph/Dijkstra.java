package basic.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Dijkstra算法（迪杰斯特拉算法）：Dijkstra算法用于处理有向无负权重的图
 *
 * 解决了图上带权的单源最短路径问题。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Dijkstra {

    public static Map<Node, Integer> dijkstra1(Node head) {
        // 从head到目标点的距离，key：目标点，value：距离
        Map<Node, Integer> distanceMap = new HashMap<>();
        // 自己到自己的最短距离肯定是0
        distanceMap.put(head, 0);
        // 已经锁定的点
        Set<Node> selectedNode = new HashSet<>();
        // 此时minNode肯定是head点
        Node minNode = getMinDistanceFromUnSelectedNode(distanceMap, selectedNode);
        while (minNode != null) {
            // 从head到minNode的最短距离
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    // toNode不存在，则从head到toNode此时的最短距离为 head到minNode的最短距离 + 此边的权重值
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    // toNode存在，则更新最短距离
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            // minNode使命完成（minNode所有直接到的点都遍历完成）
            selectedNode.add(minNode);
            // 重新找出下一个minNode
            minNode = getMinDistanceFromUnSelectedNode(distanceMap, selectedNode);
        }
        return distanceMap;
    }

    /**
     * 从Map中找出到未锁定目标点距离最短的点
     *
     * @param distanceMap  到目标点的距离
     * @param selectedNode 已经锁定的点
     * @return 最短距离的点
     */
    private static Node getMinDistanceFromUnSelectedNode(Map<Node, Integer> distanceMap, Set<Node> selectedNode) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selectedNode.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    // ---------------- 第二种：使用加强堆来优化 从Map中找出到未锁定目标点距离最短的点 ------------------

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        /**
         * 存放Node的堆
         */
        public Node[] nodes;

        /**
         * 反向索引表，Node在堆中的位置
         */
        public HashMap<Node, Integer> heapIndexMap;

        /**
         * 从指定点到目标点目前最小的距离
         */
        public HashMap<Node, Integer> distanceMap;

        /**
         * 堆大小
         */
        public int heapSize;

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        /**
         * 弹出堆顶元素
         */
        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            // 将0位置的数和最后位置的数交换
            swap(0, heapSize - 1);
            // 将反向索引表中最后位置的数置为-1，表示该点曾经进来过，只是目前被弹出了
            heapIndexMap.put(nodes[heapSize - 1], -1);
            // 距离表中可以删除此数据了
            distanceMap.remove(nodes[heapSize - 1]);
            // 堆中同理删除数据
            nodes[--heapSize] = null;
            // 重新调整堆
            heapify(0);
            return nodeRecord;
        }

        /**
         * 将i位置的数向下调整保持正常的小根堆结构
         */
        private void heapify(int index) {
            int left = 2 * index + 1;
            while (left < heapSize) {
                // 左右子树中找到更小的
                int smaller = left + 1 < heapSize && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1 : left;
                // 将左右子树中更小的和index位置的数比较，找到最小的
                int smallest = distanceMap.get(nodes[smaller]) < distanceMap.get(nodes[index]) ? smaller : index;
                // index位置的就是距离最短的，则当前就是小根堆结构
                if (index == smallest) {
                    break;
                }
                // 否则，交换index到左右子树中最小的位置上
                swap(smallest, index);
                // 进行下一次循环
                index = smallest;
                left = 2 * index + 1;
            }
        }

        /**
         * 将index位置的数向上调整，保存正常的小根堆结构
         */
        private void heapInsert(int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean isInHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int i, int j) {
            Node iNode = nodes[i];
            Node jNode = nodes[j];
            nodes[i] = jNode;
            nodes[j] = iNode;
            heapIndexMap.put(iNode, j);
            heapIndexMap.put(jNode, i);
        }

        /**
         * 有一个点node，现在发现了一个从源节点出发到达node的距离为distance
         * 判断要不要更新
         */
        public void addOrUpdateOrIgnore(Node node, int distance) {
            // node已经在堆中，update
            if (isInHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                // node位置的距离变小了，所以将node位置上移保持小根堆结构
                heapInsert(heapIndexMap.get(node));
            } else if (!isEntered(node)) {
                // node没有进来过，add
                nodes[heapSize] = node;
                heapIndexMap.put(node, heapSize);
                distanceMap.put(node, distance);
                // 从最后位置往上移
                heapInsert(heapSize++);
            }
            // 进来过，但是被弹出了，忽略
        }

    }

    /**
     * 改进后的dijkstra算法
     * <p>
     * 从head出发，所有head能到达的节点，生成到达每个节点的最短路径记录并返回
     */
    public static Map<Node, Integer> dijkstra1(Node head, int size) {
        // 使用加强堆进行优化
        NodeHeap nodeHeap = new NodeHeap(size);
        // head到head的距离肯定是0
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        Map<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node node = record.node;
            int distance = record.distance;
            for (Edge edge : node.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(node, distance);
        }
        return result;
    }

}
