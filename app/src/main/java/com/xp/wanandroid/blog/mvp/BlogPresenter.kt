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

    override fun loadMoreDataListByKey(page: Int, key: String) {
        blogModel.getDataListByKey(page, key, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "loadMoreDataListByKeySuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.loadMoreDataListSuccess(data)
                } else {
                    blogView.loadMoreDataListFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "loadMoreDataListByKeyFail = " + errorMsg)
                blogView.loadMoreDataListFail(errorMsg)
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



    override fun loadMoreDataList(page: Int) {
        blogModel.getDataList(page, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "loadMoreDataListSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.loadMoreDataListSuccess(data)
                } else {
                    blogView.loadMoreDataListFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "loadMoreDataListFail = " + errorMsg)
                blogView.loadMoreDataListFail(errorMsg)
            }

        })
    }

    override fun articleData(id: Int) {
        blogModel.articleData(id, true, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "articleDataSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.articleDataSuccess(data)
                } else {
                    blogView.articleDataFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "articleDataFail = " + errorMsg)
                blogView.articleDataFail(errorMsg)
            }
        })
    }

    override fun unArticleData(id: Int) {
        blogModel.articleData(id, false, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "unArticleDataSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.unArticleDataSuccess(data)
                } else {
                    blogView.unArticleDataFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "unArticleDataFail = " + errorMsg)
                blogView.unArticleDataFail(errorMsg)
            }
        })
    }

    override fun collectOutSideArticleData(title: String, author: String, link: String) {
        blogModel.collectOutSideArticleData(title, author, link, true, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "collectOutSideArticleDataSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.articleDataSuccess(data)
                } else {
                    blogView.articleDataFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "collectOutSideArticleDataFail = " + errorMsg)
                blogView.articleDataFail(errorMsg)
            }
        })
    }

    override fun unCollectOutSideArticleData(title: String, author: String, link: String) {
        blogModel.collectOutSideArticleData(title, author, link, false, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "unCollectOutSideArticleDataSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.unArticleDataSuccess(data)
                } else {
                    blogView.unArticleDataFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "unCollectOutSideArticleDataFail = " + errorMsg)
                blogView.unArticleDataFail(errorMsg)
            }
        })
    }

    override fun getBlogTypeDataList(page: Int, cid: Int) {
        blogModel.getBlogTypeDataList(cid, page, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "getBlogTypeDataListSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.getDataListSuccess(data)
                } else {
                    blogView.getDataListFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "getBlogTypeDataListFail = " + errorMsg)
                blogView.getDataListFail(errorMsg)
            }

        })
    }

    override fun loadMoreBlogTypeDataList(page: Int, cid: Int) {
        blogModel.getBlogTypeDataList(cid, page, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                LogUtil.d("Test", "loadMoreBlogTypeDataListSuccess = " + data)
                if (data.errorCode == 0) {
                    blogView.loadMoreDataListSuccess(data)
                } else {
                    blogView.loadMoreDataListFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "loadMoreBlogTypeDataListFail = " + errorMsg)
                blogView.loadMoreDataListFail(errorMsg)
            }

        })
    }

    override fun cancleRequest() {
        blogModel.cancelRequest()
    }

}