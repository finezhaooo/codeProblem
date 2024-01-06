import concurrency.PrintInOrder;

/**
 * @ClassName: concurrencyTest
 * @Description:
 * @Author: zhaooo
 * @Date: 2024/01/06 17:25
 */
public class concurrencyTest {
    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            PrintInOrder.Foo4 foo = new PrintInOrder.Foo4();
            // 下面三个Thread顺序可以任意掉换
            new Thread(() -> {
                try {
                    foo.second(() -> System.out.println("Two"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    foo.first(() -> System.out.println("One"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    foo.third(() -> System.out.println("Three"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            try {
                Thread.sleep(1000);
                System.out.println("====");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
