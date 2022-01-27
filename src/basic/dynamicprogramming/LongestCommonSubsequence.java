package basic.dynamicprogramming;

/**
 * 1143.最长公共子序列，https://leetcode-cn.com/problems/longest-common-subsequence/
 *
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 *
 * 一个字符串的 子序列 是指这样一个新的字符串：
 * 它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 *
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class LongestCommonSubsequence {

    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        return process1(s1.toCharArray(), s2.toCharArray(), s1.length() - 1, s2.length() - 1);
    }

    private static int process1(char[] s1, char[] s2, int i, int j) {
        if (i == 0 && j == 0) {
            // i、j都只有一位了，这一位相等则最长公共子序列长度为1
            return s1[i] == s2[j] ? 1 : 0;
        } else if (i == 0) {
            // i只有一位了，这一位和当前s2[j]相等则最长公共子序列长度为1，不等则看j-1之前的
            // 因为s1只剩一个字符了，所以s1和s2公共子序列最多长度为1
            // 如果s1[0] == s2[j]，那么此时相等已经找到了。公共子序列长度就是1，也不可能更大了
            // 如果s1[0] != s2[j]，只是此时不相等而已，
            // 那么s2[0...j-1]上有没有字符等于s1[0]呢？不知道，所以递归继续找
            return s1[i] == s2[j] ? 1 : process1(s1, s2, i, j - 1);
        } else if (j == 0) {
            // j只有一位了，这一位和当前s1[i]相等则最长公共子序列长度为1，不等则看i-1之前的
            return s1[i] == s2[j] ? 1 : process1(s1, s2, i - 1, j);
        } else {
            // 最长公共子序列一定不包含i，可能包含j位置
            int p1 = process1(s1, s2, i - 1, j);
            // 最长公共子序列可能包含i，一定不包含j位置
            int p2 = process1(s1, s2, i, j - 1);
            // p1和p2有重复的情况，但是不影响求最小值；同时还包括一定不包含i、一定不包含j的情况

            // 最长公共子序列包含i、j位置
            int p3 = s1[i] == s2[j] ? 1 + process1(s1, s2, i - 1, j - 1) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    /**
     * 根据上面的暴力递归修改
     */
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] s1Array = s1.toCharArray();
        char[] s2Array = s2.toCharArray();
        int s1Length = s1.length();
        int s2Length = s2.length();
        // 根据暴力递归，分析可变参数为i，j
        // 取值范围为[0, s1.length-1]，[0, s2.length-1]
        int[][] dp = new int[s1Length][s2Length];
        // 根据base case给dp表赋初值
        dp[0][0] = s1Array[0] == s2Array[0] ? 1 : 0;

        for (int j = 1; j < s2Length; j++) {
            dp[0][j] = s1Array[0] == s2Array[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < s1Length; i++) {
            dp[i][0] = s1Array[i] == s2Array[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < s1Length; i++) {
            for (int j = 1; j < s2Length; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = s1Array[i] == s2Array[j] ? 1 + dp[i - 1][j - 1] : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }

        // 根据暴力递归主函数调用，确定动态规划返回值
        return dp[s1Length - 1][s2Length - 1];
    }

}
