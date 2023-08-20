package org.elnino.helper.contest.coding.leetcode.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@SuppressWarnings({"unused"})
public final class ConvertUtils {
    private ConvertUtils() {
    }

    public static Object convert(Class<?> clazz, String value) {
        if (ReflectUtils.isPrimitiveOrBoxing(clazz)) {
            return convertPrimitive(clazz, value);
        } else if (clazz == String.class) {
            // drop quotation marks
            return value.substring(1, value.length() - 1);
        } else if (clazz.isArray()) {
            return convertArray(clazz, value);
        } else {
            return convertCustomClass(clazz, value);
        }
//        throw new RuntimeException(String.format("can not convert this clazz: %s", clazz.getName()));
    }

    public static Object convertArray(Class<?> clazz, String val) {
        Class<?> componentType = ArrayUtils.findArrayComponentType(clazz);
        if (ReflectUtils.isPrimitiveOrBoxing(componentType) || componentType == String.class) {
            return JsonUtils.toObject(val, clazz);
        } else {// array of custom class
            // 1. convert val to string arrays as the arg of constructor of clazz
            int dimension = ArrayUtils.findArrayDimension(clazz);
            Object[] arr = (Object[]) JsonUtils.toObject(val, ArrayUtils.makeObjectArrayClass(dimension, String.class));
            // 2. build the object array
            return convertCustomObjectArray(arr, 0, clazz.getComponentType());
        }
    }

    // a recursive function to create multi-dimension array of custom class object
    private static Object convertCustomObjectArray(Object[] args, int index, Class<?> componentType) {
        // TODO: treat args as a 1d string array first
        Object ans = Array.newInstance(componentType, args.length);
        for (int i = 0; i < args.length; i++) {
            Array.set(ans, i, convertCustomClass(componentType, (String) args[i]));
        }
        return ans;
    }


    @Deprecated
    public static Object convert1DArray(Class<?> clazz, String val) {
        Class<?> componentClass = clazz.getComponentType();
        if (!clazz.isArray() || componentClass.isArray()) {
            throw new IllegalArgumentException(String.format("%s is not a 1D array", clazz.getName()));
        }
        // drop '[' and ']'
        val = val.substring(1, val.length() - 1);
        String[] elements = Arrays.stream(val.split(",")).map(String::trim).filter(s -> s.length() > 0).toArray(String[]::new);
        Object ans = Array.newInstance(componentClass, elements.length);

        for (int i = 0; i < elements.length; i++) {
            Array.set(ans, i, elements[i]);
        }

        return ans;
    }


    /**
     * initialize an object by calling its 1-arg constructor
     */
    public static Object convertCustomClass(Class<?> clazz, String arg) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        try {
            int count = 0;
            // find constructor with 1 args
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == 1) count++;
            }
            if (count != 1) {
                throw new RuntimeException("can not create instance of class " + clazz.getName());
            }
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == 1) {
                    return constructor.newInstance(convert(constructor.getParameterTypes()[0], arg));
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("can not create instance of class " + clazz.getName());
        }
        throw new RuntimeException("can not create instance of class " + clazz.getName());
    }

    /**
     * convert literal value to primitive types: boolean, byte, short, integer, long, float, double, character
     *
     * @param clazz class of primitive type or its boxing type
     * @param value literal value
     * @return the primitive type object. note that you should handle the return value by yourself
     */
    public static Object convertPrimitive(Class<?> clazz, String value) {
        if (!ReflectUtils.isPrimitiveOrBoxing(clazz)) {
            throw new IllegalArgumentException(String.format("%s is not a primitive type or its boxing class", clazz.getName()));
        }

        if (Boolean.class == clazz || Boolean.TYPE == clazz)
            return convertBoolean(value);
        if (Byte.class == clazz || Byte.TYPE == clazz)
            return convertByte(value);
        if (Short.class == clazz || Short.TYPE == clazz)
            return convertShort(value);
        if (Integer.class == clazz || Integer.TYPE == clazz)
            return convertInt(value);
        if (Long.class == clazz || Long.TYPE == clazz)
            return convertLong(value);
        if (Float.class == clazz || Float.TYPE == clazz)
            return convertFloat(value);
        if (Double.class == clazz || Double.TYPE == clazz)
            return convertDouble(value);
        if (Character.class == clazz || Character.TYPE == clazz)
            return convertChar(value);

        throw new Error("UNREACHABLE");
    }

    public static boolean convertBoolean(String val) {
        return Boolean.parseBoolean(val);
    }

    public static byte convertByte(String val) {
        return Byte.parseByte(val);
    }

    public static char convertChar(String val) {
        if (val == null || val.length() != 1) {
            throw new IllegalArgumentException("val should be of 1 character");
        }
        return val.charAt(0);
    }

    public static short convertShort(String val) {
        return Short.parseShort(val);
    }

    public static int convertInt(String val) {
        return Integer.parseInt(val);
    }

    public static long convertLong(String val) {
        return Long.parseLong(val);
    }

    public static float convertFloat(String val) {
        return Float.parseFloat(val);
    }

    public static double convertDouble(String val) {
        return Double.parseDouble(val);
    }


}
