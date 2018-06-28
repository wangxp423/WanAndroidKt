package com.xp.wanandroid.main.mvp

import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.main.entity.HotLableEntity
import com.xp.wanandroid.mvpbase.IBaseModel
import com.xp.wanandroid.mvpbase.IBasePresenter
import com.xp.wanandroid.mvpbase.IBaseView

/**
 * @类描述：热门标签 关联类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/28 0028 10:54
 * @修改人：
 * @修改时间：2018/6/28 0028 10:54
 * @修改备注：
 */
interface MainHotLableContract {
    interface MainHotLableView : IBaseView {
        fun getMyBookmarkDataSuccess(result: HotLableEntity?)
        fun getMyBookmarkDataFail(errorMsg: String?)
        fun getHotSearchDataSuccess(result: HotLableEntity?)
        fun getHotSearchDataFail(errorMsg: String?)
        fun getHotUseDataSuccess(result: HotLableEntity?)
        fun getHotUseDataFail(errorMsg: String?)
    }

    interface IMainHotLablePresenter : IBasePresenter {
        fun getMyBookmarkData()
        fun getHotSearchData()
        fun getHotUseData()
        fun cancelRequest()
    }

    interface IMainHotLableModel : IBaseModel {
        fun getMyBookmark(listener: RequestBackListener<HotLableEntity>)
        fun getHotSearch(listener: RequestBackListener<HotLableEntity>)
        fun getHotUse(listener: RequestBackListener<HotLableEntity>)
        fun cancelRequest()
    }
}