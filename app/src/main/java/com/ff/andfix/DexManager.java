package com.ff.andfix;

import android.content.Context;

import com.ff.andfix.fix.Replace;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 * description:
 * author: FF
 * time: 2019-06-02 15:24
 */
public class DexManager {

    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 加载dex文件
     *
     * @param context
     * @param file
     */
    public void load(Context context, File file) throws IOException {
        // TODO: 2019-06-02 也可以使用 DexClassLoader 加载
        DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(),
                new File(context.getCacheDir(), "opt").getAbsolutePath(), Context.MODE_PRIVATE);
        // 当前的dex里面的class类名集合
        Enumeration<String> entry = dexFile.entries();
        while (entry.hasMoreElements()) {
            String clazzName = entry.nextElement();// 全类名
            // 注意这里不能使用Class.forName(clazzName);因为这个class没有加载内存中，是在SD卡上的。
            Class realClazz = dexFile.loadClass(clazzName, context.getClassLoader());
            if (realClazz != null) {
                fixClazz(realClazz);
            }
        }
    }

    private void fixClazz(Class realClazz) {
        // andfix 不是 class ---> bug class，而是 method ---> bug method
        // 因为实现的目的是不需要重启，而class只会加载一次，所以思路只能是修复method
        Method[] methods = realClazz.getMethods();
        for (Method rightMethod : methods) {
            // 通过注解过滤，只有使用注解的方法
            Replace replace = rightMethod.getAnnotation(Replace.class);
            if (replace == null) {
                continue;
            }
            // 拿到从网络上下载的 rightMethod
            // 本地的bug class 中 method

            String clazzName = replace.clazz();
            String methodName = replace.method();

            try {
                // 这个反射是可以的，因为通过这个clazzName是已经加载到内存中，出现异常的类
                Class<?> wrongClazz = Class.forName(clazzName);
                Method wrongMethod = wrongClazz.getDeclaredMethod(methodName, rightMethod.getParameterTypes());
                replace(wrongMethod, rightMethod);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public native static void replace(Method wrongMethod, Method rightMethod);
}
