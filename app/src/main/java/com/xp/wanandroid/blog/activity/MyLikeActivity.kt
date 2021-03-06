package com.xp.wanandroid.blog.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseToolBarActivity
import com.xp.wanandroid.blog.adapter.BlogListAdapter
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.blog.mvp.BlogContract
import com.xp.wanandroid.blog.mvp.BlogPresenter
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
class MyLikeActivity : BaseToolBarActivity(), BlogContract.BlogView {
    private val datas = mutableListOf<Datas>()
    private val blogPresenter: BlogContract.IBlogPresenter by lazy { BlogPresenter(this) }
    private val blogAdapter: BlogListAdapter by lazy { BlogListAdapter(this, datas) }
    private var pageIndex = 0

    override fun setLayoutId(): Int = R.layout.blog_activity_like


    override fun initView() {
        main_collect_toolbar.run {
            title = getString(R.string.main_nav_menu_like)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        blog_include_srl.run {
            isRefreshing = true
            setOnRefreshListener {
                showLoading()
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
            isMyLike = true
            bindToRecyclerView(blog_include_rv)
            setOnLoadMoreListener({
                blogPresenter.loadMoreDataList(pageIndex)
            }, blog_include_rv)
            setEmptyView(R.layout.recycle_list_empty)
            onItemClickListener = this
            onItemChildClickListener = this
        }
    }

    override fun initData() {
        showLoading()
        blogPresenter.getDataList(pageIndex)
    }

    override fun getDataListSuccess(result: BlogEntity?) {
        hideLoading()
        result?.data?.datas?.let {
            blogAdapter.run {
                if (data.size > 0) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                if (result.data.over) {
                    loadMoreEnd()
                    setEnableLoadMore(false)
                } else {
                    pageIndex++
                    loadMoreComplete()
                    setEnableLoadMore(true)
                }
            }
        }
    }

    override fun getDataListZero() {
    }

    override fun getDataListFail(errorMsg: String?) {
        hideLoading()
        errorMsg?.let { ToastUtil.showShort(it) }
    }

    override fun loadMoreDataListSuccess(result: BlogEntity?) {
        result?.data?.datas?.let {
            blogAdapter.run {
                addData(it)
                if (result.data.over) {
                    loadMoreEnd()
//                    setEnableLoadMore(false)
                } else {
                    pageIndex++
                    loadMoreComplete()
                    setEnableLoadMore(true)
                }
            }
        }
    }

    override fun loadMoreDataListFail(errorMsg: String?) {
        blogAdapter.loadMoreFail()
        errorMsg?.let { ToastUtil.showShort(it) }
    }

    override fun showLoading() {
        blog_include_srl.isRefreshing = true
    }

    override fun hideLoading() {
        blog_include_srl.isRefreshing = false
    }

    override fun articleDataSuccess(result: BlogEntity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun articleDataFail(errorMsg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unArticleDataSuccess(result: BlogEntity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unArticleDataFail(errorMsg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        blogAdapter.onDestory()
    }


}