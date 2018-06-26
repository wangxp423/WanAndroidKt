package com.xp.wanandroid.main.mvp

import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.main.entity.BannerEntity
import com.xp.wanandroid.util.LogUtil

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/26 0026 15:40
 * @修改人：
 * @修改时间：2018/6/26 0026 15:40
 * @修改备注：
 */
class MainHomePresenter(val homeView: MainHomeContract.IMainHomeView) : MainHomeContract.IMainHomePresenter {
    private val homeModel: MainHomeContract.IMainHomeModel = MainHomeModel()
    override fun getHomeDataList(page: Int) {
        homeModel.getHomeDataList(page, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "getHomeDataListSuccess = " + data)
                if (data.errorCode == 0) {
                    homeView.getDataListSuccess(data)
                } else {
                    homeView.getDataListFail(data?.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                homeView.getDataListFail(errorMsg)
            }
        })
    }

    override fun loadMoreHomeDatalist(page: Int) {
        homeModel.getHomeDataList(page, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "loadMoreHomeDatalistSuccess = " + data)
                if (data.errorCode == 0) {
                    homeView.loadMoreDataListSuccess(data)
                } else {
                    homeView.loadMoreDataListFail(data?.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                homeView.loadMoreDataListFail(errorMsg)
            }
        })
    }

    override fun getBannerDataList() {
        homeModel.getBannerDataList(object : RequestBackListener<BannerEntity> {
            override fun onRequestSuccess(data: BannerEntity) {
                LogUtil.d("Test", "getBannerDataListSuccess = " + data)
                if (data.errorCode == 0) {
                    homeView.getBannerDataListSuccess(data)
                } else {
                    homeView.getBannerDataListFail(data?.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                homeView.getBannerDataListFail(errorMsg)
            }
        })
    }

    override fun cancelRequest() {
        homeModel.cancelRequest()
    }

}