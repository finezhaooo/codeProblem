package normal;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @ClassName: NumberOfIslands
 * @Description: 200. 岛屿数量
 * @Author: zhaooo
 * @Date: 2024/01/26 14:59
 */
public class NumberOfIslands {
    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * dfs bfs
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length, ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    dfs(i, j, grid);
                    // bfs(i, j, grid);
                    ans++;
                }
            }
        }
        return ans;
    }

    void dfs(int i, int j, char[][] grid) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == '0') return;
        grid[i][j] = '0';
        for (int[] dir : dirs) {
            dfs(i + dir[0], j + dir[1], grid);
        }
    }

    void bfs(int i, int j, char[][] grid) {
        Deque<Integer> iDeque = new LinkedList<>();
        Deque<Integer> jDeque = new LinkedList<>();
        iDeque.addLast(i);
        jDeque.addLast(j);
        while (!iDeque.isEmpty()) {
            i = iDeque.removeFirst();
            j = jDeque.removeFirst();
            for (int[] dir : dirs) {
                if (i + dir[0] < 0 || i + dir[0] >= grid.length || j + dir[1] < 0 || j + dir[1] >= grid[0].length || grid[i + dir[0]][j + dir[1]] == '0')
                    continue;
                // 提前置0，防止在本次bfs中重复添加
                grid[i + dir[0]][j + dir[1]] = '0';
                iDeque.addLast(i + dir[0]);
                jDeque.addLast(j + dir[1]);
            }
        }
    }
}
