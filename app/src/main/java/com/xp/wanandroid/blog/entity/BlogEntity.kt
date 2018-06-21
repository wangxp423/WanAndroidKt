package com.xp.wanandroid.blog.entity

/**
 * @类描述：收藏实体类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/14 0014 16:10
 * @修改人：
 * @修改时间：2018/6/14 0014 16:10
 * @修改备注：
 */
data class BlogEntity(var errorCode: Int,
                      var errorMsg: String?,
                      var data: Data) {
    data class Data(var offset: Int,
                    var size: Int,
                    var total: Int,
                    var pageCount: Int,
                    var curPage: Int,
                    var over: Boolean,
                    var datas: List<Datas>?)
}


 