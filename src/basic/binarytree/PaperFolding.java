package basic.binarytree;

/**
 * 纸条折痕问题
 *
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。
 * 此时折痕是凹下去的（下折痕），即折痕突起的方向指向纸条的背面。
 * 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 *
 * 给定一个输入参数N，代表纸条从下边向上方连续对折N次。请从上到下打印所有折痕的方向。
 * 例如：
 * N=1时，打印: down（凹）；
 * N=2时，打印: down（凹）、down（凹）、up（凸）
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class PaperFolding {

    public static void paperFolding(int n) {
        // 二叉树头节点是凹的
        process(1, n, true);
    }

    /**
     * @param i 节点的层数
     * @param n 一共多少层
     * @param down true=凹，false=凸
     */
    private static void process(int i, int n, boolean down) {
        if (i > n) {
            return;
        }

        // 上边是凹
        process(i + 1, n, true);

        System.out.print(down ? "凹 " : "凸 ");

        // 下边是凸
        process(i + 1, n, false);
    }

    public static void main(String[] args) {
        paperFolding(1);
        System.out.println();
        paperFolding(2);
        System.out.println();
        paperFolding(3);
        System.out.println();
        paperFolding(4);
    }

}
