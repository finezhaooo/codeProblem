/**
 * @ClassName: MaximumSwap
 * @Description: 670. 最大交换
 * @Author: zhaooo
 * @Date: 2022/9/13/10:18
 */
public class MaximumSwap {
    /**
     * 单调栈
     * @param num
     * @return
     */
    public int maximumSwap(int num) {
        int[] nums = new int[9];
        int[] stack = new int[9];
        int i = 0, top = -1, res = num;
        while (num > 0) {
            nums[i++] = num % 10;
            num /= 10;
        }
        for (int j = 0; j < i; j++) {
            // 小根堆
            if (top == -1 || nums[j] < nums[stack[top]]) {
                stack[++top] = j;
            } else {
                // 每次将要出栈时改变堆顶和堆底元素在nums中的位置 并比较大小
                res = Math.max(res, numsToNum(nums, i, stack[top], stack[0]));
                // 是>而不是>= 到第一个比他小的位置截至 即最靠经末尾的最小保留
                while (top > -1 && nums[j] > nums[stack[top]]) {
                    top--;
                }
                stack[++top] = j;
            }
        }
        res = Math.max(res, numsToNum(nums, i, stack[top], stack[0]));
        return res;
    }

    void swap(int i, int j, int[] nums) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    int numsToNum(int[] nums, int length, int i, int j) {
        int num = 0;
        // 修改位置
        swap(i, j, nums);
        // 数组转为数
        for (int k = length - 1; k >= 0; k--) {
            num *= 10;
            num += nums[k];
        }
        // 改回位置
        swap(i, j, nums);
        return num;
    }
}
