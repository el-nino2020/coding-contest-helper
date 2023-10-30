package org.elnino.helper.contest.coding.leetcode.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@SuppressWarnings({"unused"})
public final class IOUtils {
    private IOUtils() {
    }


    public static String[] readAllLines(String path) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(path))))) {
            ArrayList<String> ans = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                ans.add(line);
            }
            return ans.toArray(new String[0]);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
