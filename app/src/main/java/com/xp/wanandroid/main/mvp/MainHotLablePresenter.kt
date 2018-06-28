package com.xp.wanandroid.main.mvp

import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.main.entity.HotLableEntity
import com.xp.wanandroid.util.LogUtil

/**
 * @类描述：热门标签 persenter
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/28 0028 11:11
 * @修改人：
 * @修改时间：2018/6/28 0028 11:11
 * @修改备注：
 */
class MainHotLablePresenter(val lableView: MainHotLableContract.MainHotLableView) : MainHotLableContract.IMainHotLablePresenter {
    private val lableModel: MainHotLableContract.IMainHotLableModel = MainHotLableModel()
    override fun getMyBookmarkData() {
        lableModel.getMyBookmark(object : RequestBackListener<HotLableEntity> {
            override fun onRequestSuccess(data: HotLableEntity) {
                LogUtil.d("Test", "getMyBookmarkSuccess = " + data)
                if (data.errorCode == 0) {
                    lableView.getMyBookmarkDataSuccess(data)
                } else {
                    lableView.getMyBookmarkDataFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                lableView.getMyBookmarkDataFail(errorMsg)
            }
        })
    }

    override fun getHotSearchData() {
        lableModel.getHotSearch(object : RequestBackListener<HotLableEntity> {
            override fun onRequestSuccess(data: HotLableEntity) {
                LogUtil.d("Test", "getHotSearchSuccess = " + data)
                if (data.errorCode == 0) {
                    lableView.getHotSearchDataSuccess(data)
                } else {
                    lableView.getHotSearchDataFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                lableView.getHotSearchDataFail(errorMsg)
            }
        })
    }

    override fun getHotUseData() {
        lableModel.getHotUse(object : RequestBackListener<HotLableEntity> {
            override fun onRequestSuccess(data: HotLableEntity) {
                LogUtil.d("Test", "getHotUseSuccess = " + data)
                if (data.errorCode == 0) {
                    lableView.getHotUseDataSuccess(data)
                } else {
                    lableView.getHotUseDataFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                lableView.getHotUseDataFail(errorMsg)
            }
        })
    }

    override fun cancelRequest() {
        lableModel.cancelRequest()
    }
}