package org.elnino.helper.contest.coding.leetcode.problem;

import static org.elnino.helper.contest.coding.leetcode.utils.LinkedListUtils.ListNode;

/**
 * <a href="https://leetcode.com/problems/remove-nth-node-from-end-of-list/">
 * LeetCode Remove Nth Node From End of List U.S. site</a>
 * <br/>
 * <a href="https://leetcode.cn/problems/remove-nth-node-from-end-of-list">
 * LeetCode Remove Nth Node From End of List China site</a>
 */
class Solution3 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode ans = new ListNode(0, head);

        ListNode right = ans, left = ans;
        while (n > 0) {
            right = right.next;
            n--;
        }
        while (right.next != null) {
            right = right.next;
            left = left.next;
        }
        left.next = left.next.next;

        return ans.next;
    }
}
