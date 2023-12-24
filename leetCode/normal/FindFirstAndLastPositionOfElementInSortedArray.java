package normal;

/**
 * @ClassName: FindFirstAndLastPositionOfElementInSortedArray
 * @Description: 34. 在排序数组中查找元素的第一个和最后一个位置
 * @Author: zhaooo
 * @Date: 2023/12/24 16:16
 */
public class FindFirstAndLastPositionOfElementInSortedArray {
    /**
     * 二分 + 循环不变量
     * 参考 commonDS/BinarySearch.java
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        int left = 0;
        int right = nums.length - 1;
        if (nums.length == 0 || target < nums[left] || target > nums[right]) {
            return result;
        }
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                searchBoundary(nums, left, mid, target, true, result);
                searchBoundary(nums, mid, right, target, false, result);
                break;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    // 搜索边界
    public void searchBoundary(int[] nums, int left, int right, int target, boolean searchL, int[] result) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                if (searchL) {
                    if (mid == 0 || nums[mid - 1] != target) {
                        result[0] = mid;
                        break;
                    } else {
                        right = mid - 1;
                    }
                } else {
                    if (mid == nums.length - 1 || nums[mid + 1] != target) {
                        result[1] = mid;
                        break;
                    } else {
                        left = mid + 1;
                    }
                }
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
    }

    /**
     * 二分 + 循环不变量
     * 参考 commonDS/BinarySearch.java
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange2(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false);
        if (leftIdx < nums.length && rightIdx >= 0 && nums[leftIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }

    // 利用循环不变量分为两个二分查找
    // searchL为true时：[ < target] [l, r] [ >= target]
    // searchL为false时：[ <= target] [l, r] [ > target]
    // 由循环不变量 -> 循环条件/退出条件：[l, r]的长度为0，即[r, l]此时l = r + 1
    // 由循环不变量 -> 循环体：略
    // 由循环不变量 -> 返回值：
    //  searchL为true时：返回[ >= target]的第一个数的下标，即l，因为此时l = r + 1，且r的右侧就是[ >= target]
    //  searchL为false时：返回[ <= target]的最后一个数的下标，即r，因为此时r = l - 1，且l的左侧就是[ <= target]
    // 故返回值是target的位置或者将要插入的位置
    public int binarySearch(int[] nums, int target, boolean searchL) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (right + left) / 2;
            if (nums[mid] == target) {
                left = searchL ? left : mid + 1;
                right = searchL ? mid - 1 : right;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return searchL ? left : right;
    }
}
