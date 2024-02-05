package normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: Permutations
 * @Description: 46. 全排列 
 * @Author: zhaooo
 * @Date: 2024/02/05 12:56
 */
public class Permutations {
    List<List<Integer>> ret = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        dfs(nums, 0, new Integer[nums.length], 0);
        return ret;
    }

    void dfs(int[] nums, int idx, Integer[] tmp, int map) {
        if (idx == nums.length) {
            ret.add(Arrays.stream(tmp).collect(Collectors.toList()));
        }
        for (int i = 0; i < nums.length; i++) {
            if ((map & 1 << i) == 0) {
                tmp[idx] = nums[i];
                dfs(nums, idx + 1, tmp, map | 1 << i);
            }
            // 不需要回溯，map已经标定了每个dfs哪些可以访问，对应idx直接覆盖就行
        }
    }
}
