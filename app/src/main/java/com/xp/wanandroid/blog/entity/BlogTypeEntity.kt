package com.xp.wanandroid.blog.entity

import java.io.Serializable

/**
 * @类描述：博客分类实体类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/25 0025 17:25
 * @修改人：
 * @修改时间：2018/6/25 0025 17:25
 * @修改备注：
 */
data class BlogTypeEntity(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>
) {
    data class Data(
            var id: Int,
            var name: String,
            var courseId: Int,
            var parentChapterId: Int,
            var order: Int,
            var visible: Int,
            var children: List<Children>?
    ) : Serializable {
        data class Children(
                var id: Int,
                var name: String,
                var courseId: Int,
                var parentChapterId: Int,
                var order: Int,
                var visible: Int,
                var children: List<Children>?
        ) : Serializable
    }
}