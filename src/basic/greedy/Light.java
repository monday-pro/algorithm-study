package basic.greedy;

import java.util.HashSet;

/**
 * 给定一个字符串str，只由 'X' 和 '.' 两种字符构成。
 * 'X’ 表示墙，不能放灯，点亮不点亮都可；
 * '.' 表示居民点，可以放灯，需要点亮。
 * 如果灯放在i位置，可以让 i-1，i 和 i+1 三个位置被点亮。
 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Light {

    public static int light(String light) {
        char[] lightChars = light.toCharArray();
        // 已经点灯的数量
        int result = 0;
        // 当前来到的位置
        int i = 0;
        while (i < lightChars.length) {
            // i 位置是 'X’，不管，来到 i + 1位置
            if (lightChars[i] == 'X') {
                i++;
            } else {
                // i 位置是 '.' ，不管后续是怎样的，都要点一个灯
                result++;
                if (i + 1 == lightChars.length) {
                    break;
                } else {
                    // i 位置是 '.' ，i + 1是 'X’，i 位置需要放灯，来到 i + 2位置
                    if (lightChars[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        // i 位置是 '.' ，i + 1是 '.'，i + 2是 '.'，i + 1 位置需要放灯，来到 i + 3位置
                        // i 位置是 '.' ，i + 1是 '.'，i + 2是 'X’，i 或 i + 1 位置需要放灯，来到 i + 3位置
                        i = i + 3;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 对数器方法
     */
    public static int light1(String light) {
        if (light == null || light.length() == 0) {
            return 0;
        }
        return process(light.toCharArray(), 0, new HashSet<>());
    }

    /**
     * str[index....]位置，自由选择放灯还是不放灯
     * str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
     * 要求选出满足条件的方案，并且在这些有效的方案中，返回最少需要几个灯
     *
     * @param str    需要放灯的字符串
     * @param index  当前来到的放灯的位置
     * @param lights 放了灯的位置
     * @return 最少需要几个灯
     */
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        // 结束的时候
        if (index == str.length) {
            for (int i = 0; i < str.length; i++) {
                // 当前位置是点的话
                if (str[i] != 'X') {
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else { // str还没结束
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }

    public static void main(String[] args) {
        int length = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String light = generateRandomLight(length);
            if (light(light) != light1(light)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    // ------------------------------- 辅助测试的方法 -------------------------------

    public static String generateRandomLight(int length) {
        char[] result = new char[(int) ((length + 1) * Math.random())];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(result);
    }

}
