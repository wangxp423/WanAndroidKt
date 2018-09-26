package com.xp.wanandroid.base

import android.os.Bundle
import android.support.v7.widget.Toolbar

/**
 * @类描述：沉浸式状态栏基类Activity
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/11 0011 17:07
 * @修改人：
 * @修改时间：2018/6/11 0011 17:07
 * @修改备注：
 */
abstract class BaseToolBarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(setLayoutId())
        super.onCreate(savedInstanceState)
        initEventBus()
        initTipView()
        initView()
        initData()
    }

    protected fun initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String) {
        toolbar?.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }


}