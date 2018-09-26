package com.xp.wanandroid.welcome

import android.content.Intent
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseToolBarActivity
import com.xp.wanandroid.main.MainActivity
import kotlinx.android.synthetic.main.welcome_activity_splash.*

/**
 * @类描述：欢迎页
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/11 0011 16:50
 * @修改人：
 * @修改时间：2018/6/11 0011 16:50
 * @修改备注：
 */
class SplashActivity : BaseToolBarActivity() {
    override fun useEventBus(): Boolean = false
    override fun setLayoutId(): Int = R.layout.welcome_activity_splash

    override fun initView() {
        welcome_splash_content.postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun initData() {
    }

}