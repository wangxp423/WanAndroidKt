package com.xp.wanandroid.blog.mvp

import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.mvpbase.IBaseModel
import com.xp.wanandroid.mvpbase.IBasePresenter
import com.xp.wanandroid.mvpbase.IBaseView

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/20 0020 17:03
 * @修改人：
 * @修改时间：2018/6/20 0020 17:03
 * @修改备注：
 */
interface BlogContract {
    interface BlogView : IBaseView {
        fun getDataListSuccess(result: BlogEntity?)
        fun getDataListZero()
        fun getDataListFail(errorMsg: String?)
        fun loadMoreDataListSuccess(result: BlogEntity?)
        fun loadMoreDataListFail(errorMsg: String?)
    }

    interface IBlogPresenter : IBasePresenter {
        fun getDataListByKey(page: Int, key: String)
        fun getDataList(page: Int)
        fun loadMoreDataListByKey(page: Int, key: String)
        fun loadMoreDataList(page: Int)
        fun articleData(id: Int)
        fun unArticleData(id: Int)
        fun cancleRequest()
    }

    interface IBlogModel : IBaseModel {
        fun getDataListByKey(page: Int, key: String, listener: RequestBackListener<BlogEntity>)
        fun getDataList(page: Int, listener: RequestBackListener<BlogEntity>)
        fun articleData(id: Int, isAdd: Boolean, listener: RequestBackListener<BlogEntity>)
    }
}