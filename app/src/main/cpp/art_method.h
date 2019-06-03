#include "../../../../../../../../../Library/Android/sdk/ndk-bundle/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/c++/v1/cstdint"

namespace art {
    namespace mirror {
        class Object {
            // The Class representing the type of the object.
            uint32_t klass_;
            // Monitor and hash code information.
            uint32_t monitor_;
        };

        class ArtMethod : public Object {
        public:

            // Access flags; low 16 bits are defined by spec.
            uint32_t access_flags_;
            uint32_t dex_code_item_offset_;

            uint32_t dex_method_index_;
            // 方法表的索引
            uint32_t method_index_;

            // Short cuts to declaring_class_->dex_cache_ member for fast compiled code access.
            uint32_t dex_cache_resolved_methods_;

            // Short cuts to declaring_class_->dex_cache_ member for fast compiled code access.
            uint32_t dex_cache_resolved_types_;

            // Field order required by test "ValidateFieldOrderOfJavaCppUnionClasses".
            // The class we are a part of.
            uint32_t declaring_class_;
        };
    }
}
