package org.elnino.helper.contest.coding.leetcode.annotation;

import org.elnino.helper.contest.coding.leetcode.utils.ConvertUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Which constructor to use when converting a string to
 * an object of custom class
 *
 * @see ConvertUtils#convertCustomClass(Class, String)
 */
@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Construct {
}
