package basic.trietree;

import java.util.HashMap;

/**
 * 前缀树
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class TrieTree {

    /**
     * 字符种类：只有26个小写字母
     */
    public static class Node1 {
        // 当前节点被经过了几次
        private int pass;
        // 有多少个字符串以当前节点结尾
        private int end;
        // 当前节点所有的子节点
        private Node1[] nexts;

        // 节点只存放26个小写字母
        public Node1() {
            pass = 0;
            end = 0;
            // node[i] == null，节点不存在
            nexts = new Node1[26];
        }
    }

    public static class Trie1 {
        private Node1 root;

        public Trie1() {
            root = new Node1();
        }

        /**
         * 向前缀树中添加一个字符串
         */
        public void insert(String word) {
            if (word == null) {
                return;
            }
            // 正在添加字符的节点
            Node1 node = root;
            // 已经有字符经过该节点，修改节点pass值
            node.pass++;
            int path = 0;
            // 当前单词挨个字符的添加到前缀树上
            char[] chars = word.toCharArray();
            for (char aChar : chars) {
                // 当前字符应该走的路径
                path = aChar - 'a';
                // 当前节点的path路径对应节点不存在，则新建
                if (node.nexts[path] == null) {
                    node.nexts[path] = new Node1();
                }
                // 当前节点的path路径对应节点肯定已经存在了
                // 所以当前节点来到path路径对应节点
                node = node.nexts[path];
                // 已经有字符到达该节点，修改pass值
                node.pass++;
            }
            // 字符添加完毕，修改最后节点的end值
            node.end++;
        }

        /**
         * word单词之前加过多少次
         */
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            int index = 0;
            char[] chars = word.toCharArray();
            // 从根节点开始找
            Node1 node = root;
            for (char aChar : chars) {
                // 当前字符应该走的路径
                index = aChar - 'a';
                // 当前节点的index路径对应节点不存在，直接返回
                if (node.nexts[index] == null) {
                    return 0;
                }
                // 当前节点的index路径对应节点存在，则当前查找节点来到index路径对应节点
                node = node.nexts[index];
            }
            // 最后当前节点的end值即是此单词加过的次数
            return node.end;
        }

        /**
         * 所有已经加入的字符串中，有多少是以pre为前缀的
         */
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            int index = 0;
            // 从根节点开始找
            Node1 node = root;
            // 挨个字符找
            char[] chars = pre.toCharArray();
            for (char aChar : chars) {
                // 当前字符应该走的路径
                index = aChar - 'a';
                // 当前节点的index路径对应节点不存在，直接返回
                if (node.nexts[index] == null) {
                    return 0;
                }
                // 当前节点的index路径对应节点存在，则当前查找节点来到index路径对应节点
                node = node.nexts[index];
            }
            // 最后当前查找节点所处节点的pass值即是以pre为前缀的数量
            return node.pass;
        }

        /**
         * 在前缀树中删除某个字符串
         */
        public void delete(String word) {
            // 字符串存在才执行删除逻辑
            if (search(word) != 0) {
                // 从根节点开始
                Node1 node = root;
                // 修改根节点pass值
                node.pass--;
                int index = 0;
                char[] chars = word.toCharArray();
                for (char aChar : chars) {
                    // 当前字符应该走的路径
                    index = aChar - 'a';
                    // 当前节点index路径对应节点的pass值减一
                    if (--node.nexts[index].pass == 0) {
                        // 减一后如果为0，表明没有字符串再经过此节点
                        // 将此节点index路径对应节点置空，帮助GC
                        node.nexts[index] = null;
                        return;
                    }
                    // 减一后不为0，表明还有字符串经过此节点
                    // 则当前节点移动到index路径对应的节点
                    node = node.nexts[index];
                }
                // 最后修改节点所在位置end值
                node.end--;
            }
        }

    }

    /**
     * 字符种类：不一定只有26个小写字母
     */
    public static class Node2 {

        // 当前节点被经过了几次
        private int pass;

        // 有多少个字符串以当前节点结尾
        private int end;

        // 当前节点所有的子节点，Key为节点对应字符串字符转换为整型后的ASCII码值
        private HashMap<Integer, Node2> nexts;

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        /**
         * 向前缀树中添加一个字符串，此字符串不一定全是小写字母
         */
        public void insert(String str) {
            if (str == null) {
                return;
            }
            // 正在添加字符串的节点
            Node2 node = root;
            // 已经有字符经过该节点，修改节点pass值
            node.pass++;
            int index = 0;
            // 当前字符串挨个字符的添加到前缀树上
            char[] chars = str.toCharArray();
            for (char aChar : chars) {
                index = aChar;
                // 当前节点的index路径对应节点不存在，则新建
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                // 当前节点的index路径对应节点已经存在了
                // 所以当前节点来到index路径对应节点
                node = node.nexts.get(index);
                // 已经有字符到达该节点，修改pass值
                node.pass++;
            }
            // 字符串添加完毕，修改最后节点的end值
            node.end++;
        }

        /**
         * 字符串之前加过多少次
         */
        public int search(String str) {
            if (str == null) {
                return 0;
            }
            int index = 0;
            char[] chars = str.toCharArray();
            // 从根节点开始找
            Node2 node = root;
            for (char aChar : chars) {
                // 当前字符应该走的路径
                index = aChar;
                // 当前节点的index路径对应节点不存在，直接返回
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                // 当前节点的index路径对应节点存在，则当前查找节点来到index路径对应节点
                node = node.nexts.get(index);
            }
            // 最后当前节点的end值即是此字符串加过的次数
            return node.end;
        }

        /**
         * 所有已经加入的字符串中，有多少是以pre为前缀的
         */
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            int index = 0;
            // 从根节点开始找
            Node2 node = root;
            // 挨个字符找
            char[] chars = pre.toCharArray();
            for (char aChar : chars) {
                // 当前字符应该走的路径
                index = aChar;
                // 当前节点的index路径对应节点不存在，直接返回
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                // 当前节点的index路径对应节点存在，则当前查找节点来到index路径对应节点
                node = node.nexts.get(index);
            }
            // 最后当前节点的pass值即是以pre为前缀的数量
            return node.pass;
        }

        /**
         * 在前缀树中删除某个字符串
         */
        public void delete(String str) {
            // 字符串存在才执行删除逻辑
            if (search(str) != 0) {
                // 从根节点开始
                Node2 node = root;
                // 修改根节点pass值
                node.pass--;
                int index = 0;
                char[] chars = str.toCharArray();
                for (char aChar : chars) {
                    // 当前字符应该走的路径
                    index = aChar;
                    // 当前节点index路径对应节点的pass值减一
                    if (--node.nexts.get(index).pass == 0) {
                        // 减一后如果为0，表明没有字符串再经过此节点
                        // 将此节点index路径对应节点置空
                        node.nexts.remove(index);
                        return;
                    }
                    // 减一后不为0，表明还有字符串经过此节点
                    // 则当前节点移动到index路径对应的节点
                    node = node.nexts.get(index);
                }
                // 最后修改节点所在位置end值
                node.end--;
            }
        }

    }

    public static void main(String[] args) {
        int maxLength = 100;
        int maxSize = 100;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            String[] str = generateRandomStringArray(maxSize, maxLength);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            for (String s : str) {
                double random = Math.random();
                if (random < 0.25) {
                    trie1.insert(s);
                    trie2.insert(s);
                } else if (random < 0.5) {
                    trie1.delete(s);
                    trie2.delete(s);
                } else if (random < 0.75) {
                    int search1 = trie1.search(s);
                    int search2 = trie2.search(s);
                    if (search1 != search2) {
                        System.out.println("Error1");
                        break;
                    }
                } else {
                    int prefixNumber1 = trie1.prefixNumber(s);
                    int prefixNumber2 = trie2.prefixNumber(s);
                    if (prefixNumber1 != prefixNumber2) {
                        System.out.println("Error2");
                        break;
                    }
                }
            }
        }
        System.out.println("Finish");
    }

    //--------------------------------------- 辅助测试的方法 -------------------------------------

    public static String generateRandomString(int maxLength) {
        char[] res = new char[(int) (maxLength * Math.random() + 1)];
        for (int i = 0; i < res.length; i++) {
            res[i] = (char) (97 + (int) (Math.random() * 26));
        }
        return String.valueOf(res);
    }

    public static String[] generateRandomStringArray(int maxSize, int maxLength) {
        String[] str = new String[(int) (maxSize * Math.random() + 1)];
        for (int i = 0; i < str.length; i++) {
            str[i] = generateRandomString(maxLength);
        }
        return str;
    }

}
