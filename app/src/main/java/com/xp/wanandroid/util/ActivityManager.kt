package com.xp.wanandroid.util

import android.app.Activity

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/7/11 0011 15:48
 * @修改人：
 * @修改时间：2018/7/11 0011 15:48
 * @修改备注：
 */
object ActivityManager {
    private var activitys = mutableListOf<Activity>()
    fun addActivity(activity: Activity) {
        activitys.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activitys.remove(activity)
    }

    fun getSize(): Int {
        return activitys.size
    }
}