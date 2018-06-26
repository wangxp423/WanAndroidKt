package com.xp.wanandroid.main.entity

/**
 * @类描述：首页banner实体类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/14 0014 16:10
 * @修改人：
 * @修改时间：2018/6/14 0014 16:10
 * @修改备注：
 */
data class BannerEntity(var errorCode: Int,
                        var errorMsg: String?,
                        var data: List<Data>?) {
    data class Data(var id: Int,
                    var url: String,
                    var imagePath: String,
                    var title: String,
                    var desc: String,
                    var isVisible: Int,
                    var order: Int,
                    var `type`: Int)
}


 