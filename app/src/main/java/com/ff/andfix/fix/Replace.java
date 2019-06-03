package com.ff.andfix.fix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 提供信息，使用注解的
 * author: FF
 * time: 2019-06-02 11:31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Replace {

    String clazz();// 修复哪一个class

    String method();// method
}
