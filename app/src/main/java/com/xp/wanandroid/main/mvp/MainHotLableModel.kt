package com.xp.wanandroid.main.mvp

import RetrofitHelper
import cancelByActive
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.main.entity.HotLableEntity
import com.xp.wanandroid.util.Constant
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import tryCatch

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/28 0028 11:12
 * @修改人：
 * @修改时间：2018/6/28 0028 11:12
 * @修改备注：
 */
class MainHotLableModel : MainHotLableContract.IMainHotLableModel {
    private var myBookmarkAsync: Deferred<HotLableEntity>? = null
    private var hotSearchAsync: Deferred<HotLableEntity>? = null
    private var hotUseAsync: Deferred<HotLableEntity>? = null
    override fun getMyBookmark(listener: RequestBackListener<HotLableEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                myBookmarkAsync?.cancelByActive()
                myBookmarkAsync = RetrofitHelper.retrofitService.getMyBookmarkList()
                val result = myBookmarkAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun getHotSearch(listener: RequestBackListener<HotLableEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                hotSearchAsync?.cancelByActive()
                hotSearchAsync = RetrofitHelper.retrofitService.getHotSearchList()
                val result = hotSearchAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun getHotUse(listener: RequestBackListener<HotLableEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                hotUseAsync?.cancelByActive()
                hotUseAsync = RetrofitHelper.retrofitService.getHotUseList()
                val result = hotUseAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun cancelRequest() {
        myBookmarkAsync?.cancelByActive()
        hotSearchAsync?.cancelByActive()
        hotUseAsync?.cancelByActive()
    }
}