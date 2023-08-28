package org.elnino.helper.contest.coding.leetcode.utils;

import org.elnino.helper.contest.coding.leetcode.annotation.Entrance;
import org.elnino.helper.contest.coding.leetcode.demo.Solution;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

//TODO: test this class
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
     */
    public static void solve(Object solution, Method method, String[] args) throws InvocationTargetException, IllegalAccessException {
        Parameter[] parameters = method.getParameters();

        if (args.length % parameters.length != 0) {
            throw new IllegalArgumentException(String.format(
                    "wrong number of args, it should be a multiple of %d", parameters.length));
        }

        for (int round = 0; round < args.length / parameters.length; round++) {
            Object[] arguments = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                arguments[i] = ConvertUtils.convert(parameters[i].getType(), args[round * parameters.length + i]);
            }
            Object result = method.invoke(solution, arguments);
            System.out.println(ArrayUtils.toString(method.getReturnType(), result));
        }
    }


    /**
     * Call the entrance method of solution. Since users do not specify
     * the entrance method, this method will do the following to find it:
     * <p>
     * 1. Find the method with {@code @Entrance} Annotation <br/>
     * 2. If there is no such method, find the public non-static method
     * of the Solution class
     * <p>
     * Note that in either case, there should be exactly one such method.
     * Otherwise, it is assumed that no method can be found.
     *
     * @param solution instance of Solution class
     * @param args     arguments for the entrance method of the question
     * @see Entrance
     */
    public static void solve(Object solution, String[] args) throws InvocationTargetException, IllegalAccessException {
        // case 1
        Method[] methods = ReflectUtils.findMethodsByAnnotation(solution.getClass(), Entrance.class);
        if (methods.length == 1) {
            solve(solution, methods[0], args);
            return;
        }
        // case 2
        methods = Arrays.stream(solution.getClass().getMethods())
                .filter(m -> (m.getModifiers() & Modifier.PUBLIC) != 0)
                .filter(m -> (m.getModifiers() & Modifier.STATIC) == 0)
                .filter(m -> m.getDeclaringClass() == Solution.class)
                .toArray(Method[]::new);

        if (methods.length != 1) {
            throw new RuntimeException("Solution methods definition error");
        }
        solve(solution, methods[0], args);
    }

}
