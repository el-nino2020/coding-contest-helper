package org.elnino.helper.contest.coding.leetcode.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
     * TODO: 有了 @Constructor，可以更选择指定的构造器了
     * Initialize an object by calling 1-arg constructor of the class.
     * <p>
     * Make sure that tha class has only 1 1-arg constructor, otherwise there will be exception thrown
     * <p>
     * If clazz is an inner class, make it static, otherwise there will be exception thrown
     */
    public static Object convertCustomClass(Class<?> clazz, String arg) {
        if (clazz == ListNode.class) {
            return convertLinkedList(arg);
        } else if (clazz == TreeNode.class) {
            return convertBinaryTree(arg);
        }

        try {
            Constructor<?>[] constructors = Arrays.stream(clazz.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 1)
                    .toArray(Constructor<?>[]::new);

            if (constructors.length != 1) {
                throw new RuntimeException("can not create instance of class " + clazz.getName());
            }
            return constructors[0].newInstance(convert(constructors[0].getParameterTypes()[0], arg));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
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

    public static TreeNode convertBinaryTree(String input) {
        // TODO 待修改
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
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
