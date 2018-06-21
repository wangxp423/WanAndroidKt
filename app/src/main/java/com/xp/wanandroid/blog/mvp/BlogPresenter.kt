package com.xp.wanandroid.blog.mvp

import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.util.LogUtil

/**
 * @类描述：获取博客文章presenter
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/21 0021 10:15
 * @修改人：
 * @修改时间：2018/6/21 0021 10:15
 * @修改备注：
 */
class BlogPresenter(val blogView: BlogContract.BlogView) : BlogContract.IBlogPresenter {
    private val blogModel: BlogContract.IBlogModel = BlogModel()
    override fun getDataListByKey(page: Int, key: String) {
        blogModel.getDataListByKey(page, key, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "getDataListByKeySuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.getDataListSuccess(data)
                } else {
                    blogView.getDataListFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "getDataListByKeyFail = " + errorMsg)
                blogView.getDataListFail(errorMsg)
            }

        })
    }

    override fun getDataList(page: Int) {
        blogModel.getDataList(page, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "getDataListSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.getDataListSuccess(data)
                } else {
                    blogView.getDataListFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "getDataListFail = " + errorMsg)
                blogView.getDataListFail(errorMsg)
            }

        })
    }

    override fun loadMoreDataListByKey(page: Int, key: String) {
    }

    override fun loadMoreDataList(page: Int) {
        blogModel.getDataList(page, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "refreshDataListSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.loadMoreDataListSuccess(data)
                } else {
                    blogView.loadMoreDataListFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "refreshDataListFail = " + errorMsg)
                blogView.loadMoreDataListFail(errorMsg)
            }

        })
    }

    override fun articleData(id: Int) {
    }

    override fun unArticleData(id: Int) {
    }

    override fun cancleRequest() {
    }

}