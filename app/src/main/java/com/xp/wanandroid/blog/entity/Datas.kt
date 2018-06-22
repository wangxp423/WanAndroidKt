package com.xp.wanandroid.blog.entity

/**
 * 收藏信息类
 */
data class Datas(
        var author: String,
        var chapterId: Int,
        var chapterName: String?,
        var courseId: Int,
        var desc: Any,
        var envelopePic: Any,
        var id: Int,
        var link: String,
        var niceDate: String,
        var origin: Any,
        var originId: Int,
        var publishTime: Long,
        var title: String,
        var userId: Int,
        var visible: Int,
        var zan: Any,
        var collect: Boolean
)