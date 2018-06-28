package com.xp.wanandroid.main.entity

/**
 * @类描述：首页热门标签实体类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/14 0014 16:10
 * @修改人：
 * @修改时间：2018/6/14 0014 16:10
 * @修改备注：
 */
data class HotLableEntity(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>?
) {
    data class Data(
            var id: Int,
            var name: String,
            var link: String,
            var visible: Int,
            var order: Int,
            var icon: Any
    )
}