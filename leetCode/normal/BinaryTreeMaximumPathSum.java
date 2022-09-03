package normal;

import dependency.TreeNode;

/**
 * @ClassName: BinaryTreeMaximumPathSum
 * @Description: 124. 二叉树中的最大路径和
 * @Author: zhaooo
 * @Date: 2022/9/3/16:54
 */
public class BinaryTreeMaximumPathSum {
    // 初始化为最小值
    int ret = Integer.MIN_VALUE;

    /**
     * 模板解法
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root);
        return ret;
    }

    int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // max(dfs(),0)表示在 此处取截至或向下 的最大值
        // 递归
        int l = Math.max(0, dfs(root.left));
        int r = Math.max(0, dfs(root.right));
        ret = Math.max(l + r + root.val, ret);
        return Math.max(l, r) + root.val;
    }
}
