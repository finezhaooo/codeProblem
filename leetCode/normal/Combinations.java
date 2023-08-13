package normal;

import java.util.*;

/**
 * @ClassName: Combinations
 * @Description: 77. 组合
 * @Author: zhaooo
 * @Date: 2023/08/13 09:03
 */
public class Combinations {
    List<List<Integer>> res;
    int n, k;

    /**
     * dfs
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        res = new ArrayList<>();
        this.n = n;
        this.k = k;
        for (int i = 1; i <= n - k + 1; i++) {
            Integer[] cur = new Integer[k];
            cur[0] = i;
            dfs(i + 1, 1, cur);
        }
        return res;
    }

    void dfs(int start, int idx, Integer[] cur) {
        if (idx == k) {
            res.add(Arrays.asList(cur));
            return;
        }
        // i <= n - k + 1 + idx：剪枝
        for (int i = start; i <= n - k + 1 + idx; i++) {
            Integer[] newList = cur.clone();
            newList[idx] = i;
            dfs(i + 1, idx + 1, newList);
        }
    }


    /**
     * dfs+回溯
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (k <= 0 || n < k) {
            return res;
        }
        // 使用path充当全局temp，不需要解法1的clone()
        Deque<Integer> path = new ArrayDeque<>();
        dfs(n, k, 1, path, res);
        return res;
    }

    private void dfs(int n, int k, int index, Deque<Integer> path, List<List<Integer>> res) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = index; i <= n - (k - path.size()) + 1; i++) {
            path.addLast(i);
            dfs(n, k, i + 1, path, res);
            // 回溯
            path.removeLast();
        }
    }
}
