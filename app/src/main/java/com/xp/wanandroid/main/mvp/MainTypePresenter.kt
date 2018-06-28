package com.xp.wanandroid.main.mvp

import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.util.LogUtil

/**
 * @类描述：主页-分类 presenter
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/27 0027 11:00
 * @修改人：
 * @修改时间：2018/6/27 0027 11:00
 * @修改备注：
 */
class MainTypePresenter(val typeView: MainTypeContract.MainTypeView) : MainTypeContract.IMainTypePresenter {
    private val typeModel: MainTypeContract.IMainTypeModel = MainTypeModel()
    override fun getBlogTypeTreeData() {
        typeModel.getBlogTypeTreeData(object : RequestBackListener<BlogTypeEntity> {
            override fun onRequestSuccess(data: BlogTypeEntity) {
                LogUtil.d("Test", "getBlogTypeTreeDataSuccess = " + data)
                if (data?.errorCode == 0) {
                    typeView.getBlogTypeDataSuccess(data)
                } else {
                    typeView.getBlogTypeDataFail(data?.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                typeView.getBlogTypeDataFail(errorMsg)
            }

        })
    }

    override fun cancelRequest() {
        typeModel.cancelRequest()
    }

}
 