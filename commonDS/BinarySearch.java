/**
 * @ClassName: BinarySearch
 * @Description: 二分查找
 * 开闭区间确定  退出条件 和 区间变化
 * 使mid不在结果区间内
 * @Author: zhaooo
 * @Date: 2022/8/28/20:28
 */
public class BinarySearch {

    /**
     * 闭区间
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 左闭右开 [ )
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                // mid不在当前访问区间内
                l = mid + 1;
            } else {
                // mid不在当前访问区间内
                r = mid;
            }
        }
        return -1;
    }

    /**
     * 闭区间递归
     * @param nums
     * @param target
     * @return
     */
    public int search3(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        return dfs(nums, l, r, target);
    }

    public int dfs(int[] nums, int l, int r, int target) {
        if (l > r) {
            return -1;
        }
        int mid = (l + r) >> 1;
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[mid] < target) {
            return dfs(nums, mid + 1, r, target);
        }
        return dfs(nums, l, mid - 1, target);
    }

    /**
     * 开区间递归
     * @param nums
     * @param target
     * @return
     */
    public int search4(int[] nums, int target) {
        int l = 0, r = nums.length;
        return dfs2(nums, l, r, target);
    }

    public int dfs2(int[] nums, int l, int r, int target) {
        if (l == r) {
            return -1;
        }
        int mid = (l + r) >> 1;
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[mid] < target) {
            return dfs2(nums, mid + 1, r, target);
        }
        return dfs2(nums, l, mid, target);
    }
}
