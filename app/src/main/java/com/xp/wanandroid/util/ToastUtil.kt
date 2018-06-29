package com.xp.wanandroid.util

import android.widget.Toast
import com.xp.wanandroid.base.BaseApplication

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
    fun showLong(resourceId: Int) {
        show(BaseApplication.INSTANCE.resources.getString(resourceId), Toast.LENGTH_LONG)
    }

    fun showShort(resourceId: Int) {
        show(BaseApplication.INSTANCE.resources.getString(resourceId), Toast.LENGTH_SHORT)
    }

    fun showLong(text: String) {
        show(text, Toast.LENGTH_LONG)
    }

    fun showShort(text: String) {
        show(text, Toast.LENGTH_SHORT)
    }

    private fun show(text: String, duration: Int) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.INSTANCE, text, duration)
        } else {
            toast?.setText(text)
        }
        toast?.show()
    }
}