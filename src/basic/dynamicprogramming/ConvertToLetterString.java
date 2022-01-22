package basic.dynamicprogramming;

import java.util.Random;

/**
 * 规定1和A对应、2和B对应、3和C对应……26和Z对应。
 * 那么一个数字字符串比如"111”就可以转化为：“AAA"、 "KA"和"AK"。
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class ConvertToLetterString {

    public static int number(String string) {
        if (string == null || string.length() == 0) {
            return 0;
        }
        return process1(string.toCharArray(), 0);
    }

    /**
     * arr[0...i)上的字符已经转换完成，无需修改
     * arr[i...)以后的字符还能自由转换
     *
     * @param arr 待转换的数组
     * @param i   当前转换的位置
     * @return arr的i位置及以后还有多少种转换方法
     */
    private static int process1(char[] arr, int i) {
        // i已经到了最后位置了，说明之前的转换有效，产生了一种转换方法
        if (i == arr.length) {
            return 1;
        }
        // 当前单独面对0字符，说明之前的选择不对
        if (arr[i] == '0') {
            return 0;
        }

        // i位置单独转换
        int p1 = process1(arr, i + 1);
        // i位置和i+1位置一起转换
        // i+1存在，且i和i+1组成的数字字符小于27
        int p2 = i + 1 < arr.length && (arr[i] - '0') * 10 + (arr[i + 1] - '0') < 27 ? process1(arr, i + 2) : 0;

        return p1 + p2;
    }

    /**
     * 直接由上面的暴力递归修改
     */
    public static int dp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] arr = s.toCharArray();
        // 1.根据暴力递归的可变参数i的取值范围，确定动态规划的数组大小
        int n = arr.length;
        int[] dp = new int[n + 1];
        // 2.根据暴力递归的 base case 进行边界赋值
        dp[n] = 1;
        // 3.将暴力递归的递归过程转换为动态规划
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] != '0') {
                int p1 = dp[i + 1];
                int p2 = i + 1 < n && (arr[i] - '0') * 10 + (arr[i + 1] - '0') < 27 ? dp[i + 2] : 0;
                dp[i] = p1 + p2;
            }
        }
        // 4.根据暴力递归主函数的调用，确定动态规划的返回值
        return dp[0];
    }

    public static void main(String[] args) {
        int maxLength = 10;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            String s = randomString(maxLength);
            if (number(s) != dp(s)) {
                System.out.println("Error");
                System.out.println(s);
                break;
            }
        }
        System.out.println("Finish");
    }

    // ----------------------------------- 辅助测试的方法 ----------------------------------

    public static String randomString(int maxLength) {
        Random random = new Random();
        int length = (int) ((maxLength + 1) * Math.random());
        char[] str = new char[length];
        for (int i = 0; i < length; i++) {
            str[i] = (char) (random.nextInt(10) + '0');
        }
        return String.valueOf(str);
    }

}
