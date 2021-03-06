package com.xp.wanandroid.blog.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseToolBarActivity
import com.xp.wanandroid.blog.adapter.BlogListAdapter
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.blog.mvp.BlogContract
import com.xp.wanandroid.blog.mvp.BlogPresenter
import com.xp.wanandroid.util.Constant
import com.xp.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.blog_activity_like.*
import kotlinx.android.synthetic.main.blog_include_swipe_recycle.*

/**
 * @类描述：搜索页
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/22 0022 15:44
 * @修改人：
 * @修改时间：2018/6/22 0022 15:44
 * @修改备注：
 */
class BlogSearchActivity : BaseToolBarActivity(), BlogContract.BlogView {
    private val datas = mutableListOf<Datas>()
    private val blogPresenter: BlogContract.IBlogPresenter by lazy { BlogPresenter(this) }
    private val blogAdapter: BlogListAdapter by lazy { BlogListAdapter(this, datas) }
    private var pageIndex = 0
    private var searchKey: String? = null
    private var searchView: SearchView? = null

    override fun setLayoutId(): Int = R.layout.blog_activity_like

    override fun initView() {
        main_collect_toolbar.run {
            title = ""
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        blog_include_srl.run {
            setOnRefreshListener {
                searchKey?.let {
                    showLoading()
                    blogAdapter.setEnableLoadMore(false)
                    pageIndex = 0
                    blogPresenter.getDataListByKey(pageIndex, it)
                }
            }
        }
        blog_include_rv.run {
            layoutManager = LinearLayoutManager(this@BlogSearchActivity)
            adapter = blogAdapter
        }
        blogAdapter.run {
            isMyLike = false
            bindToRecyclerView(blog_include_rv)
            setOnLoadMoreListener({
                searchKey?.let {
                    blogPresenter.loadMoreDataListByKey(pageIndex, it)
                }
            }, blog_include_rv)
            setEmptyView(R.layout.recycle_list_empty)
            onItemClickListener = this
            onItemChildClickListener = this
        }
    }

    override fun initData() {
        intent.extras?.let {
            searchKey = it.getString(Constant.BLOG_EXTRA_SEARCH_KEY, null)
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.blog_search_menu, menu)
        searchView = menu?.findItem(R.id.blog_search_memu_item_search)?.actionView as SearchView
        searchView?.init(1920, false, onQueryTextListener = onQueryTextListener)
        searchKey?.let {
            searchView?.setQuery(it, true)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            searchView?.clearFocus()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun SearchView.init(
            sMaxWidth: Int = 0,
            sIconified: Boolean = false,
            isClose: Boolean = false,
            onQueryTextListener: SearchView.OnQueryTextListener
    ) = this.run {
        if (sMaxWidth != 0) {
            maxWidth = sMaxWidth
        }
        // false open
        isIconified = sIconified
        // not close
        if (!isClose) {
            // open
            onActionViewExpanded()
        }
        // search listener
        setOnQueryTextListener(onQueryTextListener)
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                searchKey = it
                blog_include_srl.isRefreshing = true
                blogAdapter.setEnableLoadMore(false)
                blogPresenter.getDataListByKey(pageIndex, it)
            } ?: let {
                blog_include_srl.isRefreshing = false
                ToastUtil.showShort(R.string.main_collect_list_no_data)
            }
            searchView?.clearFocus()
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean = false
    }

    override fun onDestroy() {
        super.onDestroy()
        blogAdapter.onDestory()
    }

}