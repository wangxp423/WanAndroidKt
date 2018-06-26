package com.xp.wanandroid.blog.mvp

import RetrofitHelper
import cancelByActive
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.util.Constant
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import tryCatch

/**
 * @类描述：博客请求
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/20 0020 17:20
 * @修改人：
 * @修改时间：2018/6/20 0020 17:20
 * @修改备注：
 */
class BlogModel : BlogContract.IBlogModel {

    private var searchListAsync: Deferred<BlogEntity>? = null
    private var likeListAsync: Deferred<BlogEntity>? = null
    private var articleCollectAsync: Deferred<BlogEntity>? = null
    private var collectOutSideArticleAsync: Deferred<BlogEntity>? = null
    private var typeArticleListAsync: Deferred<BlogEntity>? = null

    override fun getDataListByKey(page: Int, key: String, listener: RequestBackListener<BlogEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                searchListAsync = RetrofitHelper.retrofitService.getSearchList(page, key)
                val result = searchListAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun getDataList(page: Int, listener: RequestBackListener<BlogEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                likeListAsync?.cancelByActive()
                likeListAsync = RetrofitHelper.retrofitService.getLikeList(page)
                val result = likeListAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun articleData(id: Int, isAdd: Boolean, listener: RequestBackListener<BlogEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                articleCollectAsync?.cancelByActive()
                articleCollectAsync = if (isAdd) {
                    RetrofitHelper.retrofitService.addCollectArticle(id)
                } else {
                    RetrofitHelper.retrofitService.removeCollectArticle(id)
                }
                val result = articleCollectAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun collectOutSideArticleData(title: String, author: String, link: String, isAdd: Boolean, listener: RequestBackListener<BlogEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                collectOutSideArticleAsync?.cancelByActive()
                if (isAdd) {
                    // add article
                    collectOutSideArticleAsync = RetrofitHelper.retrofitService.addCollectOutsideArticle(title, author, link)
                } else {
                    // TODO if isAdd false, remove article 没有这个接口好像
                    // collectOutSideArticleAsync = RetrofitHelper.retrofitService.removeCollectArticle(id)
                }
                val result = collectOutSideArticleAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun getBlogTypeDataList(cid: Int, page: Int, listener: RequestBackListener<BlogEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.toString())
            }) {
                typeArticleListAsync?.cancelByActive()
                typeArticleListAsync = RetrofitHelper.retrofitService.getArticleList(page, cid)
                val result = typeArticleListAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun cancelRequest() {
        searchListAsync?.cancelByActive()
        likeListAsync?.cancelByActive()
        articleCollectAsync?.cancelByActive()
        collectOutSideArticleAsync?.cancelByActive()
        typeArticleListAsync?.cancelByActive()

    }
}