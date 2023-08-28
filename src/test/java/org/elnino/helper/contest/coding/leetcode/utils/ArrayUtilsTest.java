package org.elnino.helper.contest.coding.leetcode.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ArrayUtilsTest {

    @Test
    void makeObjectArrayClass() {
        // test primitives
        assertEquals(boolean[].class, ArrayUtils.makeObjectArrayClass(1, boolean.class));
        assertEquals(byte[].class, ArrayUtils.makeObjectArrayClass(1, byte.class));
        assertEquals(char[].class, ArrayUtils.makeObjectArrayClass(1, char.class));
        assertEquals(double[].class, ArrayUtils.makeObjectArrayClass(1, double.class));
        assertEquals(float[].class, ArrayUtils.makeObjectArrayClass(1, float.class));
        assertEquals(int[].class, ArrayUtils.makeObjectArrayClass(1, int.class));
        assertEquals(long[].class, ArrayUtils.makeObjectArrayClass(1, long.class));
        assertEquals(short[].class, ArrayUtils.makeObjectArrayClass(1, short.class));


        // test primitive and boxing
        assertNotEquals(Integer[].class, ArrayUtils.makeObjectArrayClass(1, int.class));
        assertEquals(Integer[][].class, ArrayUtils.makeObjectArrayClass(2, Integer.class));


        // test other class
        assertEquals(String[][].class, ArrayUtils.makeObjectArrayClass(2, String.class));
        assertEquals(Void[][][].class, ArrayUtils.makeObjectArrayClass(3, Void.class));
    }

    @Test
    void findArrayDimension() {
        int result = ArrayUtils.findArrayDimension(int[][][].class);
        assertEquals(3, result);

        result = ArrayUtils.findArrayDimension(String.class);
        assertEquals(0, result);
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

    @Test
    void testToString() {
        assertEquals("23", ArrayUtils.toString(Integer.class, 23));
        assertEquals("[[1, 2], [3, 4]]",
                ArrayUtils.toString(int[][].class, new int[][]{{1, 2}, {3, 4}}));
    }
}