package basic.stackqueue;

import java.util.Stack;

/**
 * 使用栈结构实现队列结构的功能
 *
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class TwoStacksImplementQueue {

    /**
     * 使用两个栈来实现队列结构
     */
    public static class TwoStackQueue<T> {
        // 用于存放添加数据的栈
        public Stack<T> stackPush;
        // 用于存放获取数据的栈
        public Stack<T> stackPop;

        public TwoStackQueue() {
            stackPush = new Stack<>();
            stackPop = new Stack<>();
        }

        /**
         * 倒数据
         */
        private void pushToPop() {
            // pop为空才倒数据
            if (stackPop.empty()) {
                // push有数据可倒，并且一次性倒完
                while (!stackPush.empty()) {
                    stackPop.push(stackPush.pop());
                }
            }
        }

        /**
         * 添加数据都往stackPush栈放
         */
        public void add(T data) {
            stackPush.push(data);
        }

        public T poll() {
            if (stackPush.empty() && stackPop.empty()) {
                throw new RuntimeException("Queue is empty");
            }
            // 拿数据前先执行倒数据方法
            pushToPop();
            // 拿数据都从stackPop栈拿
            return stackPop.pop();
        }

        public T peek() {
            if (stackPush.empty() && stackPop.empty()) {
                throw new RuntimeException("Queue is empty");
            }
            pushToPop();
            return stackPop.peek();
        }

    }

    public static void main(String[] args) {
        TwoStackQueue<Integer> test = new TwoStackQueue<>();
        test.add(1);
        test.add(4);
        test.add(3);
        test.add(2);

        System.out.println(test.peek());
        System.out.println(test.poll());

        System.out.println(test.peek());
        System.out.println(test.poll());

        System.out.println(test.peek());
        System.out.println(test.poll());

        System.out.println(test.peek());
        System.out.println(test.poll());
    }

}
