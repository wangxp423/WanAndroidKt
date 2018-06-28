package com.xp.wanandroid.main.mvp

import RetrofitHelper
import cancelByActive
import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.util.Constant
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import tryCatch

/**
 * @类描述：主页-分类 model
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/27 0027 10:57
 * @修改人：
 * @修改时间：2018/6/27 0027 10:57
 * @修改备注：
 */
class MainTypeModel : MainTypeContract.IMainTypeModel {
    private var bolgTypeTreeListAsync: Deferred<BlogTypeEntity>? = null

    override fun getBlogTypeTreeData(listener: RequestBackListener<BlogTypeEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                bolgTypeTreeListAsync?.cancelByActive()
                bolgTypeTreeListAsync = RetrofitHelper.retrofitService.getTypeTreeList()
                val result = bolgTypeTreeListAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun cancelRequest() {
        bolgTypeTreeListAsync?.cancelByActive()
    }

}