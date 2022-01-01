package basic.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工信息的定义如类:Employee
 *
 * 一个员工只有一个直接上级。也就是这个公司员工的层级结构就是一个多叉树。
 *
 * 现在公司邀请员工参加派对，要求不能同时邀请员工和员工的任一下级（即直接上下级不能同时邀请），
 * 如何邀请员工，才能使得参加派对的员工的快乐值是所有情况中最大的。最后返回最大的快乐值。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class MaxHappy {

    public static class Employee {
        //这名员工可以带来的快乐值
        public int happy;
        //这名员工有哪些直接下级
        List<Employee> next;

        public Employee(int happy) {
            this.happy = happy;
            this.next = new ArrayList<>();
        }
    }

    public static int maxHappy(Employee head) {
        if (head == null) {
            return 0;
        }
        Info processInfo = process(head);
        return Math.max(processInfo.yes, processInfo.no);
    }

    public static class Info {
        // 来的最大收益
        public int yes;
        // 不来的最大收益
        public int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static Info process(Employee x) {
        if (x == null) {
            return new Info(0, 0);
        }

        // x不来的最大快乐值
        int no = 0;
        // x来的最大快乐值
        int yes = x.happy;
        for (Employee e : x.next) {
            Info nextInfo = process(e);
            // x来，则所有下级都不能来
            yes += nextInfo.no;
            // x不来，则求每个下级 来或不来 的最大值
            no += Math.max(nextInfo.yes, nextInfo.no);
        }

        return new Info(yes, no);
    }

    public static int right(Employee head) {
        if (head == null) {
            return 0;
        }
        return rightProcess(head, false);
    }

    private static int rightProcess(Employee current, boolean up) {
        if (up) {
            // up为true，表示current的上级来，那么current就不能来
            int ans = 0;
            for (Employee e : current.next) {
                ans += rightProcess(e, false);
            }
            return ans;
        } else {
            // up为false，表示current的上级不来，那么current来不来都可以
            // current来的最大快乐值
            int yes = current.happy;
            // current不来的最大快乐值
            int no = 0;
            for (Employee e : current.next) {
                yes += rightProcess(e, true);
                no += rightProcess(e, false);
            }
            return Math.max(yes, no);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxNext = 5;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee head = generateEmployee(maxLevel, maxNext, maxHappy);
            if (maxHappy(head) != right(head)) {
                System.out.println("Error");
                break;
            }
        }
        System.out.println("Finish");
    }

    // -------------------------------- 辅助测试的方法 ------------------------------------

    public static Employee generateEmployee(int maxLevel, int maxNext, int maxHappy) {
        if (Math.random() < 0.1) {
            return null;
        }

        Employee boss = new Employee((int) ((maxHappy + 1) * Math.random()));
        generateNext(boss, 1, maxLevel, maxNext, maxHappy);
        return boss;
    }

    private static void generateNext(Employee e, int level, int maxLevel, int maxNext, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextSize = (int) ((maxNext + 1) * Math.random());
        for (int i = 0; i < nextSize; i++) {
            Employee next = new Employee((int) ((maxHappy + 1) * Math.random()));
            e.next.add(next);
            generateNext(next, level + 1, maxLevel, maxNext, maxHappy);
        }
    }

}
