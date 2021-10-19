package basic.stackqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 使用队列结构实现栈结构
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class TwoQueuesImplementStack {

    /**
     * 两个队列实现栈结构
     */
    public static class TwoQueueStack<T> {
        public Queue<T> queue;
        public Queue<T> help;

        public TwoQueueStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public void push(T data) {
            queue.offer(data);
        }

        public T poll() {
            while (queue.size() > 1) {
                // 将queue中数据倒入help中，留下一个
                help.offer(queue.poll());
            }
            // 将queue中剩下的一个数据取出后返回
            T res = queue.poll();

            // 交换queue和help的引用
            // 这样就不用判断当前哪个队列存在数据，每次都是从queue倒到help，取数据、交换引用，取后又是queue队列存放数据
            // 下次拿就直接从queue中拿，大大减少代码复杂程度
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;

            return res;
        }

        public T peek() {
            while (queue.size() > 1) {
                // 将queue中数据倒入help中，留下一个
                help.offer(queue.poll());
            }
            // 将queue中剩下的一个数据取出后返回
            T res = queue.poll();
            // 由于是peek，所以将最后这一个数放到help队列中
            help.offer(res);

            // 交换queue和help的引用
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;

            return res;
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

    public static void main(String[] args) {
        System.out.println("test begin");
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        Stack<Integer> stack = new Stack<>();

        int testTime = 100000;
        int maxValue = 100000;

        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!stack.isEmpty()) {
                    System.out.println("error");
                }
                int value = (int) ((maxValue + 1) * Math.random());
                myStack.push(value);
                stack.push(value);
            } else {
                if (Math.random() < 0.25) {
                    int value = (int) ((maxValue + 1) * Math.random());
                    myStack.push(value);
                    stack.push(value);
                } else if (Math.random() < 0.5) {
                    if (!myStack.peek().equals(stack.peek())) {
                        System.out.println("error");
                    }
                } else if (Math.random() < 0.75) {
                    if (!myStack.poll().equals(stack.pop())) {
                        System.out.println("error");
                    }
                } else {
                    if (myStack.isEmpty() != stack.isEmpty()) {
                        System.out.println("error");
                    }
                }
            }
        }
        System.out.println("test end");
    }

}
