import dependency.TreeNode;

import java.util.*;

/**
 * @ClassName: TreeOrder
 * @Description: 树的遍历
 * @Author: zhaooo
 * @Date: 2022/7/13/10:55
 */
public class TreeOrder {
    public void preOrder(List<Integer> result, TreeNode node) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        preOrder(result, node.left);
        preOrder(result, node.right);
    }

    public void preOrder2(List<Integer> result, TreeNode node) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.addLast(node);
        // 每次取栈顶元素加入结果，再右孩子入栈，左孩子入栈（左孩子先访问）
        while (!stack.isEmpty()) {
            TreeNode tmpNode = stack.removeLast();
            result.add(tmpNode.val);
            if (tmpNode.right != null) {
                stack.addLast(tmpNode.right);
            }
            if (tmpNode.left != null) {
                stack.addLast(tmpNode.left);
            }
        }
    }

    public void inOrder(List<Integer> result, TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(result, node.left);
        result.add(node.val);
        inOrder(result, node.right);
    }

    public void in2(List<Integer> result, TreeNode node) {
        //1、申请一个栈stack，初始时令cur=head
        //2、先把cur压入栈中，依次把左边界压入栈中，即不停的令cur=cur.left，重复步骤2（找到最左节点）
        //3、不断重复2，直到为null，从stack中弹出一个节点，记为node，打印node的值，并令cur=node.right,重复步骤2（访问最左节点的右孩子，即向下）
        //4、当stack为空且cur为空时，整个过程停止。
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (node != null || !stack.isEmpty()) {
            // 找到最左节点
            while (node != null) {
                stack.addLast(node);
                node = node.left;
            }
            // 没有左孩子就弹出
            node = stack.removeLast();
            // 左孩子已经访问过，现在访问根节点（左，根，右）
            result.add(node.val);
            // 左节点已经访问了
            node = node.right;
        }
    }


    public void postOrder(List<Integer> result, TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(result, node.left);
        postOrder(result, node.right);
        result.add(node.val);
    }

    public void postOrder2(List<Integer> result, TreeNode node) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode visited = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.addLast(node);
                node = node.left;
            }
            // 最左节点（无左孩子）
            node = stack.removeLast();
            // 每次访问时要判断该节点有没有右孩子
            // 如果没有右孩子或者右孩子已经访问，就向上返回并添加当前结果
            if (node.right == null || node.right == visited) {
                // 向上返回时设置当前节点以访问
                visited = node;
                result.add(node.val);
                node = null;
                // 否则向右重复过程
            } else {
                // 把右孩子的父节点添加上
                stack.addLast(node);
                node = node.right;
            }
        }
    }

    public void levelOrder(LinkedList<Integer> result, TreeNode node) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            result.add(node.val);
            if (node.right != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.left);
            }
        }
    }
}
