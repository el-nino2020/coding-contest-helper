package org.elnino.helper.contest.coding.leetcode.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReflectUtilsTest {

    @Test
    void isPrimitiveOrBoxing() {
        assertTrue(ReflectUtils.isPrimitiveOrBoxing(int.class));
        assertTrue(ReflectUtils.isPrimitiveOrBoxing(Integer.class));
        assertTrue(ReflectUtils.isPrimitiveOrBoxing(Integer.TYPE));

        assertFalse(ReflectUtils.isPrimitiveOrBoxing(String.class));
        assertFalse(ReflectUtils.isPrimitiveOrBoxing(Void.class));
    }
}