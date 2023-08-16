package normal;

/**
 * @ClassName: MaximumSubarray
 * @Description: 53. 最大子数组和
 * @Author: zhaooo
 * @Date: 2023/8/16/10:10
 */
public class MaximumSubarray {
    /**
     * 动态规划
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        // dp[i] 定义为数组nums 中已num[i] 结尾的最大连续子串和
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            if (max < dp[i]) {
                max = dp[i];
            }
        }
        return max;
    }

    /**
     * 贪心（可以由动态规划+使用一个变量保存转变来）
     *
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        int ans = nums[0];
        // sum表示以当前num的前一个元素结尾的最大值
        int sum = 0;
        for (int num : nums) {
            // 如果 sum > 0，则说明 sum 对结果有增益效果
            if (sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    /**
     * 分治法
     * https://leetcode.cn/problems/maximum-subarray/solutions/94638/zheng-li-yi-xia-kan-de-dong-de-da-an-by-lizhiqiang/?envType=study-plan-v2&envId=top-interview-150
     *
     * @param nums
     * @return
     */
    public int maxSubArray3(int[] nums) {
        return maxSubArrayDivideWithBorder(nums, 0, nums.length - 1);
    }

    private int maxSubArrayDivideWithBorder(int[] nums, int start, int end) {
        if (start == end) {
            // 只有一个元素，也就是递归的结束情况
            return nums[start];
        }

        // 计算中间值
        int center = (start + end) / 2;
        // 计算左侧子序列最大值
        int leftMax = maxSubArrayDivideWithBorder(nums, start, center);
        // 计算右侧子序列最大值
        int rightMax = maxSubArrayDivideWithBorder(nums, center + 1, end);

        // 下面计算横跨两个子序列的最大值

        // 计算包含左侧子序列最后一个元素的子序列最大值
        int leftCrossMax = Integer.MIN_VALUE; // 初始化一个值
        int leftCrossSum = 0;
        for (int i = center; i >= start; i--) {
            leftCrossSum += nums[i];
            leftCrossMax = Math.max(leftCrossSum, leftCrossMax);
        }

        // 计算包含右侧子序列最后一个元素的子序列最大值
        int rightCrossMax = nums[center + 1];
        int rightCrossSum = 0;
        for (int i = center + 1; i <= end; i++) {
            rightCrossSum += nums[i];
            rightCrossMax = Math.max(rightCrossSum, rightCrossMax);
        }

        // 计算跨中心的子序列的最大值
        int crossMax = leftCrossMax + rightCrossMax;

        // 比较三者，返回最大值
        return Math.max(crossMax, Math.max(leftMax, rightMax));
    }
}
