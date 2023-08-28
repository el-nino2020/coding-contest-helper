package org.elnino.helper.contest.coding.leetcode.utils;

import org.elnino.helper.contest.coding.leetcode.annotation.Constructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class BinaryTreeUtils {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        @Constructor
        public TreeNode(int x) {
            val = x;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return BinaryTreeUtils.toString(this, ToStringType.LEVEL_ORDER);
        }
    }


    public static Iterator<TreeNode> bfsIterator(TreeNode root) {
        if (root == null) {
            throw new NullPointerException("root can not be null");
        }
        return new Iterator<TreeNode>() {
            private final ArrayDeque<TreeNode> que = new ArrayDeque<>();

            {
                que.add(root);
            }

            @Override
            public boolean hasNext() {
                return !que.isEmpty();
            }

            @Override
            public TreeNode next() {
                TreeNode ans = que.pollFirst();
                if (ans.left != null) que.add(ans.left);
                if (ans.right != null) que.add(ans.right);
                return ans;
            }
        };
    }

    public static boolean equal(TreeNode node1, TreeNode node2) {
        Iterator<TreeNode> iterator1 = bfsIterator(node1);
        Iterator<TreeNode> iterator2 = bfsIterator(node1);

        while (iterator1.hasNext() && iterator2.hasNext()) {
            if (iterator1.next().val != iterator2.next().val) {
                return false;
            }
        }

        return iterator1.hasNext() == iterator2.hasNext();
    }

    public enum ToStringType {
        /**
         * Only print data of current node
         */
        ONE_NODE,
        /**
         * Print the tree rooted by current node in level order,
         * the string will in array format
         */
        LEVEL_ORDER,
        /**
         * Print the tree rooted by current node visually, in a
         * nice format
         */
        PRETTY_PRINT
    }

    /**
     * This <a href="https://leetcode.cn/problems/print-binary-tree/description/">url</a>
     * gives me ideas on how to pretty print a binary tree.
     *
     * @param root node
     * @param type how to perform the toString operation
     * @return
     */
    public static String toString(TreeNode root, ToStringType type) {
        switch (type) {
            case ONE_NODE:
                return toStringOneNode(root);
            case LEVEL_ORDER:
                return toStringLevelOrder(root);
            case PRETTY_PRINT:
                break;
        }
        throw new RuntimeException("unreachable");
    }

    private static String toStringOneNode(TreeNode root) {
        if (root == null) {
            return "null";
        }
        return "TreeNode{" +
                "val=" + root.val +
                ", left=" + (root.left == null ? "null" : root.left.val) +
                ", right=" + (root.right == null ? "null" : root.right.val) +
                '}';
    }

    private static String toStringLevelOrder(TreeNode root) {
        if (root == null) {
            return "[]";
        }

        ArrayList<Integer> ans = new ArrayList<>();
        ArrayDeque<TreeNode> que = new ArrayDeque<>();
        que.addLast(root);

        while (!que.isEmpty()) {
            TreeNode node = que.pollFirst();

            if (node == null) {
                ans.add(null);
                continue;
            }
            ans.add(node.val);
            que.addLast(node.left);
            que.addLast(node.right);
        }
        return ans.toString();
    }

    public static String toStringPrettyPrint(TreeNode root) {
        // TODO
        return null;
    }


}
