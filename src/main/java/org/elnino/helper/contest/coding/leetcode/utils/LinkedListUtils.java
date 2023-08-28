package org.elnino.helper.contest.coding.leetcode.utils;

import org.elnino.helper.contest.coding.leetcode.annotation.Constructor;

import java.util.ArrayList;


@SuppressWarnings("unused")
public class LinkedListUtils {
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode() {
        }

        @Constructor
        public ListNode(int x) {
            val = x;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        /**
         * return the list view of the linked list headed by the current node
         */
        @Override
        public String toString() {
            ArrayList<Integer> ans = new ArrayList<>();

            ListNode cur = this;

            for (; cur != null; cur = cur.next) {
                ans.add(cur.val);
            }

            return ans.toString();
        }
    }

    public static boolean equal(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) return false;
            l1 = l1.next;
            l2 = l2.next;
        }

        return l1 == null && l2 == null;
    }


}
