package com.xp.wanandroid.blog.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseFragment
import com.xp.wanandroid.blog.adapter.BlogTypeListAdapter
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.blog.mvp.BlogContract
import com.xp.wanandroid.blog.mvp.BlogPresenter
import com.xp.wanandroid.util.Constant
import com.xp.wanandroid.util.LogUtil
import com.xp.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.blog_include_swipe_recycle.*

/**
 * @类描述：博客文章分类fragment
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/26 0026 10:28
 * @修改人：
 * @修改时间：2018/6/26 0026 10:28
 * @修改备注：
 */
class BlogContentTypeFragment : BaseFragment(), BlogContract.BlogView {


    private val datas = mutableListOf<Datas>()
    private val blogTypePresenter: BlogContract.IBlogPresenter by lazy { BlogPresenter(this) }
    private val blogAdapter: BlogTypeListAdapter by lazy { BlogTypeListAdapter(activity, datas) }
    private var cid: Int = 0
    private var pageIndex: Int = 0
    override fun getContentViewLayoutID(): Int = R.layout.blog_include_swipe_recycle

    override fun initView(view: View?) {
        blog_include_srl.run {
            isRefreshing = true
            setOnRefreshListener {
                isRefreshing = true
                blogAdapter.setEnableLoadMore(false)
                pageIndex = 0
                blogTypePresenter.getBlogTypeDataList(pageIndex, cid)
            }
        }
        blog_include_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = blogAdapter
        }
        blogAdapter.run {
            bindToRecyclerView(blog_include_rv)
            setOnLoadMoreListener({
                pageIndex++
                blogTypePresenter.loadMoreBlogTypeDataList(pageIndex, cid)
            }, blog_include_rv)
            setEmptyView(R.layout.recycle_list_empty)
            onItemClickListener = this
            onItemChildClickListener = this
        }
    }

    override fun initData() {
        cid = arguments.getInt(Constant.BLOG_EXTRA_CID)
        blogTypePresenter.getBlogTypeDataList(pageIndex, cid)
    }

    override fun cancelRequest() {
        blog_include_srl.isRefreshing = false
        blogTypePresenter.cancleRequest()
    }

    override fun getDataListSuccess(result: BlogEntity?) {
        LogUtil.d("Test", "获取博客分类数据  = " + result)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataListFail(errorMsg: String?) {
        errorMsg?.let {
            ToastUtil.showShort(activity, it)
        }
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
        errorMsg?.let {
            ToastUtil.showShort(activity, it)
        }
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

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(cid: Int): BlogContentTypeFragment {
            val fragment = BlogContentTypeFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.BLOG_EXTRA_CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

}
 