package normal;

/**
 * @ClassName: SearchInRotatedSortedArray
 * @Description: 33. 搜索旋转排序数组
 * @Author: zhaooo
 * @Date: 2024/01/22 19:53
 */
public class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // mid在左边
            if (nums[0] <= nums[mid]) {
                // target在左边，同时target<nums[mid]
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
                // mid在右边
            } else {
                // target在右边，同时target>nums[mid]
                if (nums[mid] < target && target <= nums[n - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }
}
