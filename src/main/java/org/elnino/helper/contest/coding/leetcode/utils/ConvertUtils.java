package org.elnino.helper.contest.coding.leetcode.utils;

import org.elnino.helper.contest.coding.leetcode.annotation.Construct;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.elnino.helper.contest.coding.leetcode.utils.BinaryTreeUtils.TreeNode;
import static org.elnino.helper.contest.coding.leetcode.utils.LinkedListUtils.ListNode;

@SuppressWarnings({"unused"})
public final class ConvertUtils {
    private ConvertUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> clazz, String value) {
        if (ReflectUtils.isPrimitiveOrBoxing(clazz)) {
            return (T) convertPrimitive(clazz, value);
        } else if (clazz == String.class) {
            // drop quotation marks
            return (T) value.substring(1, value.length() - 1);
        } else if (clazz.isArray()) {
            return convertArray(clazz, value);
        } else {
            return (T) convertCustomClass(clazz, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertArray(Class<T> clazz, String val) {
        if (!clazz.isArray()) {
            throw new IllegalArgumentException(String.format("%s is not a class of array", clazz.getName()));
        }

        Class<?> componentType = ArrayUtils.findArrayComponentType(clazz);

        if (ReflectUtils.isPrimitiveOrBoxing(componentType) || componentType == String.class) {
            return JsonUtils.toObject(val, clazz);
        } else { // array of custom class
            // 1. convert val to string arrays as the arg of constructor of clazz
            int dimension = ArrayUtils.findArrayDimension(clazz);
            Object[] arr = (Object[]) JsonUtils.toObject(val, ArrayUtils.makeObjectArrayClass(dimension, String.class));
            // 2. build the object array
            return (T) convertCustomObjectArray(arr, clazz.getComponentType());
        }
    }

    // a recursive function to create multi-dimension array of custom class object
    private static Object[] convertCustomObjectArray(Object[] args, Class<?> componentType) {
        Object[] ans = (Object[]) Array.newInstance(componentType, args.length);

        if (!componentType.isArray()) {
            for (int i = 0; i < args.length; i++) {
                ans[i] = convertCustomClass(componentType, (String) args[i]);
            }
        } else {
            for (int i = 0; i < args.length; i++) {
                ans[i] = convertCustomObjectArray((Object[]) Array.get(args, i), componentType.getComponentType());
            }
        }

        return ans;
    }

    /**
     * Initialize an object by calling the constructor of its class. <br/>
     * The method will do the following to find which constructor to use:
     * <p>
     * 1. Find the constructor with {@code @Construct} Annotation <br/>
     * 2. If there is no such constructor, find any constructor of the class
     * <p>
     * Note that in either case, there should be exactly one such constructor.
     * Otherwise, it is assumed that no method can be found.
     * <p>
     * If clazz is an inner class, make it static, otherwise there will be exception thrown
     */
    public static Object convertCustomClass(Class<?> clazz, String input) {
        if (clazz == ListNode.class) {
            return convertLinkedList(input);
        } else if (clazz == TreeNode.class) {
            return convertBinaryTree(input);
        }

        Constructor<?>[] constructors;
        Constructor<?> constructor = null;
        try {
            constructors = ReflectUtils.findConstructorsByAnnotation(clazz, Construct.class);
            if (constructors.length == 1) {
                constructor = constructors[0];
            }

            if (constructor == null) {
                constructors = Arrays.stream(clazz.getDeclaredConstructors()).toArray(Constructor<?>[]::new);
                if (constructors.length == 1) {
                    constructor = constructors[0];
                }
            }
            if (constructor == null) {
                throw new RuntimeException("can not create instance of class " + clazz.getName() + " ; No av");
            }
            Parameter[] parameters = constructor.getParameters();
            String[] args = ArrayUtils.parse(input);

            if (parameters.length != args.length) {
                throw new RuntimeException("invalid argument count. expected: " + parameters.length + ", real: " + args.length);
            }

            Object[] initArgs = IntStream.range(0, parameters.length)
                    .mapToObj(i -> ConvertUtils.convert(parameters[i].getType(), args[i]))
                    .toArray(Object[]::new);
            return constructor.newInstance(initArgs);
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    public static ListNode convertLinkedList(String input) {
        int[] args = JsonUtils.toObject(input, int[].class);

        ListNode ans = new ListNode(-1);
        ListNode ptr = ans;

        for (int val : args) {
            ptr.next = new ListNode(val);
            ptr = ptr.next;
        }

        return ans.next;
    }

    /**
     * @param input a String array representing level order
     *              traversal of a binary tree
     * @return root of the binary tree
     */
    public static TreeNode convertBinaryTree(String input) {
        TreeNode emptyNode = new TreeNode();

        TreeNode[] arr = Arrays.stream(ArrayUtils.parse(input))
                .map(s -> {
                    if ("null".equals(s)) return emptyNode;
                    else return new TreeNode(Integer.parseInt(s));
                })
                .toArray(TreeNode[]::new);

        if (arr.length == 0) return null;

        TreeNode ans = arr[0];
        ArrayDeque<TreeNode> que = new ArrayDeque<>();
        que.addLast(ans);

        for (int i = 1; !que.isEmpty(); i += 2) {
            TreeNode cur = que.pollFirst();
            if (cur == emptyNode) continue;

            TreeNode left = i < arr.length ? arr[i] : null;
            if (left == emptyNode) left = null;

            TreeNode right = (i + 1 < arr.length) ? arr[i + 1] : null;
            if (right == emptyNode) right = null;

            cur.left = left;
            cur.right = right;

            if (left != null) que.addLast(left);
            if (right != null) que.addLast(right);
        }

        return ans;
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
