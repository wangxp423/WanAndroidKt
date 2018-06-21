package com.xp.wanandroid.main

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseImmersionBarActivity
import kotlinx.android.synthetic.main.main_activity_about.*

/**
 * @类描述：关于我们页面
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/20 0020 10:03
 * @修改人：
 * @修改时间：2018/6/20 0020 10:03
 * @修改备注：
 */
class AboutUsActivity : BaseImmersionBarActivity() {
    override fun setLayoutId(): Int = R.layout.main_activity_about

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.main_activity_about_tb).init()
    }

    override fun initView() {
        main_activity_about_tb.run {
            title = getString(R.string.main_about_about_us)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        main_activity_about_version.text = getString(R.string.main_about_version_code, getString(R.string.app_name), packageManager.getPackageInfo(packageName, 0).versionName)
        main_activity_about_content.run {
            text = Html.fromHtml(getString(R.string.main_about_content))
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    override fun initData() {
    }

    override fun cancelRequest() {
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}