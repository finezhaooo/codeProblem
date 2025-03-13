import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 自定义线程池
 * @Author 63538
 * @Date 2024/09/03 22:20
 */
public class MyThreadPoolExecutor implements Executor {
    private final AtomicInteger ctl = new AtomicInteger(0);
    private final int corePoolSize;
    private final int maxPoolSize;
    private final BlockingQueue<Runnable> workQueue;

    public MyThreadPoolExecutor(int corePoolSize, int maxPoolSize, BlockingQueue<Runnable> workQueue) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.workQueue = workQueue;
    }

    @Override
    public void execute(Runnable command) {
        int threadCount = ctl.get();
        if (threadCount < corePoolSize) {
            if (!addWorker(command)) reject();
        } else {
            if (!addCommand(command)) reject();
        }
    }

    private boolean addCommand(Runnable command) {
        if (!workQueue.offer(command))
            return addWorker(command);
        return true;
    }

    private boolean addWorker(Runnable command) {
        int threadCount = ctl.get();
        if (threadCount >= maxPoolSize) return false;
        Worker worker = new Worker(command);
        worker.thread.start();
        ctl.incrementAndGet();
        return true;
    }

    private void reject() {
        throw new RuntimeException("Can't resolve this task. Info:" +
                " thread.count = " + ctl.get() +
                " taskQueue.size = " + workQueue.size());
    }


    private final class Worker implements Runnable {
        Thread thread;
        Runnable command;

        public Worker(Runnable command) {
            this.thread = new Thread(this);
            this.command = command;
        }

        private Runnable getTask() {
            try {
                System.out.println("Notice: " + thread.getName() + " try to take a work, workQueue.size = " + workQueue.size());
                return workQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void run() {
            try {
                Runnable task = command;
                while (task != null || (task = getTask()) != null) {
                    task.run();
                    if (ctl.get() > maxPoolSize) break;
                    task = null;
                }
            } finally {
                ctl.decrementAndGet();
            }
        }
    }

    public static void main(String[] args) {
        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(2, 4, new ArrayBlockingQueue<>(2));
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}