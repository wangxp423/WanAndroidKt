package com.xp.wanandroid.main.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseFragment
import com.xp.wanandroid.blog.adapter.BlogListAdapter
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.main.adapter.HomeBannerAdapter
import com.xp.wanandroid.main.entity.BannerEntity
import com.xp.wanandroid.main.mvp.MainHomeContract
import com.xp.wanandroid.main.mvp.MainHomePresenter
import com.xp.wanandroid.util.ToastUtil
import com.xp.wanandroid.widget.HorizontalRecyclerView
import kotlinx.android.synthetic.main.main_fragment_home.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * @类描述：首页
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/12 0012 18:13
 * @修改人：
 * @修改时间：2018/6/12 0012 18:13
 * @修改备注：
 */
class HomeFragment : BaseFragment(), MainHomeContract.IMainHomeView {
    private val BANNER_TIME: Long = 5000L
    private val bannerPagerSnap: PagerSnapHelper by lazy { PagerSnapHelper() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false) }
    private lateinit var bannerRecyclerView: HorizontalRecyclerView
    private val bannerDatas = mutableListOf<BannerEntity.Data>()
    private val bannerAdapter: HomeBannerAdapter by lazy { HomeBannerAdapter(activity, bannerDatas) }
    private var bannerSwithJob: Job? = null
    private var currentBannerIndex = 0

    private val datas = mutableListOf<Datas>()
    private val homePresenter: MainHomeContract.IMainHomePresenter by lazy { MainHomePresenter(this) }
    private val blogAdapter: BlogListAdapter by lazy { BlogListAdapter(activity, datas) }
    private var pageIndex: Int = 0

    private var rootView: View? = null
    override fun getContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView ?: let {
            rootView = inflater?.inflate(R.layout.main_fragment_home, container, false)
        }
        return rootView
    }

    override fun initView(view: View?) {
        bannerRecyclerView = View.inflate(activity, R.layout.main_recycle_home_banner, null) as HorizontalRecyclerView
        main_fragment_home_srl.run {
            isRefreshing = true
            setOnRefreshListener {
                showLoading()
                blogAdapter.setEnableLoadMore(false)
                cancelSwitchJob()
                pageIndex = 0
                homePresenter.getHomeDataList(pageIndex)
                homePresenter.getBannerDataList()
            }
        }
        main_fragment_home_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = blogAdapter
        }
        bannerRecyclerView.run {
            layoutManager = linearLayoutManager
            bannerPagerSnap.attachToRecyclerView(this)
            requestDisallowInterceptTouchEvent(true)
            setOnTouchListener(onBannerTouchListener)
            addOnScrollListener(onBannerScrollListener)
        }
        bannerAdapter.run {
            bindToRecyclerView(bannerRecyclerView)
            onItemClickListener = this
        }
        blogAdapter.run {
            bindToRecyclerView(main_fragment_home_rv)
            setOnLoadMoreListener({
                homePresenter.loadMoreHomeDatalist(pageIndex)
            }, main_fragment_home_rv)
            setEmptyView(R.layout.recycle_list_empty)
            addHeaderView(bannerRecyclerView)
            onItemClickListener = this
            onItemChildClickListener = this
        }
    }

    fun smoothScrollToPosition() = main_fragment_home_rv.scrollToPosition(0)

    override fun initData() {
        showLoading()
        homePresenter.getHomeDataList(pageIndex)
        homePresenter.getBannerDataList()
    }

    override fun onPause() {
        super.onPause()
        cancelSwitchJob()
    }

    override fun onResume() {
        super.onResume()
        startSwitchJob()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelSwitchJob()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            cancelSwitchJob()
        } else {
            startSwitchJob()
        }
    }

    override fun cancelRequest() {
        hideLoading()
        homePresenter.cancelRequest()
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
        errorMsg?.let {
            ToastUtil.showShort(activity, it)
        }
    }

    override fun getBannerDataListSuccess(result: BannerEntity) {
        result.data?.let {
            bannerAdapter.replaceData(it)
        }
    }

    override fun getBannerDataListFail(errorMsg: String?) {
        errorMsg?.let { ToastUtil.showShort(activity, errorMsg) }
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
        main_fragment_home_srl.isRefreshing = true
    }

    override fun hideLoading() {
        main_fragment_home_srl.isRefreshing = false
    }

    private val onBannerTouchListener = View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                cancelSwitchJob()
            }
        }
        false
    }

    private val onBannerScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                    currentBannerIndex = linearLayoutManager.findFirstVisibleItemPosition()
                    startSwitchJob()
                }
            }
        }
    }

    private fun getBannerSwithJob() = launch {
        repeat(Int.MAX_VALUE) {
            if (bannerDatas.size == 0) {
                return@launch
            }
            delay(BANNER_TIME)
            currentBannerIndex++
            val index = currentBannerIndex % bannerDatas.size
            bannerRecyclerView.smoothScrollToPosition(index)
            currentBannerIndex = index
        }
    }

    private fun startSwitchJob() = bannerSwithJob?.run {
        if (!isActive) {
            bannerSwithJob = getBannerSwithJob().apply { start() }
        }
    } ?: let {
        bannerSwithJob = getBannerSwithJob().apply { start() }
    }

    private fun cancelSwitchJob() = bannerSwithJob?.run {
        if (isActive) {
            cancel()
        }
    }

}
 