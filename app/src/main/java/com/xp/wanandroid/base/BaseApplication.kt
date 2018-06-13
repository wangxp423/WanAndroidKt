package com.xp.wanandroid.base

import android.app.Application
import android.content.ComponentCallbacks2
import com.bumptech.glide.Glide
import com.squareup.leakcanary.LeakCanary
import com.xp.wanandroid.BuildConfig

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/12 0012 10:37
 * @修改人：
 * @修改时间：2018/6/12 0012 10:37
 * @修改备注：
 */
class BaseApplication : Application() {
    companion object {
        lateinit var INSTANCE: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
        INSTANCE = this
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }
}