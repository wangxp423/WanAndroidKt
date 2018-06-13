package com.xp.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.gyf.barlibrary.ImmersionBar

/**
 * @类描述：沉浸式状态栏基类Activity
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/11 0011 17:07
 * @修改人：
 * @修改时间：2018/6/11 0011 17:07
 * @修改备注：
 */
abstract class BaseImmersionBarActivity : BaseActivity() {
    protected lateinit var immersionBar: ImmersionBar

    private val inputMM: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        initImmersionBar()
        initView()
        initData()
    }

    protected abstract fun setLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    open protected fun initImmersionBar() {
        immersionBar = ImmersionBar.with(this)
        immersionBar.init()
    }

    protected abstract fun cancelRequest()

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()
        cancelRequest()
    }

    override fun finish() {
        super.finish()
        if (!isFinishing) {
            super.finish()
            hideSoftKeyBoard()
        }
    }

    private fun hideSoftKeyBoard() {
        currentFocus?.let { inputMM.hideSoftInputFromWindow(it.windowToken, 2) }
    }
}