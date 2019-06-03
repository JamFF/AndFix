#include <jni.h>
#include <string>
#include "art_method.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_ff_andfix_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_ff_andfix_DexManager_replace(JNIEnv *env, jclass type, jobject wrongMethod,
                                      jobject rightMethod) {

    // ArtMethod结构体在Android系统源码中art/runtime/mirror
    art::mirror::ArtMethod *wrong = (art::mirror::ArtMethod *) env->FromReflectedMethod(
            wrongMethod);
    art::mirror::ArtMethod *right = (art::mirror::ArtMethod *) env->FromReflectedMethod(
            rightMethod);
    // method   --->class  --->ClassLoader
    // wrong = right;// 这样替换是不行的，因为加载两个Method的ClassLoader都不一样，只能一个个替换
    wrong->declaring_class_ = right->declaring_class_;
    wrong->dex_cache_resolved_methods_ = right->dex_cache_resolved_methods_;
    wrong->access_flags_ = right->access_flags_;
    wrong->dex_cache_resolved_types_ = right->dex_cache_resolved_types_;
    wrong->dex_code_item_offset_ = right->dex_code_item_offset_;
    wrong->method_index_ = right->method_index_;// 关键是这里：方法索引的替换
    wrong->dex_method_index_ = right->dex_method_index_;
}