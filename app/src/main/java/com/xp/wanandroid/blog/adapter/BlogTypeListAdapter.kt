package com.xp.wanandroid.blog.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xp.wanandroid.R
import com.xp.wanandroid.blog.activity.BlogActivity
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.blog.mvp.BlogModel
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.login.LoginActivity
import com.xp.wanandroid.util.Constant
import com.xp.wanandroid.util.Preference
import com.xp.wanandroid.util.ToastUtil


/**
 * @类描述：博客分类列表适配器
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/20 0020 15:19
 * @修改人：
 * @修改时间：2018/6/20 0020 15:19
 * @修改备注：
 */
class BlogTypeListAdapter(val context: Context, val datas: MutableList<Datas>) : BaseQuickAdapter<Datas, BaseViewHolder>(R.layout.main_recycle_item_collect), BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    private val blogModel: BlogModel? by lazy { BlogModel() }
    private val isLogin: Boolean by Preference(Constant.KEY_LOGIN, false)
    override fun convert(helper: BaseViewHolder?, item: Datas?) {
        item ?: return
        helper?.setText(R.id.main_recycle_item_author, item?.author)
        helper?.setText(R.id.main_recycle_item_date, item?.niceDate)
        helper?.setText(R.id.main_recycle_item_title, item?.title)
        helper?.setVisible(R.id.main_recycle_item_type, false)
        helper?.setImageResource(R.id.main_recycle_item_like, if (item.collect) R.drawable.ic_action_like else R.drawable.ic_action_no_like)
        helper?.addOnClickListener(R.id.main_recycle_item_like)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (data.size > 0) {
            val item = data.get(position)
            when (view?.id) {
                R.id.main_recycle_item_like -> {
                    if (isLogin) {
                        val collect = item.collect
                        changeCollect(item.id, !collect, position)
                    } else {
                        Intent(context, LoginActivity::class.java).run { context.startActivity(this) }
                    }
                }
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (data.size > 0) {
            val item = getItem(position)
            Intent(context, BlogActivity::class.java).run {
                putExtra(Constant.BLOG_EXTRA_URL, item?.link)
                putExtra(Constant.BLOG_EXTRA_TITLE, item?.title)
                putExtra(Constant.BLOG_EXTRA_ID, item?.id)
                item?.let {
                    if (it.collect) {
                        putExtra(Constant.BLOG_EXTRA_ISCOLLECTED, true)
                    }
                }
                context.startActivity(this)
            }
        }
    }

    fun changeCollect(id: Int, isAdd: Boolean, position: Int) {
        blogModel?.articleData(id, isAdd, object : RequestBackListener<BlogEntity> {
            override fun onRequestSuccess(data: BlogEntity) {
                if (data?.errorCode == 0) {
                    if (isAdd) {
                        ToastUtil.showShort(context, context.getString(R.string.main_collect_success))
                    } else {
                        ToastUtil.showShort(context, context.getString(R.string.main_collect_cancel_success))
                    }
                    val item = this@BlogTypeListAdapter.data.get(position)
                    item.collect = isAdd
                    setData(position, item)
                } else {
                    onRequestFail(data?.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                errorMsg?.let { ToastUtil.showShort(context, errorMsg) }
            }
        })
    }

    fun onDestory() {
        datas.clear()
        data.clear()
    }
}
 