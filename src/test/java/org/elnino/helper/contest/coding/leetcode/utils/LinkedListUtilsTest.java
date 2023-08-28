package org.elnino.helper.contest.coding.leetcode.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.elnino.helper.contest.coding.leetcode.utils.LinkedListUtils.ListNode;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListUtilsTest {
    @Nested
    class LinkedList {
        @Test
        public void testToString() {
            ListNode listNode = new ListNode(1, new ListNode(2));
            assertEquals(listNode.toString(), "[1, 2]");
        }
    }

    @Test
    void equal() {
        ListNode l1 = new ListNode(1, new ListNode(2));
        ListNode l2 = new ListNode(1, new ListNode(2));
        assertTrue(LinkedListUtils.equal(l1, l2));
        assertTrue(LinkedListUtils.equal(l2, l1));

        l2 = new ListNode(1);
        assertFalse(LinkedListUtils.equal(l1, l2));
        assertFalse(LinkedListUtils.equal(l2, l1));


        l2 = new ListNode(3);
        assertFalse(LinkedListUtils.equal(l1, l2));
        assertFalse(LinkedListUtils.equal(l2, l1));

        assertFalse(LinkedListUtils.equal(l1, null));
        assertFalse(LinkedListUtils.equal(null, l1));
    }


}