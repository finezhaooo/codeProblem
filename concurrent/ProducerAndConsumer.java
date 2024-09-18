import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName: ProducerAndConsumer
 * @Description: 生产者消费者模型
 * @Author: zhaooo
 * @Date: 2024/03/31 16:28
 */
public class ProducerAndConsumer {

    static class SemaphoreProducerAndConsumer {
        static class producer implements Runnable {
            Semaphore full;
            Semaphore empty;
            Semaphore mutex;
            List<Object> list;

            @Override
            public void run() {
                while (true) {
                    try {
                        // 消耗一个空缓冲区
                        empty.acquire();
                        // 进入临界区
                        mutex.acquire();
                        // 生产数据
                        Thread.sleep(2000);
                        list.add(new Object());
                        System.out.println(Thread.currentThread().getName() + " produce one product and the list size is " + list.size());
                        // 离开临界区，释放互斥信号量
                        mutex.release();
                        // 提供产品，产品缓冲区+1
                        full.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public producer(Semaphore full, Semaphore empty, Semaphore mutex, List<Object> list) {
                this.full = full;
                this.empty = empty;
                this.mutex = mutex;
                this.list = list;

            }
        }

        static class consumer implements Runnable {
            Semaphore full;
            Semaphore empty;
            Semaphore mutex;
            List<Object> list;

            @Override
            public void run() {
                while (true) {
                    try {
                        // 获取满缓冲区
                        full.acquire();
                        // 进入临界区
                        mutex.acquire();
                        // 消费数据
                        Thread.sleep(2000);
                        list.remove(0);
                        // 离开临界区，释放互斥信号量
                        System.out.println(Thread.currentThread().getName() + " consume one product and the list size is " + list.size());
                        mutex.release();
                        // 提供产品，产品缓冲区+1
                        empty.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public consumer(Semaphore full, Semaphore empty, Semaphore mutex, List<Object> list) {
                this.full = full;
                this.empty = empty;
                this.mutex = mutex;
                this.list = list;
            }
        }

        public static void main(String[] args) {
            // 缓冲区初始化为0
            Semaphore full = new Semaphore(0);
            // 空闲缓冲区，假如为5
            Semaphore empty = new Semaphore(4);
            // 互斥锁
            Semaphore mutex = new Semaphore(1);
            // 用一个list来模拟生产和消费过程，因为需要频繁增删，所以用链表
            LinkedList<Object> list = new LinkedList<>();
            new Thread(new producer(full, empty, mutex, list)).start();
            new Thread(new producer(full, empty, mutex, list)).start();
            new Thread(new consumer(full, empty, mutex, list)).start();
        }
    }

    static class WaitNotifyProducerAndConsumer {
        static class Producer implements Runnable {
            private final List<Object> list;
            private final int maxLength;

            @Override
            public void run() {
                while (true) {
                    synchronized (list) {
                        try {
                            while (list.size() == maxLength) {
                                list.wait();
                            }
                            list.add(new Object());
                            Thread.sleep(1000);
                            System.out.println(Thread.currentThread().getName() + " consume one product and the list size is " + list.size());
                            list.notifyAll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            public Producer(List<Object> list, int maxLength) {
                this.list = list;
                this.maxLength = maxLength;
            }
        }

        static class Consumer implements Runnable {
            private final List<Object> list;

            @Override
            public void run() {
                while (true) {
                    synchronized (list) {
                        try {
                            while (list.isEmpty()) {
                                list.wait();
                            }
                            list.remove(0);
                            Thread.sleep(1000);
                            System.out.println(Thread.currentThread().getName() + " consume one product and the list size is " + list.size());
                            list.notifyAll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            public Consumer(List<Object> list) {
                this.list = list;
            }
        }

        public static void main(String[] args) {
            LinkedList<Object> linkedList = new LinkedList<>();
            new Thread(new Producer(linkedList, 10)).start();
            new Thread(new Producer(linkedList, 10)).start();
            new Thread(new Consumer(linkedList)).start();
        }
    }

    static class BlockingQueueProducerAndConsumer {
        static class Producer implements Runnable {
            private final BlockingQueue<Object> queue;

            public Producer(BlockingQueue<Object> queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        queue.put(new Object());
                        System.out.println(Thread.currentThread().getName() + " produce one product and the list size is " + queue.size());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        static class Consumer implements Runnable {
            private final BlockingQueue<Object> queue;

            public Consumer(BlockingQueue<Object> queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        queue.take();
                        System.out.println(Thread.currentThread().getName() + " consume one product and the list size is " + queue.size());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
            new Thread(new Producer(queue)).start();
            new Thread(new Producer(queue)).start();
            new Thread(new Consumer(queue)).start();
        }
    }
}