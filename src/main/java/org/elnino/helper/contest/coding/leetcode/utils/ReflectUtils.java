package org.elnino.helper.contest.coding.leetcode.utils;

public class ReflectUtils {
    private ReflectUtils() {
    }

    /**
     * Check whether clazz is a class of primitive type or its boxing type. <br/>
     * Note that {@code Void.class} is not considered as a primitive type in this method
     */
    public static boolean isPrimitiveOrBoxing(Class<?> clazz) {
        return Boolean.class == clazz || boolean.class == clazz ||
                Byte.class == clazz || byte.class == clazz ||
                Short.class == clazz || short.class== clazz ||
                Integer.class == clazz || int.class == clazz ||
                Long.class == clazz || long.class == clazz ||
                Float.class == clazz || float.class == clazz ||
                Double.class == clazz || double.class == clazz ||
                Character.class == clazz || char.class == clazz;
    }
}
