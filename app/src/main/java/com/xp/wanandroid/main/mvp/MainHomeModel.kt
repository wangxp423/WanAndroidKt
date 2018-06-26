package com.xp.wanandroid.main.mvp

import RetrofitHelper
import cancelByActive
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.main.entity.BannerEntity
import com.xp.wanandroid.util.Constant
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import tryCatch

/**
 * @类描述：首页 model
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/26 0026 15:33
 * @修改人：
 * @修改时间：2018/6/26 0026 15:33
 * @修改备注：
 */
class MainHomeModel : MainHomeContract.IMainHomeModel {
    private var homeDataListAsync: Deferred<BlogEntity>? = null
    private var bannerDataListAsync: Deferred<BannerEntity>? = null

    override fun getHomeDataList(page: Int, listener: RequestBackListener<BlogEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                homeDataListAsync?.cancelByActive()
                homeDataListAsync = RetrofitHelper.retrofitService.getHomeList(page)
                // Get async result
                val result = homeDataListAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun getBannerDataList(listener: RequestBackListener<BannerEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                bannerDataListAsync?.cancelByActive()
                bannerDataListAsync = RetrofitHelper.retrofitService.getBanner()
                // Get async result
                val result = bannerDataListAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun cancelRequest() {
        homeDataListAsync?.cancelByActive()
        bannerDataListAsync?.cancelByActive()
    }

}