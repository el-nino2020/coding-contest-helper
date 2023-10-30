package org.elnino.helper.contest.coding.leetcode.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings({"unused"})
public final class ArrayUtils {
    private ArrayUtils() {
    }

    /**
     * <p>
     * The <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getName--">class
     * name of an array</a> is not intuitive. Given a component type,
     * it is not easy to get the class object of an array of this component type
     * by the method {@code Class.forName(className)}.
     *
     * <p>
     * The method provides an simple way to achieve this. Here is an example:
     * <pre>{@code
     *   Class<?> aClass = ArrayUtils.makeObjectArrayClass(3, String.class);
     *   System.out.println(aClass.getSimpleName());
     *   // output String[][][]
     * }<pre/>
     *
     * @param dimension dimension of the array
     * @param clazz     component of the array
     * @return the class object of a {@code dimension}-d array whose component is {@code clazz}
     */
    public static Class<?> makeObjectArrayClass(int dimension, Class<?> clazz) {
        if (!(dimension >= 1)) {
            throw new IllegalArgumentException("dimension should be greater than 0");
        }
        StringBuilder arraySymbol = new StringBuilder(dimension);
        for (int i = 0; i < dimension; i++) {
            arraySymbol.append('[');
        }
        String className;

        if (clazz == boolean.class) {
            className = arraySymbol + "Z";
        } else if (clazz == byte.class) {
            className = arraySymbol + "B";
        } else if (clazz == char.class) {
            className = arraySymbol + "C";
        } else if (clazz == double.class) {
            className = arraySymbol + "D";
        } else if (clazz == float.class) {
            className = arraySymbol + "F";
        } else if (clazz == int.class) {
            className = arraySymbol + "I";
        } else if (clazz == long.class) {
            className = arraySymbol + "J";
        } else if (clazz == short.class) {
            className = arraySymbol + "S";
        } else {
            className = String.format("%sL%s;", arraySymbol, clazz.getCanonicalName());
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int findArrayDimension(Class<?> clazz) {
        int ans = 0;
        while (clazz.isArray()) {
            ans++;
            clazz = clazz.getComponentType();
        }
        return ans;
    }

    public static Class<?> findArrayComponentType(Class<?> clazz) {
        while (clazz.isArray()) {
            clazz = clazz.getComponentType();
        }
        return clazz;
    }

    /**
     * recursively calls the toString() method from the base component of the
     * array so that a multiple-dimensional array can be properly shown when
     * outputted
     */
    public static String toString(Class<?> clazz, Object arr) {
        if (!clazz.isArray()) {
            return arr.toString();
        }
        ArrayList<Object> ans = new ArrayList<>();
        int n = Array.getLength(arr);
        for (int i = 0; i < n; i++) {
            ans.add(toString(clazz.getComponentType(), Array.get(arr, i)));
        }
        return ans.toString();
    }

    /**
     * If input is the same as a json array, then parse it into array of string
     * <br/><br/>
     * Here is an example: <br/>
     * input = "['str', 123, true]" <br/>
     * ans = {"str", "123", "true" } <br/><br/>
     * If input represents only 1 element, then it is not necessarily in the form
     * of a json array, it can be "123" or "[123]". In either case, it is true.
     */
    public static String[] parse(String input) {
        input = input.trim();
        boolean begin = input.charAt(0) == '[';
        boolean end = input.charAt(input.length() - 1) == ']';

        if (begin ^ end) {
            throw new RuntimeException("wrong format of input, see the document of this method");
        } else if (!begin) {
            return new String[]{input};
        }

        return Arrays.stream(input.substring(1, input.length() - 1).split(","))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(s -> {
                    int len = s.length();
                    if ((s.charAt(0) == '"' && s.charAt(len - 1) == '"') ||
                            (s.charAt(0) == '\'' && s.charAt(len - 1) == '\'')) {
                        return s.substring(1, len - 1);
                    } else {
                        return s;
                    }
                })
                .toArray(String[]::new);
    }
}

