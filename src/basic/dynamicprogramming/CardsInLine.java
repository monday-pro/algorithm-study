package basic.dynamicprogramming;

/**
 * 纸牌问题
 *
 * 给定一个正数整型数组arr，代表数值不同的纸牌排成一条线。
 * 玩家A和玩家B依次拿走每张纸牌（可以看见所有的牌），
 * 规定玩家A先拿，玩家B后拿。但是每个玩家每次只能拿走最左或最右的纸牌。
 * 玩家A和玩家B都绝顶聪明，请返回最后获胜者的分数。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class CardsInLine {

    /**
     * 1.从尝试开始
     */
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int before = f1(arr, 0, arr.length - 1);
        int after = g1(arr, 0, arr.length - 1);
        return Math.max(before, after);
    }

    /**
     * 以先手状态获得的最大分数
     */
    private static int f1(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        // 还剩不止一张牌
        int p1 = arr[L] + g1(arr, L + 1, R);
        int p2 = arr[R] + g1(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    /**
     * 以后手状态获得的最大分数
     */
    private static int g1(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        // 对手拿走L位置的数
        int p1 = f1(arr, L + 1, R);
        // 对手拿走R位置的数
        int p2 = f1(arr, L, R - 1);
        return Math.min(p1, p2);
    }

    /**
     * 2.傻缓存法
     */
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fMap = new int[N][N];
        int[][] gMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fMap[i][j] = -1;
                gMap[i][j] = -1;
            }
        }

        int before = f2(arr, 0, arr.length - 1, fMap, gMap);
        int after = g2(arr, 0, arr.length - 1, fMap, gMap);
        return Math.max(before, after);
    }

    /**
     * 以先手状态获得的最大分数
     */
    private static int f2(int[] arr, int L, int R, int[][] fMap, int[][] gMap) {
        if (fMap[L][R] != -1) {
            return fMap[L][R];
        }

        int ans;
        if (L == R) {
            ans = arr[L];
        } else {
            // 还剩不止一张牌
            int p1 = arr[L] + g2(arr, L + 1, R, fMap, gMap);
            int p2 = arr[R] + g2(arr, L, R - 1, fMap, gMap);
            ans =  Math.max(p1, p2);
        }
        // 更新缓存
        fMap[L][R] = ans;

        return fMap[L][R];
    }

    /**
     * 以后手状态获得的最大分数
     */
    private static int g2(int[] arr, int L, int R, int[][] fMap, int[][] gMap) {
        if (gMap[L][R] != -1) {
            return gMap[L][R];
        }

        int ans;
        if (L == R) {
            ans = 0;
        } else {
            // 对手拿走L位置的数
            int p1 = f2(arr, L + 1, R, fMap, gMap);
            // 对手拿走R位置的数
            int p2 = f2(arr, L, R - 1, fMap, gMap);
            ans =  Math.min(p1, p2);
        }
        // 更新缓存
        gMap[L][R] = ans;

        return gMap[L][R];
    }

    /**
     * 3.最终动态规划
     */
    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fMap = new int[N][N];
        int[][] gMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            fMap[i][i] = arr[i];
        }
        for (int startColumn = 1; startColumn < N; startColumn++) {
            // 行
            int L = 0;
            // 列
            int R = startColumn;
            while (R < N) {
                fMap[L][R] = Math.max(arr[L] + gMap[L + 1][R], arr[R] + gMap[L][R - 1]);
                gMap[L][R] = Math.min(fMap[L + 1][R], fMap[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(fMap[0][N - 1], gMap[0][N - 1]);
    }

    public static void main(String[] args) {
        // int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        int[] arr = { 7, 4, 16, 15, 1 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
    }

}
