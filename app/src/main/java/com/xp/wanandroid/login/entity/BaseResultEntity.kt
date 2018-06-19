package com.xp.wanandroid.login.entity

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/19 0019 10:19
 * @修改人：
 * @修改时间：2018/6/19 0019 10:19
 * @修改备注：
 */
data class BaseResultEntity<T>(var errorCode: Int,
                               var errorMsg: String?,
                               var data: T)