package basic.dynamicprogramming.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 打印一个字符串的全部子序列
 * <p>
 * 子序列：对于字符串"12345"，任意取其中0个、1个、2个、3个、4个、5个都是它的子序列，同时相对顺序不能改变。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class PrintAllSubSequences {

    public static List<String> getAllSubSequences(String s) {
        char[] str = s.toCharArray();
        List<String> answer = new ArrayList<>();
        String path = "";
        process1(str, 0, answer, path);
        return answer;
    }

    /**
     * 当前来到了str[index]字符
     * str[0..index-1]已经走过了，之前的选择都在path上，之前的选择已经不能改变了，就是path。
     * 但是str[index....]还能自由选择，把str[index....]所有生成的子序列，放入到answer里
     *
     * @param str    指定的字符串（固定）
     * @param index  当前所处的位置
     * @param answer 之前决策依据产生的答案
     * @param path   之前已经做的选择
     */
    public static void process1(char[] str, int index, List<String> answer, String path) {
        // 当前来到了字符串的最后位置，已经不能再做决策了，answer只能放入之前的决策
        if (index == str.length) {
            answer.add(path);
            return;
        }
        // 当前没有要index位置的字符
        process1(str, index + 1, answer, path);
        // 当前要index位置的字符
        process1(str, index + 1, answer, path + str[index]);
    }

    /**
     * 打印一个字符串的全部子序列，要求没有重复字面值的子序列
     */
    public static Set<String> getAllSubSequencesNoRepeat(String s) {
        char[] str = s.toCharArray();
        Set<String> answer = new HashSet<>();
        String path = "";
        process2(str, 0, answer, path);
        return answer;
    }

    public static void process2(char[] str, int index, Set<String> answer, String path) {
        if (index == str.length) {
            answer.add(path);
            return;
        }
        process2(str, index + 1, answer, path);
        process2(str, index + 1, answer, path + str[index]);
    }

    public static void main(String[] args) {
        String s = "abcc";
        System.out.println(getAllSubSequences(s));
        System.out.println("======================");
        System.out.println(getAllSubSequencesNoRepeat(s));
    }

}
