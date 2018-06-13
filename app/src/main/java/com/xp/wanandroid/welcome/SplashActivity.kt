package com.xp.wanandroid.welcome

import android.content.Intent
import android.os.Bundle
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseActivity
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
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity_splash)
        welcome_splash_content.postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}