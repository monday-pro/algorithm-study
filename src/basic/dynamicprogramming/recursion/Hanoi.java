package basic.dynamicprogramming.recursion;

/**
 * 汉诺塔问题
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Hanoi {

    public static void hanoi1(int n) {
        leftToRight(n);
    }

    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to right");
            return;
        }
        // 从左到中
        leftToMid(n - 1);
        // 从左到右
        System.out.println("Move " + n + " from left to right");
        // 从中到右
        midToRight(n - 1);
    }

    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to mid");
        rightToMid(n - 1);
    }

    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("Move " + n + " from mid to right");
        leftToRight(n - 1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to mid");
        leftToMid(n - 1);
    }

    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("Move " + n + " from right to left");
        midToLeft(n - 1);
    }

    public static void hanoi2(int n) {
        function(n, "left", "right", "mid");
    }

    /**
     * 第一次调用function时：from=左、to=右、other=中
     * 表示借助other=中，将圆盘从from=左移动到to=右杆上
     *
     * @param n     总共的圆盘数量
     * @param from  起始位置
     * @param to    目标位置
     * @param other 剩余杆子
     */
    public static void function(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
            return;
        }
        // 1.将上面n-1个圆盘从左移到中杆上，
        // 即起始位置为左，当前from=左；目标位置中，当前other=中
        function(n - 1, from, other, to);
        // 2.将最大的圆盘从左移到右杆上
        System.out.println("Move " + n + " from " + from + " to " + to);
        // 3.将上面n-1个圆盘从中移到右杆上
        // 即起始位置为中，当前other=中；目标位置右，当前to=右
        function(n - 1, other, to, from);
    }

    public static void main(String[] args) {
        int n = 3;

        hanoi1(n);
        System.out.println("=======================");
        hanoi2(n);
    }

}
