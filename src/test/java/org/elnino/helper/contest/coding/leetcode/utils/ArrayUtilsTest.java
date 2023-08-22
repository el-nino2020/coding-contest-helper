package org.elnino.helper.contest.coding.leetcode.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArrayUtilsTest {

    @Test
    void makeObjectArrayClass() {
        Class<?> result = ArrayUtils.makeObjectArrayClass(2, String.class);
        assertEquals(String[][].class, result);

        // TODO: does not implement yet
        assertThrows(RuntimeException.class, () -> {
            Class<?> clazz = ArrayUtils.makeObjectArrayClass(1, int.class);
            assertEquals(int[].class, clazz);
        });

        assertThrows(IllegalArgumentException.class,
                () -> ArrayUtils.makeObjectArrayClass(0, int.class));
    }

    @Test
    void findArrayDimension() {
        int result = ArrayUtils.findArrayDimension(int[][][].class);
        assertEquals(3, result);

        result = ArrayUtils.findArrayDimension(String.class);
        assertEquals(1, result);
    }

    @Test
    void findArrayComponentType() {
        Class<?> result = ArrayUtils.findArrayComponentType(String[][][].class);
        assertEquals(String.class, result);

        result = ArrayUtils.findArrayComponentType(boolean[].class);
        assertEquals(boolean.class, result);

        result = ArrayUtils.findArrayComponentType(boolean.class);
        assertEquals(boolean.class, result);
    }
}