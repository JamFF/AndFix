package com.ff.andfix;

/**
 * description: 出现异常的类
 * author: FF
 * time: 2019-06-02 11:17
 */
public class Calculator {
    public int calculator() {
        throw new RuntimeException("异常");
    }
}
