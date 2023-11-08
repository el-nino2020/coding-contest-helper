package org.elnino.helper.contest.coding.leetcode.utils;

import org.elnino.helper.contest.coding.leetcode.annotation.Entrance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("unused")
public class SolutionUtils {
    private SolutionUtils() {
    }

    /**
     * call the entrance method of solution
     *
     * @param solution instance of Solution class
     * @param method   entrance method of the question
     * @param args     arguments for the entrance method
     * @return results of every testcase
     */
    public static Object[] solve(Object solution, Method method, String[] args, boolean printResult)
            throws InvocationTargetException, IllegalAccessException {
        Parameter[] parameters = method.getParameters();
        method.setAccessible(true);
        if (args.length % parameters.length != 0) {
            throw new IllegalArgumentException(String.format(
                    "wrong number of args, it should be a multiple of %d", parameters.length));
        }
        ArrayList<Object> ans = new ArrayList<>();
        for (int round = 0; round < args.length / parameters.length; round++) {
            Object[] arguments = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                arguments[i] = ConvertUtils.convert(parameters[i].getType(), args[round * parameters.length + i]);
            }
            Object result = method.invoke(solution, arguments);
            ans.add(result);
            if (printResult) {
                System.out.println(ArrayUtils.toString(method.getReturnType(), result));
            }
        }
        return ans.toArray();
    }


    /**
     * Call the entrance method of solution. If users do not specify
     * the entrance method, this method will do the following to find it:
     * <p>
     * 1. Find the method with {@code @Entrance} Annotation <br/>
     * 2. If there is no such method, find the public non-static method
     * of the Solution class
     * <p>
     * Note that in either case, there should be exactly one such method.
     * Otherwise, it is assumed that no method can be found.
     *
     * @param solution    instance of Solution class
     * @param args        arguments for the entrance method of the question
     * @param printResult whether print the result in stdout
     * @return results of every testcase
     * @see Entrance
     */
    public static Object[] solve(Object solution, String[] args, boolean printResult)
            throws InvocationTargetException, IllegalAccessException {
        // case 1
        Method[] methods = ReflectUtils.findMethodsByAnnotation(solution.getClass(), Entrance.class);
        if (methods.length == 1) {
            return solve(solution, methods[0], args, printResult);
        }
        // case 2
        methods = Arrays.stream(solution.getClass().getMethods())
                .filter(m -> (m.getModifiers() & Modifier.PUBLIC) != 0)
                .filter(m -> (m.getModifiers() & Modifier.STATIC) == 0)
                .filter(m -> m.getDeclaringClass() == solution.getClass())
                .toArray(Method[]::new);

        if (methods.length != 1) {
            throw new RuntimeException("Solution methods definition error");
        }
        return solve(solution, methods[0], args, printResult);
    }


    /**
     * solve the problem and print the result
     *
     * @see SolutionUtils#solve(Object, String[], boolean)
     */
    public static Object[] solve(Object solution, String[] args)
            throws InvocationTargetException, IllegalAccessException {
        return solve(solution, args, true);
    }
}
