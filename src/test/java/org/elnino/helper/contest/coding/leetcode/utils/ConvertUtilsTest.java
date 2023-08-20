package org.elnino.helper.contest.coding.leetcode.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertUtilsTest {
    @Test
    void convertBoolean() {
        assertTrue(ConvertUtils.convertBoolean("True"));
        assertFalse(ConvertUtils.convertBoolean("false"));
    }

    @Test
    void convertByte() {
        assertEquals((byte) 1, ConvertUtils.convertByte("1"));
        assertNotEquals((byte) 1, ConvertUtils.convertByte("2"));
    }

    @Test
    void convertChar() {
        assertEquals('c', ConvertUtils.convertChar("c"));
        assertNotEquals('c', ConvertUtils.convertChar("d"));

        assertThrows(IllegalArgumentException.class, () -> ConvertUtils.convertChar("abbd"));
        assertThrows(IllegalArgumentException.class, () -> ConvertUtils.convertChar(null));
    }

    @Test
    void convertShort() {
        assertEquals((short) 1, ConvertUtils.convertShort("1"));
        assertNotEquals((short) 1, ConvertUtils.convertShort("2"));
    }

    @Test
    void convertInt() {
        assertEquals(1, ConvertUtils.convertInt("1"));
        assertNotEquals(1, ConvertUtils.convertInt("2"));
    }

    @Test
    void convertLong() {
        assertEquals(1L, ConvertUtils.convertLong("1"));
        assertNotEquals(1L, ConvertUtils.convertLong("2"));
    }

    @Test
    void convertFloat() {
        assertEquals(1.2f, ConvertUtils.convertFloat("1.2"));
        assertNotEquals(1f, ConvertUtils.convertFloat("2"));
    }

    @Test
    void convertDouble() {
        assertEquals(1.2d, ConvertUtils.convertDouble("1.2"));
        assertNotEquals(1d, ConvertUtils.convertDouble("2"));
    }

    @Test
    void convertPrimitive() {
        assertThrows(IllegalArgumentException.class,
                () -> ConvertUtils.convertPrimitive(String.class, "abc"));
        assertThrows(IllegalArgumentException.class,
                () -> ConvertUtils.convertPrimitive(Void.class, "abc"));

        assertEquals(1, ConvertUtils.convertPrimitive(Integer.TYPE, "1"));
        assertEquals(1, ConvertUtils.convertPrimitive(Integer.class, "1"));
    }

    @Test
    void convertArray() {
    }

    @Disabled
    void convert1DArray() {
    }

    @Test
    void convertCustomClass() {
    }


    @Test
    void convert() {
    }


}