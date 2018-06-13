package com.xp.wanandroid.base

import android.support.v4.app.Fragment


/**
 * @类描述：基类Fragment
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/12 0012 18:09
 * @修改人：
 * @修改时间：2018/6/12 0012 18:09
 * @修改备注：
 */
abstract class BaseFragment : Fragment() {
    protected var isFrist: Boolean = true

    protected abstract fun cancelRequest()

    override fun onDestroyView() {
        super.onDestroyView()
        cancelRequest()
    }
}
 