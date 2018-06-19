package com.xp.wanandroid.login.entity

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/19 0019 10:26
 * @修改人：
 * @修改时间：2018/6/19 0019 10:26
 * @修改备注：
 */
data class Data(var id: Int,
                var username: String,
                var password: String,
                var icon: String?,
                var type: Int,
                var collectIds: List<Int>?)
//最好应该是用BaseResultEntiti<Data>这种方式，但是因为Wanandroid提供的接口返回数据统一为Data 这里使用不了这种的。