import normal.MaximumXorOfTwoNumbersInAnArray;

/**
 * @ClassName: test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/22 14:12
 */
public class test {

    public static void main(String[] args) {
        System.out.println(binarySearch(new int[]{1, 3, 5, 6}, -1, true));
    }

    public static int binarySearch(int[] nums, int target, boolean searchL) {
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
