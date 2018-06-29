package com.xp.wanandroid.main.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseFragment
import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.main.adapter.MainTypeBlogAdapter
import com.xp.wanandroid.main.mvp.MainTypeContract
import com.xp.wanandroid.main.mvp.MainTypePresenter
import com.xp.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.blog_include_swipe_recycle.*

/**
 * @类描述：分类页
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/12 0012 18:13
 * @修改人：
 * @修改时间：2018/6/12 0012 18:13
 * @修改备注：
 */
class TypeFragment : BaseFragment(), MainTypeContract.MainTypeView {
    private val datas = mutableListOf<BlogTypeEntity.Data>()
    private val typePresenter: MainTypeContract.IMainTypePresenter by lazy { MainTypePresenter(this) }
    private val typeAdapter: MainTypeBlogAdapter by lazy { MainTypeBlogAdapter(activity, datas) }

    private var rootView: View? = null
    override fun getContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView ?: let {
            rootView = inflater?.inflate(R.layout.blog_include_swipe_recycle, container, false)
        }
        return rootView
    }

    override fun initView(view: View?) {
        blog_include_srl.run {
            isRefreshing = true
            setOnRefreshListener {
                showLoading()
                typePresenter.getBlogTypeTreeData()
            }
        }
        blog_include_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = typeAdapter
        }
        typeAdapter.run {
            bindToRecyclerView(blog_include_rv)
            setEmptyView(R.layout.recycle_list_empty)
            onItemClickListener = this
        }
    }

    fun smoothScrollToPosition() = blog_include_rv.scrollToPosition(0)

    override fun initData() {
        showLoading()
        typePresenter.getBlogTypeTreeData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            typePresenter.getBlogTypeTreeData()
        }
    }

    override fun cancelRequest() {
        hideLoading()
        typePresenter.cancelRequest()
    }

    override fun getBlogTypeDataSuccess(result: BlogTypeEntity?) {
        hideLoading()
        result?.data?.let {
            typeAdapter.run {
                if (data.size > 0) {
                    replaceData(it)
                } else {
                    addData(it)
                }
            }
        }
    }

    override fun getBlogTypeDataFail(errorMsg: String?) {
        hideLoading()
        errorMsg?.let {
            ToastUtil.showShort(errorMsg)
        }
    }

    override fun showLoading() {
        blog_include_srl.isRefreshing = true
    }

    override fun hideLoading() {
        blog_include_srl.isRefreshing = false
    }

}
 