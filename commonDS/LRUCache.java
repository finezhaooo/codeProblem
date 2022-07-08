import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LRUCache
 * @Description: LRU 缓存
 * 使用双向链表+hashMap实现 可用LinkedHashMap替换
 * @Author: zhaooo
 * @Date: 2022/7/8/17:02
 */
public class LRUCache {

    int capacity;
    Map<Integer, Node> map;
    DoubleList list;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        list = new DoubleList();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        list.remove(node);
        list.addLast(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            // 放入队尾
            get(key);
        } else {
            Node node = new Node(key, value);
            if (list.size() == capacity) {
                Node rm = list.removeFirst();
                map.remove(rm.key);
            }
            map.put(key, node);
            list.addLast(node);
        }
    }

    class Node {
        //保存key是为了删除时可以找到map中的元素
        public int key, val;
        // 保存prev是为了在删除时可以找到要删除的元素即可
        public Node next, prev;

        public Node(int k, int v) {
            this.key = k;
            this.val = v;
        }
    }

    class DoubleList {
        // 头尾虚节点
        private Node head, tail;
        // 链表元素数
        private int size;

        public DoubleList() {
            // 初始化双向链表的数据
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        // 在链表尾部添加节点 x，时间 O(1)
        public void addLast(Node x) {
            x.prev = tail.prev;
            x.next = tail;
            tail.prev.next = x;
            tail.prev = x;
            size++;
        }

        // 删除链表中的 x 节点（x 一定存在）
        // 由于是双链表且给的是目标 Node 节点，时间 O(1)
        public void remove(Node x) {
            x.prev.next = x.next;
            x.next.prev = x.prev;
            size--;
        }

        // 删除链表中第一个节点，并返回该节点，时间 O(1)
        public Node removeFirst() {
            if (head.next == tail) {
                return null;
            }
            Node first = head.next;
            remove(first);
            return first;
        }

        // 返回链表长度，时间 O(1)
        public int size() {
            return size;
        }

    }
}
