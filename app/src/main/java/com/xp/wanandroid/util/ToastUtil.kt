package com.xp.wanandroid.util

import android.content.Context
import android.widget.Toast

/**
 * @类描述：Toast工具类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/13 0013 11:32
 * @修改人：
 * @修改时间：2018/6/13 0013 11:32
 * @修改备注：
 */
object ToastUtil {
    private var toast: Toast? = null
    fun showLong(context: Context, resourceId: Int) {
        show(context, context.resources.getString(resourceId), Toast.LENGTH_LONG)
    }

    fun showShort(context: Context, resourceId: Int) {
        show(context, context.resources.getString(resourceId), Toast.LENGTH_SHORT)
    }

    fun showLong(context: Context, text: String) {
        show(context, text, Toast.LENGTH_LONG)
    }

    fun showShort(context: Context, text: String) {
        show(context, text, Toast.LENGTH_SHORT)
    }

    private fun show(context: Context, text: String, duration: Int) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration)
        } else {
            toast?.setText(text)
        }
        toast?.show()
    }
}