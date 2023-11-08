package org.elnino.helper.contest.coding.leetcode.problem;

import java.util.HashMap;

/**
 * <a href="https://leetcode.com/problems/two-sum/">LeetCode 2-sum U.S. site</a>
 * <br/>
 * <a href="https://leetcode.cn/problems/two-sum/description/">LeetCode 2-sum China site</a>
 */
class Solution1 {
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
