package basic.dynamicprogramming;

import java.util.HashMap;

/**
 * https://leetcode-cn.com/problems/stickers-to-spell-word
 * 691.贴纸拼词
 * <p>
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文。
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来，
 * 返回需要至少多少张贴纸可以完成这个任务。（每种贴纸无数张）
 * <p>
 * 例子: str= "babac"，arr= {"ba","c","abcd"}
 * 至少需要两张贴纸，"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含
 * 有2个a、2个b、1个c，是可以拼出str的。所以返回2。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class StickersToSpellWord {

    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * @param stickers 所有贴纸stickers，每一种贴纸都有无穷张
     * @param target   目标
     * @return 最少张数
     */
    public static int process1(String[] stickers, String target) {
        // 目标已经完成，还需要0张贴纸
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        // 每一张贴纸都可能作为第一个选择的贴纸，在所有情况中返回最小的
        for (String first : stickers) {
            // 从target中减去当前first字符串后剩下的字符串
            String rest = minus(target, first);
            // 排除first为空字符的情况
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }
        // 所有情况都枚举完后，都无法拼出target字符串，则min=Integer.MAX_VALUE
        // 能够拼出target，则min还要加上first这张贴纸
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 返回s1-s2后的字符串
     * <p>
     * 例如：aabbccdd - abf = abccdd
     */
    public static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // count数组从下标0到25分别表示a到z在字符串中出现的次数
        int[] count = new int[26];
        for (char cha : str1) {
            count[cha - 'a']++;
        }
        for (char cha : str2) {
            count[cha - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        // 用字符出现次数代替贴纸
        // stickersCounts[i]表示i号贴纸各个字符出现的次数，
        // 0位置表示a出现次数……25表示z出现的次数
        int[][] stickersCounts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] charArray = stickers[i].toCharArray();
            for (char c : charArray) {
                stickersCounts[i][c - 'a']++;
            }
        }
        int answer = process2(stickersCounts, target);
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    /**
     * @param stickersCounts 所有贴纸各个字符出现次数的统计
     * @param target         目标字符串
     * @return 拼成目标字符串最少贴纸数
     */
    private static int process2(int[][] stickersCounts, String target) {
        // 目标已经完成，还需要0张贴纸
        if (target.length() == 0) {
            return 0;
        }
        // 统计target各个字符出现次数
        char[] targetArray = target.toCharArray();
        int[] targetCounts = new int[26];
        for (char c : targetArray) {
            targetCounts[c - 'a']++;
        }
        // 最小张数
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickersCounts.length; i++) {
            // 当前贴纸
            int[] sticker = stickersCounts[i];
            // 剪枝
            // 当前贴纸包含目标字符串的首字符才统计
            if (sticker[targetArray[0] - 'a'] > 0) {
                // 目标字符串减去贴纸后剩余的字符串
                StringBuilder rest = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetCounts[j] > 0) {
                        int num = targetCounts[j] - sticker[j];
                        for (int k = 0; k < num; k++) {
                            rest.append((char) (j + 'a'));
                        }
                    }
                }
                min = Math.min(min, process2(stickersCounts, rest.toString()));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 采用傻缓存优化
     * <p>
     * 因为可变参数是字符串，无法按照之前的做成一个表结构。
     * 前面两种执行都会超时，只有此种不会
     */
    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * @param stickers 所有贴纸各个字符出现次数的统计
     * @param t        剩余还需要拼接的字符串
     * @param dp       傻缓存表
     */
    public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp) {
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, ans);
        return ans;
    }

}
