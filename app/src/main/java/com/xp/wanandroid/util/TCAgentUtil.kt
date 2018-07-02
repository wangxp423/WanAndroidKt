package com.xp.wanandroid.util

import android.content.Context

import com.tendcloud.tenddata.TCAgent

/**
 * @类描述：TalkingData 统计工具类
 * @创建人：Wangxiaopan
 * @创建时间：2017/9/25 0025 17:46
 * @修改人：
 * @修改时间：2017/9/25 0025 17:46
 * @修改备注：
 */

object TCAgentUtil {
    val EVENT_ID_MAIN_FUNCTION = "首页功能"
    val EVENT_ID_OPEN_NETWORK = "开网接口"
    val EVENT_ID_DEVICE_CHECK = "设备检测接口"
    private val isOnline = true

    fun onPageStart(context: Context?, pageName: String?) {
        if (null == context) return
        if (!isOnline) return
        pageName?.let {
            TCAgent.onPageStart(context, pageName)
        }
    }

    fun onPageEnd(context: Context?, pageName: String?) {
        if (null == context) return
        if (!isOnline) return
        pageName?.let {
            TCAgent.onPageEnd(context, pageName)
        }
    }

    fun onEvent(context: Context?, eventId: String?) {
        if (null == context) return
        if (!isOnline) return
        eventId?.let {
            TCAgent.onEvent(context, eventId)
        }
    }

    fun onEvent(context: Context?, eventId: String?, labelId: String?) {
        if (null == context) return
        if (!isOnline) return
        eventId?.let {
            labelId?.let {
                TCAgent.onEvent(context, eventId, labelId)
            }
        }
    }

    fun onEvent(context: Context?, eventId: String?, labelId: String?, map: Map<String, String>) {
        if (null == context) return
        if (!isOnline) return
        eventId?.let {
            labelId?.let {
                TCAgent.onEvent(context, eventId, labelId, map)
            }
        }
    }
}
