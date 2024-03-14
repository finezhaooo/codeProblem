package normal;

/**
 * @ClassName: LongestSubstringWithoutRepeatingCharacters
 * @Description: 3. 无重复字符的最长子串
 * @Author: zhaooo
 * @Date: 2023/10/11 13:41
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        // ASCII可打印字符范围为32-126 长度为95
        // map记录字符上一次出现的序号，从1开始
        int[] map = new int[95];
        int l = 1, r = 1;
        for (; r <= s.length(); r++) {
            int idx = s.charAt(r - 1) - ' ';
            // 当出现相同字符时才判断ans，因为长度在相同之前一直增加，减少判断次数
            if (map[idx] != 0) {
                // [1, l) [l, r) [r, len+1)
                // 其中l和r对应的字符相同，求[l, r)的长度
                ans = Math.max(ans, r - l);
                // 有可能[map[idx], ..., l, ..., r]，即idx对应的元素的上一次出现在l之前
                l = Math.max(map[idx] + 1, l);
                // 在r++之后会变为[l+1, r+1) 窗口内只有一个r
            }
            map[idx] = r;
        }
        // r = len + 1
        ans = Math.max(ans, r - l);
        return ans;
    }
}
