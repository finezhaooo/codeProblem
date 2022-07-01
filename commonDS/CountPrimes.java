/**
 * @ClassName: CountPrimes
 * @Description: 质数
 * @Author: zhaooo
 * @Date: 2022/6/30/22:14
 */
public class CountPrimes {
    /**
     * 埃式筛
     * @param n
     * @return
     */
    int NumbersOfPrime(int n) {
        if (n < 1) {
            return 0;
        }
        boolean[] notPrimes = new boolean[n + 1];
        int res = 1;
        for (int i = 2; i <= n; i++) {
            if (!notPrimes[i]) {
                continue;
            }
            for (int j = 2; j * i <= n; j++) {
                notPrimes[j * i] = true;
            }
            res++;
        }
        return res;
    }
}
