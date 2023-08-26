package org.elnino.helper.contest.coding.leetcode.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

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


    @Disabled
    void convert1DArray() {
    }


    static class A {
        int val;

        public A(int val) {
            this.val = val;
        }
    }

    static class B {
        int val;
    }

    static class C {
        int val;

        private C(int val) {
            this.val = val;
        }
    }

    @Test
    void convertCustomClass() {
        Object o = ConvertUtils.convertCustomClass(A.class, "-5");
        assertEquals(A.class, o.getClass());
        A a = (A) o;
        assertEquals(-5, a.val);

        assertThrows(RuntimeException.class, () -> {
            ConvertUtils.convertCustomClass(B.class, "3");
        });

        assertThrows(RuntimeException.class, () -> {
            ConvertUtils.convertCustomClass(C.class, "3");
        });

        class D {
            int val;

            public D(int val) {
                this.val = val;
            }
        }
        assertThrows(RuntimeException.class, () -> {
            ConvertUtils.convertCustomClass(D.class, "3");
        });
    }

    @Test
    void convertArray() {
        assertThrows(IllegalArgumentException.class, () -> ConvertUtils.convertArray(int.class, "1"));
        // primitive class
        assertArrayEquals(new int[]{1, 2, 3}, ConvertUtils.convertArray(int[].class, "[1, 2, 3]"));
        assertArrayEquals(new Integer[][]{{1, 2, 3}, {4, 5, 6}},
                ConvertUtils.convertArray(Integer[][].class, "[[1,2,3],[4,5,6]]"));
        // custom class
        int[] args1 = {1, 2, 3, 4};
        A[] arr1 = ConvertUtils.convertArray(A[].class, Arrays.toString(args1));
        IntStream.range(0, args1.length).forEach(i -> assertEquals(args1[i], arr1[i].val));

        int[][] args2 = {{5, 6}, {7, 8}};
        A[][] arr2 = ConvertUtils.convertArray(A[][].class, "[[5,6],[7,8]]");
        IntStream.range(0, args2.length).forEach(i -> {
            IntStream.range(0, args2[i].length).forEach(j -> {
                assertEquals(args2[i][j], arr2[i][j].val);
            });
        });
    }


    @Test
    void convert() {
        assertEquals(12.3d, ConvertUtils.convert(double.class, "12.3"));
        assertEquals("raft", ConvertUtils.convert(String.class, "'raft'"));
        assertArrayEquals(new boolean[]{true, false, true},
                ConvertUtils.convert(boolean[].class, "[true, false, true]"));
        assertEquals(-2, ConvertUtils.convert(A.class, "-2").val);
    }


}