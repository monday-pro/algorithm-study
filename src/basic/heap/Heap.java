package basic.heap;

/**
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class Heap {

    public static class MyHeap {
        private int[] heap;
        private int limit;
        private int heapSize;

        public MyHeap(int limit) {
            this.limit = limit;
            heap = new int[limit];
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return limit == heapSize;
        }

        /**
         * 添加数据，并保持大根堆结构
         */
        public void push(int data) {
            if (isFull()) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = data;
            heapInsert(heap, heapSize++);
        }

        /**
         * 获取当前数组最大值，并从堆中删除，维持大根堆结构
         */
        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        /**
         * 将index位置的数往下沉，直到较大的孩子都没自己大，或者没孩子了
         */
        private void heapify(int[] arr, int index, int heapSize) {
            // 左子树位置
            int left = 2 * index + 1;
            while (left < heapSize) {
                // 找到左右子树哪个值更大
                int maxIndex = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                // 将左右子树中较大的和父节点比较
                maxIndex = arr[maxIndex] > arr[index] ? maxIndex : index;
                // 左右子树较大的值都小于父节点，停止循环
                if (maxIndex == index) {
                    break;
                }
                // 将左右子树中较大的和父节点交换
                swap(arr, index, maxIndex);
                // 当前比较的节点位置来到较大的子节点
                index = maxIndex;
                // 重新获取当前节点的左子树
                left = 2 * index + 1;
            }
        }

        /**
         * 对于新加进来的index位置的数，请放到数组合适位置，使其成为一个大根堆
         */
        private void heapInsert(int[] heap, int index) {
            // i = 0 或 i位置数小于父节点
            // while包含这两种终止条件
            while (heap[index] > heap[(index - 1) / 2]) {
                swap(heap, index, (index - 1) / 2);
                // 继续向上比较
                index = (index - 1) / 2;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    /**
     * 用于测试上面大根堆的类
     *
     * 此类里面的数据不是大根堆结构
     */
    public static class TestMaxHeap {
        private int[] arr;
        private int limit;
        private int size;

        public TestMaxHeap(int limit) {
            this.limit = limit;
            arr = new int[limit];
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return limit == size;
        }

        public void push(int data) {
            if (isFull()) {
                throw new RuntimeException("heap if full");
            }
            arr[size++] = data;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 1000;
        int maxValue = 10000;

        System.out.println("start");
        for (int i = 0; i < testTimes; i++) {
            int currentLimit = (int) ((maxSize + 1) * Math.random());
            MyHeap myHeap = new MyHeap(currentLimit);
            TestMaxHeap test = new TestMaxHeap(currentLimit);

            for (int j = 0; j < currentLimit; j++) {
                if (myHeap.isEmpty() != test.isEmpty()) {
                    System.out.println("error1");
                }
                if (myHeap.isFull() != test.isFull()) {
                    System.out.println("error2");
                }
                if (myHeap.isEmpty()) {
                    int value = (int) ((maxValue + 1) * Math.random());
                    myHeap.push(value);
                    test.push(value);
                } else if (myHeap.isFull()) {
                    if (myHeap.pop() != test.pop()) {
                        System.out.println("error3");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int value = (int) ((maxValue + 1) * Math.random());
                        myHeap.push(value);
                        test.push(value);
                    } else {
                        if (myHeap.pop() != test.pop()) {
                            System.out.println("error4");
                        }
                    }
                }
            }
        }
        System.out.println("finish");
    }

}
