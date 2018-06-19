package com.xp.wanandroid.login.entity

/**
 * @类描述：用户数据
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/14 0014 16:10
 * @修改人：
 * @修改时间：2018/6/14 0014 16:10
 * @修改备注：
 */
data class UserEntity(var errorCode: Int,
                      var errorMsg: String?,
                      var data: Data) {
    data class Data(var id: Int,
                    var username: String,
                    var password: String,
                    var icon: String?,
                    var type: Int,
                    var collectIds: List<Int>?)
}


 