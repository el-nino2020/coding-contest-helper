package org.elnino.helper.contest.coding.leetcode.problem;

import java.util.ArrayList;
import java.util.List;

import static org.elnino.helper.contest.coding.leetcode.utils.BinaryTreeUtils.TreeNode;

/**
 * <a href="https://leetcode.com/problems/binary-tree-preorder-traversal/">
 * LeetCode Binary Tree Preorder Traversal U.S. site</a>
 * <br/>
 * <a href="https://leetcode.cn/problems/binary-tree-preorder-traversal">
 * LeetCode Binary Tree Preorder Traversal China site</a>
 */
public class Solution2 {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();

        preorderTraversal(root, ans);

        return ans;
    }

    private void preorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) return;
        list.add(root.val);
        preorderTraversal(root.left, list);
        preorderTraversal(root.right, list);
    }
}
