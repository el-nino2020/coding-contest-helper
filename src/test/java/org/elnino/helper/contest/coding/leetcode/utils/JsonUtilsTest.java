package org.elnino.helper.contest.coding.leetcode.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class JsonUtilsTest {

    @Test
    public void toObject() {
        char[][] arr1 = JsonUtils.toObject("[['a',\"b\", 'c'], ['d',\"e\",\"f\"]]", char[][].class);
        assertArrayEquals(new char[][]{
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
        }, arr1);

        String[] arr2 = JsonUtils.toObject("['acas',\"132313\"]", String[].class);
        assertArrayEquals(new String[]{"acas", "132313"}, arr2);

        int[] arr3 = JsonUtils.toObject("[1,2]", int[].class);
        assertArrayEquals(new int[]{1, 2}, arr3);
    }
}