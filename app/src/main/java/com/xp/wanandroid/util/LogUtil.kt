package com.xp.wanandroid.util

import android.util.Log
import com.xp.wanandroid.BuildConfig

/**
 * @类描述：日志显示工具类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/13 0013 10:17
 * @修改人：
 * @修改时间：2018/6/13 0013 10:17
 * @修改备注：
 */
object LogUtil {
    val isDebug: Boolean = BuildConfig.DEBUG
    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }
}
 