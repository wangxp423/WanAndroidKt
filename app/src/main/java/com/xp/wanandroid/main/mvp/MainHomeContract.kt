package com.xp.wanandroid.main.mvp

import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.mvp.BlogContract
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.main.entity.BannerEntity

/**
 * @类描述：首页 关联类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/26 0026 15:20
 * @修改人：
 * @修改时间：2018/6/26 0026 15:20
 * @修改备注：
 */
interface MainHomeContract {
    interface IMainHomeView : BlogContract.BlogView {
        fun getBannerDataListSuccess(result: BannerEntity)
        fun getBannerDataListFail(errorMsg: String?)
    }

    interface IMainHomePresenter {
        fun getHomeDataList(page: Int)
        fun loadMoreHomeDatalist(page: Int)
        fun getBannerDataList()
        fun cancelRequest()
    }

    interface IMainHomeModel {
        fun getHomeDataList(page: Int, listener: RequestBackListener<BlogEntity>)
        fun getBannerDataList(listener: RequestBackListener<BannerEntity>)
        fun cancelRequest()
    }

}