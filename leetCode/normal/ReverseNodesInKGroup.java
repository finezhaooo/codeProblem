package normal;

import dependency.ListNode;

/**
 * @ClassName: ReverseNodesInKGroup
 * @Description: 25. K 个一组翻转链表
 * @Author: zhaooo
 * @Date: 2023/3/14/10:26
 */
public class ReverseNodesInKGroup {
    // 记录当前反转次数
    int counter = 0;

    /**
     * 记录反转部分的头尾节点
     * O(1)的空间复杂度
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        counter = k;
        ListNode ret = reverseList(head, k);
        // pre记录已反转部分尾节点
        ListNode pre = head;
        // 还有可以反转的部分
        while (head.next != null) {
            counter = k;
            // pre记录已反转部分尾节点
            pre = head;
            // tmp记录未反转部分的头节点
            ListNode tmp = head.next;
            // 反转后tmp为已反转部分尾节点
            head.next = reverseList(tmp, k);
            head = tmp;
        }
        // 最后一组不足k个则反转回去
        pre.next = (counter == 1 ? pre.next : reverseList(pre.next, k));
        return ret;
    }


    ListNode reverseList(ListNode head, int k) {
        // cur记录已经反转部分的头节点
        ListNode cur = head;
        while (k > 1 && head.next != null) {
            ListNode tmp = head.next.next;
            head.next.next = cur;
            // head.next保存原链表cur的下一个，即未反转部分的头节点
            cur = head.next;
            head.next = tmp;
            k--;
        }
        counter = k;
        // 此时tmp(head.next)为下一组要反转节点的头节点
        return cur;
    }
}
