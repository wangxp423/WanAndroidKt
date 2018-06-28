package com.xp.wanandroid.main.mvp

import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.mvpbase.IBaseModel
import com.xp.wanandroid.mvpbase.IBasePresenter
import com.xp.wanandroid.mvpbase.IBaseView

/**
 * @类描述：主页--分类 关联类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/27 0027 10:00
 * @修改人：
 * @修改时间：2018/6/27 0027 10:00
 * @修改备注：
 */
interface MainTypeContract {
    interface MainTypeView : IBaseView {
        fun getBlogTypeDataSuccess(result: BlogTypeEntity?)
        fun getBlogTypeDataFail(errorMsg: String?)
    }

    interface IMainTypePresenter : IBasePresenter {
        fun getBlogTypeTreeData()
        fun cancelRequest()
    }

    interface IMainTypeModel : IBaseModel {
        fun getBlogTypeTreeData(listener: RequestBackListener<BlogTypeEntity>)
        fun cancelRequest()
    }
}
 