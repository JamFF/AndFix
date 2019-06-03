package com.ff.andfix.fix;

/**
 * description: 修复后的类，这里是为了模拟，放在了代码中
 * 真实项目应该放在线上，从网络获取，用于修复原有异常的Calculator
 * author: FF
 * time: 2019-06-02 11:27
 */
public class Calculator {

    @Replace(clazz = "com.ff.andfix.Calculator", method = "calculator")
    public int calculator() {
        return 10;
    }
}
