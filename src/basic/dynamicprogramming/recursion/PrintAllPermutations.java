package basic.dynamicprogramming.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印一个字符串的全部全排列
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class PrintAllPermutations {

    /**
     * 1.添加删除的方式
     */
    public static List<String> permutation1(String s) {
        List<String> answer = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return answer;
        }

        ArrayList<Character> strList = new ArrayList<>();
        for (char c : s.toCharArray()) {
            strList.add(c);
        }
        String path = "";
        process1(strList, path, answer);

        return answer;
    }

    /**
     * 递归获取全排列
     *
     * @param strList 当前参与选择的所有字符
     * @param path 之前所做的选择
     * @param answer 最终结果
     */
    private static void process1(ArrayList<Character> strList, String path, List<String> answer) {
        // 当前没有可以选择的字符了，answer只能放入之前的选择
        if (strList.isEmpty()) {
            answer.add(path);
            return;
        }
        for (int i = 0; i < strList.size(); i++) {
            // 当前选择的字符
            char cur = strList.get(i);
            // 舍弃已经选择的字符
            strList.remove(i);
            // 剩余字符再进行选择
            process1(strList, path + cur, answer);
            // 恢复现场
            strList.add(i, cur);
        }
    }

    /**
     * 2.交换的方式
     */
    public static List<String> permutation2(String s) {
        List<String> answer = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return answer;
        }
        char[] str = s.toCharArray();
        process2(str, 0, answer);
        return answer;
    }

    /**
     * 递归获取全排列
     *
     * @param str 当前经历过交换后的字符
     * @param index 当前交换到哪个位置了
     * @param answer 结果
     */
    private static void process2(char[] str, int index, List<String> answer) {
        // 当前来到了字符串的最后位置，已经不能再交换了，answer只能放入之前交换后的字符
        if (index == str.length) {
            answer.add(String.valueOf(str));
            return;
        }

        // index之前的已经交换过不能再变了，所以从index往后还可以再交换
        for (int i = index; i < str.length; i++) {
            // index、i位置交换
            swap(str, index, i);
            // index后面的继续交换
            process2(str, index + 1, answer);
            // index、i位置 恢复现场
            swap(str, index, i);
        }
    }

    /**
     * 2.交换的方式，去重
     */
    public static List<String> permutation3(String s) {
        List<String> answer = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return answer;
        }
        char[] str = s.toCharArray();
        process3(str, 0, answer);
        return answer;
    }

    /**
     * 递归获取全排列，没有重复的字符串
     *
     * @param str 当前经历过交换后的字符
     * @param index 当前交换到哪个位置了
     * @param answer 结果
     */
    private static void process3(char[] str, int index, List<String> answer) {
        // 当前来到了字符串的最后位置，已经不能再交换了，answer只能放入之前交换后的字符
        if (index == str.length) {
            answer.add(String.valueOf(str));
            return;
        }

        boolean[] visited = new boolean[256];
        // index之前的已经交换过不能再变了，所以从index往后还可以再交换
        for (int i = index; i < str.length; i++) {
            // str[i]位置对应字符没有出现过才递归交换，否则忽略
            if (!visited[str[i]]) {
                visited[str[i]] = true;
                // index、i位置交换
                swap(str, index, i);
                // index后面的继续交换
                process3(str, index + 1, answer);
                // index、i位置 恢复现场
                swap(str, index, i);
            }
        }
    }

    private static void swap(char[] str, int i, int j) {
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }

    public static void main(String[] args) {
        String s = "abcc";
        System.out.println(permutation1(s));
        System.out.println();
        System.out.println(permutation2(s));
        System.out.println();
        System.out.println(permutation3(s));
    }

}
