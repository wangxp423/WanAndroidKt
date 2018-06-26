package com.xp.wanandroid.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (getContentViewLayoutID() != 0) {
            return inflater?.inflate(getContentViewLayoutID(), null)
        } else {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
    }

    protected abstract fun getContentViewLayoutID(): Int
    protected abstract fun initView(view: View?)
    protected abstract fun initData()
    protected abstract fun cancelRequest()

    override fun onDestroyView() {
        super.onDestroyView()
        cancelRequest()
    }
}
 