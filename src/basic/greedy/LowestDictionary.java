package basic.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * 给定一个由字符串组成的数组，必须把所有的字符串拼接起来，返回所有可能的拼接结果中，字典序最小的结果。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class LowestDictionary {

    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    public static String lowestDictionary(String[] str) {
        if (str == null || str.length < 1) {
            return null;
        }
        Arrays.sort(str, new MyComparator());
        StringBuilder result = new StringBuilder();
        for (String s : str) {
            result.append(s);
        }
        return result.toString();
    }

    /**
     * 对数器方法
     */
    public static String right(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> ans = process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }

    /**
     * strs中所有字符串全排列，返回所有可能的结果
     */
    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndexString(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur : next) {
                ans.add(first + cur);
            }
        }
        return ans;
    }

    public static String[] removeIndexString(String[] arr, int index) {
        int n = arr.length;
        String[] ans = new String[n - 1];
        int ansIndex = 0;
        for (int i = 0; i < n; i++) {
            if (i != index) {
                ans[ansIndex++] = arr[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 10;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestDictionary(arr1).equals(right(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Error!");
                break;
            }
        }
        System.out.println("finish!");
    }

    // ---------------------------------- 辅助测试的方法 ----------------------------------

    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

}
