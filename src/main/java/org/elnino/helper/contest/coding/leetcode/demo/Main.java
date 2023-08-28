package org.elnino.helper.contest.coding.leetcode.demo;

import org.elnino.helper.contest.coding.leetcode.utils.SolutionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        // this output helps you better find the path of  inputs.txt
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String[] lines;

        // read all lines
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(
                Paths.get("src/main/resources/org/elnino/helper/contest/coding/leetcode/demo/inputs.txt"))))) {
            ArrayList<String> list = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) list.add(line);
            lines = list.toArray(new String[0]);
        }

        // solve the question
        SolutionUtils.solve(new Solution(), lines);

    }
}
