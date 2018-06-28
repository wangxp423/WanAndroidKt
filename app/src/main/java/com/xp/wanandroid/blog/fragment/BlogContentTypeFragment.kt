package com.xp.wanandroid.blog.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseFragment
import com.xp.wanandroid.blog.adapter.BlogTypeListAdapter
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.blog.mvp.BlogContract
import com.xp.wanandroid.blog.mvp.BlogPresenter
import com.xp.wanandroid.util.Constant
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
    private var rootView: View? = null
    private val datas = mutableListOf<Datas>()
    private val blogTypePresenter: BlogContract.IBlogPresenter by lazy { BlogPresenter(this) }
    private val blogAdapter: BlogTypeListAdapter by lazy { BlogTypeListAdapter(activity, datas) }
    private var cid: Int = 0
    private var pageIndex: Int = 0

    override fun getContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView ?: let {
            rootView = inflater?.inflate(R.layout.blog_include_swipe_recycle, container, false)
        }
        return rootView
    }

    override fun initView(view: View?) {
        blog_include_srl.run {
            showLoading()
            setOnRefreshListener {
                showLoading()
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
            setEnableLoadMore(false)
            setOnLoadMoreListener({
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
        blogAdapter.loadMoreComplete()
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataListFail(errorMsg: String?) {
        blogAdapter.setEnableLoadMore(false)
        hideLoading()
        errorMsg?.let {
            ToastUtil.showShort(activity, it)
        }
    }

    override fun loadMoreDataListSuccess(result: BlogEntity?) {
        result?.data?.datas?.let {
            blogAdapter.run {
                addData(it)
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

    override fun loadMoreDataListFail(errorMsg: String?) {
        blogAdapter.loadMoreFail()
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
        blog_include_srl.isRefreshing = true
    }

    override fun hideLoading() {
        blog_include_srl.isRefreshing = false
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
 