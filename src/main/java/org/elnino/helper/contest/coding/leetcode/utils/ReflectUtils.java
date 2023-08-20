package org.elnino.helper.contest.coding.leetcode.utils;

public class ReflectUtils {
    private ReflectUtils() {
    }

    /**
     * Check whether clazz is a class of primitive type or its boxing type. <br/>
     * Note that {@code Void.class} is not considered as a primitive type in this method
     */
    public static boolean isPrimitiveOrBoxing(Class<?> clazz) {
        return Boolean.class == clazz || Boolean.TYPE == clazz ||
                Byte.class == clazz || Byte.TYPE == clazz ||
                Short.class == clazz || Short.TYPE == clazz ||
                Integer.class == clazz || Integer.TYPE == clazz ||
                Long.class == clazz || Long.TYPE == clazz ||
                Float.class == clazz || Float.TYPE == clazz ||
                Double.class == clazz || Double.TYPE == clazz ||
                Character.class == clazz || Character.TYPE == clazz;
    }
}
