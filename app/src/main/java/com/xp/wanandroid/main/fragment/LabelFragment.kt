package com.xp.wanandroid.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseFragment
import com.xp.wanandroid.main.adapter.HotLableAdapter
import com.xp.wanandroid.main.entity.HotLableEntity
import com.xp.wanandroid.main.mvp.MainHotLableContract
import com.xp.wanandroid.main.mvp.MainHotLablePresenter
import com.xp.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.main_fragment_hot_lable.*

/**
 * @类描述：热门标签页
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/12 0012 18:13
 * @修改人：
 * @修改时间：2018/6/12 0012 18:13
 * @修改备注：
 */
class LabelFragment : BaseFragment(), MainHotLableContract.MainHotLableView {
    private val lablePresenter: MainHotLableContract.IMainHotLablePresenter by lazy { MainHotLablePresenter(this) }

    private val myBookmarkDatas = mutableListOf<HotLableEntity.Data>()
    private val myBookmarkAdapter: HotLableAdapter by lazy { HotLableAdapter(activity, myBookmarkDatas) }

    private val hotSearchDatas = mutableListOf<HotLableEntity.Data>()
    private val hotSearchAdapter: HotLableAdapter by lazy { HotLableAdapter(activity, hotSearchDatas) }

    private val hotUseDatas = mutableListOf<HotLableEntity.Data>()
    private val hotUseAdapter: HotLableAdapter by lazy { HotLableAdapter(activity, hotUseDatas) }

    private var rootView: View? = null
    override fun getContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView ?: let {
            rootView = inflater?.inflate(R.layout.main_fragment_hot_lable, container, false)
        }
        return rootView
    }

    override fun initView(view: View?) {
        srl_main_fragment_hot_lable.run {
            showLoading()
            setOnRefreshListener {
                refreshData()
            }
            tfl_main_fragment_hot_lable_bookmark.run {
                adapter = myBookmarkAdapter
                setOnTagClickListener(myBookmarkAdapter)
            }
            tfl_main_fragment_hot_lable_hotsearch.run {
                adapter = hotSearchAdapter
                hotSearchAdapter.isSearch = true
                setOnTagClickListener(hotSearchAdapter)
            }
            tfl_main_fragment_hot_lable_hotuse.run {
                adapter = hotUseAdapter
                setOnTagClickListener(hotUseAdapter)
            }
        }
    }

    override fun initData() {
        showLoading()
        lablePresenter.getMyBookmarkData()
        lablePresenter.getHotSearchData()
        lablePresenter.getHotUseData()
    }

    fun refreshData() {
        showLoading()
        lablePresenter.getMyBookmarkData()
        lablePresenter.getHotSearchData()
        lablePresenter.getHotUseData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initData()
        }
    }

    override fun cancelRequest() {
        lablePresenter.cancelRequest()
    }

    override fun getMyBookmarkDataSuccess(result: HotLableEntity?) {
        hideLoading()
        result?.data?.let {
            myBookmarkAdapter.run {
                myBookmarkDatas.clear()
                myBookmarkDatas.addAll(it)
                notifyDataChanged()
            }
        }
    }

    override fun getMyBookmarkDataFail(errorMsg: String?) {
        hideLoading()
        errorMsg?.let {
            ToastUtil.showShort(activity, errorMsg)
        }
    }

    override fun getHotSearchDataSuccess(result: HotLableEntity?) {
        hideLoading()
        result?.data?.let {
            hotSearchAdapter.run {
                hotSearchDatas.clear()
                hotSearchDatas.addAll(it)
                notifyDataChanged()
            }
        }
    }

    override fun getHotSearchDataFail(errorMsg: String?) {
        hideLoading()
        errorMsg?.let {
            ToastUtil.showShort(activity, errorMsg)
        }
    }

    override fun getHotUseDataSuccess(result: HotLableEntity?) {
        hideLoading()
        result?.data?.let {
            hotUseAdapter.run {
                hotUseDatas.clear()
                hotUseDatas.addAll(it)
                notifyDataChanged()
            }
        }
    }

    override fun getHotUseDataFail(errorMsg: String?) {
        hideLoading()
        errorMsg?.let {
            ToastUtil.showShort(activity, errorMsg)
        }
    }

    override fun showLoading() {
        srl_main_fragment_hot_lable.isRefreshing = true
    }

    override fun hideLoading() {
        srl_main_fragment_hot_lable.isRefreshing = false
    }

}
 