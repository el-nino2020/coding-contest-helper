package org.elnino.helper.contest.coding.leetcode.utils;

import com.google.gson.GsonBuilder;

@SuppressWarnings({"unused"})
public final class JsonUtils {
    private JsonUtils() {
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return new GsonBuilder().create().fromJson(json, clazz);
    }

}
