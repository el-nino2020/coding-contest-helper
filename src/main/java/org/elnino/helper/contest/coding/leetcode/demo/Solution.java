package org.elnino.helper.contest.coding.leetcode.demo;

import java.util.HashMap;

/**
 * Write your solution in this file. <br/>
 * Example: Two Sum - LeetCode
 * <br/><br/>
 * <a href="https://leetcode.com/problems/two-sum/">LeetCode 2-sum U.S. site</a>
 * <br/>
 * <a href="https://leetcode.cn/problems/two-sum/description/">LeetCode 2-sum China site</a>
 */
@SuppressWarnings("all")
class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}