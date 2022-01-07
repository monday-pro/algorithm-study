package basic.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class TheUnionFind {

    public static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    public static class UnionFind<V> {
        // 用户输入的V对应内部的Node<V>
        public HashMap<V, Node<V>> nodes;
        // Node<V>的父亲是谁
        public HashMap<Node<V>, Node<V>> parents;
        // Node<V>所在集合的大小（只有集合的代表节点<可以理解为头节点>才会放到sizeMap中）
        public HashMap<Node<V>, Integer> sizeMap;

        // 初始化时把用户给定的数据全部放到各个Map中
        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V current : values) {
                Node<V> node = new Node<>(current);
                nodes.put(current, node);
                // 初始化时node的父亲是自己
                parents.put(node, node);
                // 初始化时node的size是1
                sizeMap.put(node, 1);
            }
        }

        /**
         * 找到指定节点所在的代表节点
         */
        public Node<V> findHead(Node<V> node) {
            Node<V> current = node;
            Stack<Node<V>> stack = new Stack<>();
            // 当前节点的父节点不是自己，说明还没找到最顶
            while (current != parents.get(current)) {
                stack.push(current);
                current = parents.get(current);
            }

            // 优化：修改查找路径上的所有节点，将它们都指向根结点
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), current);
            }

            return current;
        }

        /**
         * 判断两个节点所在集合是不是同一个集合
         */
        public boolean isSameSet(V a, V b) {
            return findHead(nodes.get(a)) == findHead(nodes.get(b));
        }

        /**
         * 将两个节点所在集合合并为一个集合
         */
        public void union(V a, V b) {
            Node<V> aHead = findHead(nodes.get(a));
            Node<V> bHead = findHead(nodes.get(b));
            if (aHead != bHead) { // 说明a、b所在集合不是同一个集合
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                // 找到size更大的集合
                Node<V> big = aSize >= bSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                // 小的连到大的上面（这也是一个优化）
                parents.put(small, big);
                // 重新调整big所在集合的size
                sizeMap.put(big, aSize + bSize);
                // small所在集合已经连到big上，从sizeMap中移除
                sizeMap.remove(small);
            }
        }

        /**
         * 当前集合数量
         */
        public int size() {
            return sizeMap.size();
        }

    }

}
