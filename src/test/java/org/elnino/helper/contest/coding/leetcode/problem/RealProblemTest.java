package org.elnino.helper.contest.coding.leetcode.problem;

import org.elnino.helper.contest.coding.leetcode.utils.SolutionUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class RealProblemTest {

    /**
     * test problem that uses built-in type
     */
    @Test
    public void testCommonMethod() throws InvocationTargetException, IllegalAccessException {
        String[] args = {
                "[2,7,11,15]",
                "9",
                "[3,2,4]",
                "6",
                "[3,3]",
                "6"
        };
        Object[] result = SolutionUtils.solve(new Solution1(), args, false);
        assertArrayEquals(new int[]{1, 0}, (int[]) result[0]);
        assertArrayEquals(new int[]{2, 1}, (int[]) result[1]);
        assertArrayEquals(new int[]{1, 0}, (int[]) result[0]);
    }

    /**
     * test problem that focuses on binary tree
     */
    @Test
    public void testBinaryTree() throws InvocationTargetException, IllegalAccessException {
        String[] args = {
                "[1,null,2,3]",
                "[]",
                "[1]",
                "[1,2]",
                "[1,null,2]"
        };
        Object[] result = SolutionUtils.solve(new Solution2(), args, false);
        assertEquals("[1, 2, 3]", result[0].toString());
        assertEquals("[]", result[1].toString());
        assertEquals("[1]", result[2].toString());
        assertEquals("[1, 2]", result[3].toString());
        assertEquals("[1, 2]", result[4].toString());
    }

    /**
     * test problem that focuses on linked list
     */
    @Test
    public void testLinkedList() throws InvocationTargetException, IllegalAccessException {
        String[] args = {
                "[1,2,3,4,5]", "2",
                "[1]", "1",
                "[1,2]", "1"
        };
        Object[] result = SolutionUtils.solve(new Solution3(), args, false);
        assertEquals("[1, 2, 3, 5]", result[0].toString());
        assertNull(result[1]);
        assertEquals("[1]", result[2].toString());
    }
}
