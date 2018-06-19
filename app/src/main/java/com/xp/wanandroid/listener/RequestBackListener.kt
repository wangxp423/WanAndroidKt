package com.xp.wanandroid.listener

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/19 0019 10:06
 * @修改人：
 * @修改时间：2018/6/19 0019 10:06
 * @修改备注：
 */
interface RequestBackListener<T> {
    fun onRequestSuccess(data: T)
    fun onRequestFail(errorMsg: String?)
}