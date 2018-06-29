package com.xp.wanandroid.blog.activity

import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseImmersionBarActivity
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.mvp.BlogContract
import com.xp.wanandroid.blog.mvp.BlogPresenter
import com.xp.wanandroid.util.Constant
import com.xp.wanandroid.util.ToastUtil
import getAgentWeb
import kotlinx.android.synthetic.main.blog_activity_blog.*

/**
 * @类描述：博客内容
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/22 0022 11:04
 * @修改人：
 * @修改时间：2018/6/22 0022 11:04
 * @修改备注：
 */
class BlogActivity : BaseImmersionBarActivity(), BlogContract.BlogView {
    private val blogPresenter: BlogContract.IBlogPresenter by lazy { BlogPresenter(this) }
    private lateinit var agentWeb: AgentWeb
    private lateinit var shareTitle: String
    private lateinit var shareUrl: String
    private var isCollect: Boolean = false
    private var shareId: Int = 0

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.blog_activity_blog_toolbar).init()
    }

    override fun setLayoutId(): Int = R.layout.blog_activity_blog

    override fun initView() {
        blog_activity_blog_toolbar.run {
            title = getString(R.string.blog_title_loading)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        intent.extras?.let {
            isCollect = it.getBoolean(Constant.BLOG_EXTRA_ISCOLLECTED, false)
            shareId = it.getInt(Constant.BLOG_EXTRA_ID, 0)
            shareTitle = it.getString(Constant.BLOG_EXTRA_TITLE)
            shareUrl = it.getString(Constant.BLOG_EXTRA_URL)
            agentWeb = shareUrl.getAgentWeb(this,
                    blog_activity_blog_webcontent,
                    LinearLayout.LayoutParams(-1, -1),
                    receivedTitleCallback = receivedTitleCallback)
        }
    }

    override fun initData() {
    }

    override fun getDataListSuccess(result: BlogEntity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataListZero() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataListFail(errorMsg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadMoreDataListSuccess(result: BlogEntity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadMoreDataListFail(errorMsg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun articleDataSuccess(result: BlogEntity?) {
        ToastUtil.showShort(getString(R.string.main_collect_success))
    }

    override fun articleDataFail(errorMsg: String?) {
        errorMsg?.let {
            ToastUtil.showShort(getString(R.string.main_collect_failed, errorMsg))
        }
    }

    override fun unArticleDataSuccess(result: BlogEntity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unArticleDataFail(errorMsg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelRequest() {
        blogPresenter.cancleRequest()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.blog_content_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.blog_content_menu_item_share -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.blog_share_article_url, getString(R.string.app_name), shareTitle, shareUrl))
                    type = Constant.BLOG_SHARE_CONTENT_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.blog_share_title)))
                }
                return true
            }
            R.id.blog_content_menu_item_collect -> {
                if (isCollect) {
                    ToastUtil.showShort(getString(R.string.blog_content_collect_already))
                    return true
                }
                if (shareId == 0) {
                    blogPresenter.collectOutSideArticleData(shareTitle, getString(R.string.blog_outside_title), shareUrl)
                    return true
                }
                blogPresenter.articleData(shareId)
                return true

            }
            R.id.blog_content_menu_item_browser -> {
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(shareUrl)
                    startActivity(this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else {
            finish()
            return super.onKeyDown(keyCode, event)
        }
    }

    private val receivedTitleCallback =
            ChromeClientCallbackManager.ReceivedTitleCallback { _, title ->
                title?.let {
                    blog_activity_blog_toolbar.title = it
                }
            }

}
 