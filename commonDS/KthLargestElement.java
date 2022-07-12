import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @ClassName: KthLargestElement
 * @Description: 数组中的第K个最大元素
 * @Author: zhaooo
 * @Date: 2022/7/11/10:43
 */
public class KthLargestElement {
    /**
     * 快速排序分治
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        int l = 0, r = nums.length - 1;
        // 个数变为下标
        k--;
        // 在[l,r]内寻找
        while (l < r) {
            int mid = partition(nums, l, r);
            if (mid == k) {
                return nums[mid];
            }
            l = mid < k ? mid + 1 : l;
            r = mid > k ? mid - 1 : r;
        }
        // 退出时l==r，即范围内只有一个元素
        return nums[l];
    }

    public int partition(int[] nums, int l, int r) {
        int pivot = nums[l];
        // 逆序排序，大的在左边
        while (l < r) {
            while (l < r && nums[r] <= pivot) {
                r--;
            }
            nums[l] = nums[r];
            while (l < r && nums[l] >= pivot) {
                l++;
            }
            nums[r] = nums[l];
        }
        nums[l] = pivot;
        return l;
    }

    /**
     * 堆排序
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        // 建立容量为k的小根堆，k个最大元素的最小值将为nums[0]
        for (int i = k - 1; i >= 0; i--) {
            heapify(nums, i, k);
        }
        // 把堆大于堆内最小元素的值加入
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > nums[0]) {
                swap(nums, 0, i);
                heapify(nums, 0, k);
            }
        }
        return nums[0];
    }

    public void heapify(int[] nums, int i, int len) {
        int lc = (i << 1) + 1, rc = (i << 1) + 2;
        int min = i;
        // 找到最小的子节点
        min = lc < len && nums[lc] < nums[min] ? lc : min;
        min = rc < len && nums[rc] < nums[min] ? rc : min;
        if (i != min) {
            // 较大元素下沉即较小元素上浮
            swap(nums, i, min);
            // 不能保证子节点还是小根堆，heapify
            heapify(nums, min, len);
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 优先队列
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest3(int[] nums, int k) {
        // 默认小根堆
        Queue<Integer> pq = new PriorityQueue<>(k);
        for (int i = 0; i < k; i++) {
            pq.add(nums[i]);
        }
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > pq.peek()) {
                pq.poll();
                pq.add(nums[i]);
            }
        }
        return pq.peek();
    }
}
