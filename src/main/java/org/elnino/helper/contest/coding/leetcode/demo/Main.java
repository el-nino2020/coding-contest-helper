package org.elnino.helper.contest.coding.leetcode.demo;

import org.elnino.helper.contest.coding.leetcode.utils.IOUtils;
import org.elnino.helper.contest.coding.leetcode.utils.SolutionUtils;

import java.lang.reflect.InvocationTargetException;

class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        // this output helps you better find the path of inputs.txt
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        String path = "src/main/resources/org/elnino/helper/contest/coding/leetcode/demo/inputs.txt";
        String[] lines = IOUtils.readAllLines(path);

        // solve the question
        SolutionUtils.solve(new Solution(), lines);
    }
}
