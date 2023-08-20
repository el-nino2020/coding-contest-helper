package org.elnino.helper.contest.coding.leetcode.demo;

import org.elnino.helper.contest.coding.leetcode.utils.ConvertUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Files.newInputStream(Paths.get(
                                "src/main/java/org/elnino/helper/contest/coding/leetcode/demo/inputs.txt"))));

        // TODO: find a better way to get the target method
        // solution should only have 1 non-static, public method, i.e., the method to solve the question
        Method[] methods = Solution.class.getMethods();
        methods = Arrays.stream(methods)
                .filter(m -> (m.getModifiers() & Modifier.PUBLIC) != 0)
                .filter(m -> (m.getModifiers() & Modifier.STATIC) == 0)
                .filter(m -> m.getDeclaringClass() == Solution.class)
                .toArray(Method[]::new);

        if (methods.length != 1) {
            throw new RuntimeException("Solution methods definition error");
        }
        Method method = methods[0];
        Parameter[] parameters = method.getParameters();

        String line;
        boolean flag = true;
        while (true) {
            Object[] arguments = new Object[parameters.length];
            for (int i = 0; i < arguments.length; i++) {
                line = reader.readLine();
                if (line == null) {
                    flag = false;
                    break;
                }
                arguments[i] = ConvertUtils.convert(parameters[i].getType(), line);
            }
            if (!flag) break;
            // TODO: write method to call result's corresponding toString() method, may be another convert method
            Object result = method.invoke(new Solution(), arguments);
            System.out.println(result);
        }
    }
}
