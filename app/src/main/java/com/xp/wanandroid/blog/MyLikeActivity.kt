package com.xp.wanandroid.blog

import android.support.v7.widget.LinearLayoutManager
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseImmersionBarActivity
import com.xp.wanandroid.blog.adapter.BlogListAdapter
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.blog.mvp.BlogContract
import com.xp.wanandroid.blog.mvp.BlogPresenter
import com.xp.wanandroid.util.LogUtil
import com.xp.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.blog_activity_like.*
import kotlinx.android.synthetic.main.blog_include_swipe_recycle.*

/**
 * @类描述：我关注的人
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/20 0020 11:16
 * @修改人：
 * @修改时间：2018/6/20 0020 11:16
 * @修改备注：
 */
class MyLikeActivity : BaseImmersionBarActivity(), BlogContract.BlogView {

    private val datas = mutableListOf<Datas>()
    private val blogPresenter: BlogContract.IBlogPresenter by lazy { BlogPresenter(this) }
    private val blogAdapter: BlogListAdapter by lazy { BlogListAdapter(this, datas) }
    private var pageIndex = 0

    override fun setLayoutId(): Int = R.layout.blog_activity_like

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.main_collect_toolbar).init()
    }

    override fun initView() {
        main_collect_toolbar.run {
            title = getString(R.string.main_nav_menu_like)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        blog_include_srl.run {
            setOnRefreshListener {
                isRefreshing = true
                blogAdapter.setEnableLoadMore(false)
                pageIndex = 0
                blogPresenter.getDataList(pageIndex)
            }
        }
        blog_include_rv.run {
            layoutManager = LinearLayoutManager(this@MyLikeActivity)
            adapter = blogAdapter
        }
        blogAdapter.run {
            bindToRecyclerView(blog_include_rv)
            setOnLoadMoreListener({
                pageIndex++
                blogPresenter.loadMoreDataList(pageIndex)
            }, blog_include_rv)
            setEmptyView(R.layout.recycle_list_empty)
            onItemChildClickListener
        }
    }

    override fun initData() {
        blogPresenter.getDataList(pageIndex)
    }

    override fun cancelRequest() {
    }

    override fun getDataListSuccess(result: BlogEntity?) {
        LogUtil.d("Test", "获取数据  = " + result)
        result?.data?.datas?.let {
            blogAdapter.run {
                if (data.size > 0) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                if (pageIndex > result.data.pageCount) {
                    loadMoreEnd()
                    setEnableLoadMore(false)
                }
            }
        }
        blog_include_srl.isRefreshing = false
    }

    override fun getDataListZero() {
    }

    override fun getDataListFail(errorMsg: String?) {
        errorMsg?.let { ToastUtil.showShort(this, it) }
    }

    override fun loadMoreDataListSuccess(result: BlogEntity?) {
        result?.data?.datas?.let {
            blogAdapter.run {
                addData(it)
                loadMoreComplete()
                setEnableLoadMore(true)
                if (result.data.curPage > result.data.pageCount) {
                    loadMoreEnd()
                }
            }
        }
    }

    override fun loadMoreDataListFail(errorMsg: String?) {
        errorMsg?.let { ToastUtil.showShort(this, it) }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }


}