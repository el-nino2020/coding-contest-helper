package org.elnino.helper.contest.coding.leetcode.utils;

import org.elnino.helper.contest.coding.leetcode.annotation.Constructor;
import org.elnino.helper.contest.coding.leetcode.annotation.Entrance;
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

    @SuppressWarnings("all")
    static class A {
        A() {
        }

        @Constructor
        A(int a, int b) {
        }

        A(String a) {
        }

        @Entrance
        void f1() {
        }

        @Entrance
        int f2(String s) {
            return 0;
        }
    }

    @Test
    void findMethodsByAnnotation() {
        assertEquals(2, ReflectUtils.findMethodsByAnnotation(A.class, Entrance.class).length);
    }

    @Test
    void findConstructorsByAnnotation() {
        assertEquals(1, ReflectUtils.findConstructorsByAnnotation(A.class, Constructor.class).length);
    }
}