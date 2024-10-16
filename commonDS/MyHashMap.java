import java.util.Objects;

/**
 * @ClassName: MyHashMap
 * @Description: hashMap简化版
 * @Author: zhaooo
 * @Date: 2022/8/1/22:38
 */
public class MyHashMap {
    static class HashMapChaining<K, V> {
        // 定义链表节点
        static class Entry<K, V> {
            K key;
            V value;
            Entry<K, V> next;

            public Entry(K key, V value) {
                this.key = key;
                this.value = value;
                this.next = null;
            }
        }

        private final int CAPACITY = 16; // 初始容量
        private Entry<K, V>[] table; // 用于存储链表的数组

        public HashMapChaining() {
            table = new Entry[CAPACITY]; // 初始化数组
        }

        // 计算哈希值
        private int hash(K key) {
            return Objects.hashCode(key) % CAPACITY;
        }

        // 插入或更新键值对
        public void put(K key, V value) {
            int index = hash(key); // 计算哈希索引
            Entry<K, V> newEntry = new Entry<>(key, value);

            // 如果该索引处为空，直接插入
            if (table[index] == null) {
                table[index] = newEntry;
            } else {
                // 遍历链表，如果找到相同的键，则更新值；否则插入到链表末尾
                Entry<K, V> current = table[index];
                Entry<K, V> prev = null;
                while (current != null) {
                    if (current.key.equals(key)) {
                        current.value = value; // 更新值
                        return;
                    }
                    prev = current;
                    current = current.next;
                }
                prev.next = newEntry; // 插入到链表末尾
            }
        }

        // 获取键对应的值
        public V get(K key) {
            int index = hash(key); // 计算哈希索引
            Entry<K, V> current = table[index];
            // 遍历链表，找到匹配的键
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value; // 返回找到的值
                }
                current = current.next;
            }
            // 未找到键
            return null;
        }

        // 移除键值对
        public void remove(K key) {
            int index = hash(key); // 计算哈希索引
            Entry<K, V> current = table[index];
            Entry<K, V> prev = null;
            // 遍历链表，找到需要删除的节点
            while (current != null) {
                if (current.key.equals(key)) {
                    if (prev == null) {
                        table[index] = current.next; // 删除头节点
                    } else {
                        prev.next = current.next; // 删除非头节点
                    }
                    return;
                }
                prev = current;
                current = current.next;
            }
        }

        // 打印 HashMap 的内容
        public void printMap() {
            for (int i = 0; i < CAPACITY; i++) {
                Entry<K, V> current = table[i];
                System.out.print("Bucket " + i + ": ");
                while (current != null) {
                    System.out.print("[" + current.key + "=" + current.value + "] -> ");
                    current = current.next;
                }
                System.out.println("null");
            }
        }
    }

    static class HashMapLinearProbing<K, V> {

        static final int DEFAULT_CAPACITY = 16;
        static final float DEFAULT_LOAD_FACTOR = 0.75f;
        static final int MAXIMUM_CAPACITY = 1 << 30;
        Node<K, V>[] hashTable;
        int size;
        float loadFactor;

        // 哈希表扩容的阈值 = 哈希表的长度 x 负载因子
        int threshold;

        // 返回第一个大于或者等于 capacity 且为 2 的整数次幂的那个数（最高位以及之后位都位1，然后再+1）
        static int roundUp(int capacity) {
            int n = capacity - 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n |= n >>> 16;
            // 如果最终得到的数据小于 0 则初始长度为 1
            // 如果长度大于我们所允许的最大的容量 则将初始长度设置为我们所允许的最大的容量：1 << 30;
            return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        }

        private static class Node<K, V> {
            final int hash;
            final K key;
            V value;

            public Node(int hash, K key, V value) {
                this.hash = hash;
                this.key = key;
                this.value = value;
            }

            public V setValue(V newValue) {
                value = newValue;
                return newValue;
            }

            @Override
            public String toString() {
                return key + "=" + value;
            }
        }

        public HashMapLinearProbing() {
            this.loadFactor = DEFAULT_LOAD_FACTOR;
            this.threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
        }

        public HashMapLinearProbing(int capacity) {
            this(capacity, DEFAULT_LOAD_FACTOR);
        }

        public HashMapLinearProbing(int initialCapacity, float loadFactor) {
            if (initialCapacity <= 0) {
                throw new IllegalArgumentException("initialCapacity must be positive");
            }
            if (loadFactor <= 0 || loadFactor > 1) {
                throw new IllegalArgumentException("loadFactor must be in (0, 1)");
            }
            if (initialCapacity > MAXIMUM_CAPACITY) {
                initialCapacity = MAXIMUM_CAPACITY;
            }
            initialCapacity = roundUp(initialCapacity);
            hashTable = (Node<K, V>[]) new Node[initialCapacity];
            this.loadFactor = loadFactor;
            threshold = (int) (loadFactor * initialCapacity);
        }

        static int hash(Object key) {
            int h;
            // 上面的函数之所以要将对象的哈希值右移16，是因为我们的数组的长度一般不会超过2^16，因为2^16已经是一个比较大的值了，
            // 因此当哈希值与2^n−1进行&操作的时候，高位通常没有使用到，这样做的原理是可以充分利用数据哈希值当中的信息。
            return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        }

        // 添加node到hashTable，采用线性再探测法
        public void put(K key, V value) {
            if (null == key) {
                throw new IllegalArgumentException("");
            }
            int hash = hash(key);
            // 如果hashTable为空，即使用了无参构造函数，初始化hashTable并直接放入
            if (null == hashTable) {
                hashTable = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
                hashTable[hash & (DEFAULT_CAPACITY - 1)] = new Node<>(hash, key, value);
            } else {
                int n = hashTable.length;
                int idx = hash & (n - 1);
                // 线性再探测法 如果为空或者有相同key就退出循环
                while (null != hashTable[idx] && !key.equals(hashTable[idx].key)) {
                    idx = (idx + 1) & (n - 1);
                }
                hashTable[idx] = new Node<>(hash, key, value);
            }
            if (++size > threshold) {
                resize();
            }
        }

        private void resize() {
            int n = (hashTable.length << 1);
            threshold = (int) (n * loadFactor);
            Node<K, V>[] oldTable = hashTable;
            hashTable = (Node<K, V>[]) new Node[n];
            for (Node<K, V> kvNode : oldTable) {
                if (null == kvNode) {
                    continue;
                }
                int idx = kvNode.hash & (n - 1);
                // 线性再探测
                while (null != hashTable[idx] && !kvNode.key.equals(hashTable[idx].key)) {
                    idx = (idx + 1) & (n - 1);
                }
                hashTable[idx] = kvNode;
            }
        }

        public V get(K key) {
            if (null == key) {
                throw new IllegalArgumentException("");
            }
            int hash = hash(key);
            int n = hashTable.length;
            // 初始idx
            int idx = hash & (n - 1);
            if (null == hashTable[idx]) {
                return null;
            }
            // JDK源码中，无限循环大多使用for(;;)而不是while(true)
            for (; ; ) {
                // 找到一个节点的hash和key都与key相同
                if (null != hashTable[idx]
                        && hash == hashTable[idx].hash
                        && key.equals(hashTable[idx].key)) {
                    break;
                }
                idx = (idx + 1) & (n - 1);
            }
            return hashTable[idx].value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            for (Node<K, V> kvNode : hashTable) {
                builder.append(kvNode);
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append("]");
            return builder.toString();
        }
    }
}
