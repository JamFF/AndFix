// 来自源码 art/runtime/mirror
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

            // Offset to the CodeItem.
            uint32_t dex_code_item_offset_;

            // Index into method_ids of the dex file associated with this method
            uint32_t method_dex_index_;// 不能删除

            // Index into method_ids of the dex file associated with this method.
            uint32_t dex_method_index_;// 不能删除

            // For static and direct methods this is the index in the direct methods table.
            uint32_t method_index_;// 方法表的索引

            // The target native method registered with this method
            const void *native_method_;// 不能删除

            // When a register is promoted into a register, the spill mask holds which registers hold dex
            // registers. The first promoted register's corresponding dex register is vmap_table_[1], the Nth
            // is vmap_table_[N]. vmap_table_[0] holds the length of the table.
            const uint16_t *vmap_table_;// 不能删除

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
