package org.elnino.helper.contest.coding.leetcode.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("unused")
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
                Short.class == clazz || short.class == clazz ||
                Integer.class == clazz || int.class == clazz ||
                Long.class == clazz || long.class == clazz ||
                Float.class == clazz || float.class == clazz ||
                Double.class == clazz || double.class == clazz ||
                Character.class == clazz || char.class == clazz;
    }

    /**
     * find all the methods of a class having certain annotation
     */
    public static Method[] findMethodsByAnnotation(Class<?> clazz, Class<?> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> Arrays.stream(m.getAnnotations()).anyMatch(a -> a.annotationType() == annotation))
                .toArray(Method[]::new);
    }

    /**
     * find all the constructors of a class having certain annotation
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] findConstructorsByAnnotation(Class<T> clazz, Class<?> annotation) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> Arrays.stream(c.getAnnotations()).anyMatch(a -> a.annotationType() == annotation))
                .toArray(Constructor[]::new);
    }
}
