import normal.LongestPalindromicSubstring;
import normal.MaximumXorOfTwoNumbersInAnArray;

import java.util.*;

/**
 * @ClassName: test
 * @Description:
 * @Author: zhaooo
 * @Date: 2023/12/22 14:12
 */
public class test {
    // 注意类名必须为 Main, 不要有任何 package xxx 信息
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[] cs = in.nextLine().toCharArray();
        int low = 0;
        int high = 0;
        for (char c : cs) {
            if ('a' <= c && c <= 'z') {
                low++;
            } else {
                high++;
            }
        }
        int ans = Math.min(low, high);
        if (!Character.isLowerCase(cs[0])) {
            ans = Math.min(ans, high - 1);
        }
        System.out.println(ans);
    }

}
