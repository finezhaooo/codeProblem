/**
 * @ClassName: SegmentTreeDynamic
 * @Description: 线段树（动态开点）
 * 使用该数据结构的题:
 *  MyCalendarI
 *  RangeModule
 *  RectangleAreaII
 * @Author: zhaooo
 * @Date: 2022/6/20/12:14
 */
public class SegmentTreeDynamic {
    class Node {
        Node left, right;
        // val是该节点的值，本代码中是该线段的总和
        // add是懒标记即该点区间内每个点要更新的值
        int val, add;
    }

    // 在区间 [l, r] 中更新区间 [start, end] 的结果
    public void update(Node node, int start, int end, int l, int r, int add) {
        // 要更新的区间（即[l, r]）大于该点（该点范围为[start, end]）时懒更新
        if (l <= start && end <= r) {
            node.val += (end - start + 1) * add;
            //? 也可为node.add=add 因为原add要么为0，要么已经包含在node.val里
            // 更新懒标记
            node.add += add;
            return;
        }
        // [l, r]不能完全包裹[start, end]
        int mid = (start + end) >> 1;
        // 开点和更新
        pushDown(node, mid - start + 1, end - mid);
        // 递归查询
        // [l,r]包含该点的左孩子
        if (l <= mid) {
            update(node.left, start, mid, l, r, add);
        }
        // [l,r]包含该点的右孩子
        if (r > mid) {
            update(node.right, mid + 1, end, l, r, add);
        }
        // 向上更新结果
        pushUp(node);
    }

    public int query(Node node, int start, int end, int l, int r) {
        // 该点（[start, end]）在查询区间（[l, r]）内
        if (l <= start && end <= r) {
            return node.val;
        }
        int mid = (start + end) >> 1, ans = 0;
        // 开点和更新
        pushDown(node, mid - start + 1, end - mid);
        // 结果可以拆分为不同区间的结果
        // [l,r]包含该点的左孩子
        if (l <= mid) {
            ans += query(node.left, start, mid, l, r);
        }
        // [l,r]包含该点的右孩子
        if (r > mid) {
            ans += query(node.right, mid + 1, end, l, r);
        }
        return ans;
    }

    private void pushUp(Node node) {
        node.val = node.left.val + node.right.val;
    }

    private void pushDown(Node node, int leftNum, int rightNum) {
        if (node.left == null) {
            node.left = new Node();
        }
        if (node.right == null) {
            node.right = new Node();
        }
        if (node.add == 0) {
            return;
        }
        // 对某个节点的懒标记进行下放时，左右儿子的标记也要更新，并且该节点的标记值要清零，以防重复下放
        node.left.val += node.add * leftNum;
        node.right.val += node.add * rightNum;
        node.left.add = node.add;
        node.right.add = node.add;
        node.add = 0;
    }
}
