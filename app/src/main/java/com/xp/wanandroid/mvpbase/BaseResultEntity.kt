package com.xp.wanandroid.mvpbase

/**
 * @类描述：数据处理基类
 * @创建人：Wangxiaopan
 * @创建时间：2017/7/11 0011 10:47
 * @修改人：
 * @修改时间：2017/7/11 0011 10:47
 * @修改备注：
 */

class BaseResultEntity<T> {
    var errorCode: Int = 0 //状态码
    var errorMsg: String? = null //状态消息
    var data: T? = null //返回数据
}
