package normal;

import java.util.Arrays;

/**
 * @ClassName: LongestSubstringWithoutRepeatingCharacters
 * @Description: 3. 无重复字符的最长子串
 * @Author: zhaooo
 * @Date: 2023/10/11 13:41
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        // 126 - 32 + 1 = 95
        // 126：~    32：空格
        // 保存字符在s中的下标+1
        int[] map = new int[95];
        Arrays.fill(map, -1);
        int l = 0, r = 0;
        for (; r < s.length(); r++) {
            int idx = s.charAt(r) - 32;
            // 当出现相同字符时才判断ans，减少判断次数
            if (map[idx] != -1) {
                // [l, r) : (r-1) - l + 1 = r - l
                ans = Math.max(ans, r - l);
                // 有可能[map[idx], .., l, .., r]，即idx对应的元素的上一次出现在l之前
                l = Math.max(map[idx] + 1, l);
            }
            map[idx] = r;
        }
        // r = s.length()
        ans = Math.max(ans, r - l);
        return ans;
    }
}
