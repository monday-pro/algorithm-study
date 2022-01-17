package basic.dynamicprogramming.recursion;

import java.util.Stack;

/**
 * 给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数。
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class ReverseStackUsingRecursive {

    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int down = getDown(stack);
        reverse(stack);
        stack.push(down);
    }

    /**
     * 得到栈底元素，剩余元素直接下沉
     *
     * 例如，从栈顶到栈底元素为1、2、3、4、5
     * 此方法返回5，剩余从栈顶到栈底元素为1、2、3、4
     */
    private static int getDown(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = getDown(stack);
            stack.push(result);
            return last;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        reverse(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

}
