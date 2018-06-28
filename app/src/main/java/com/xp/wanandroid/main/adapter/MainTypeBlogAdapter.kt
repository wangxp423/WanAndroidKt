package com.xp.wanandroid.main.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xp.wanandroid.R
import com.xp.wanandroid.blog.activity.BlogTypeActivity
import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.util.Constant

class MainTypeBlogAdapter(val context: Context, datas: MutableList<BlogTypeEntity.Data>) :
        BaseQuickAdapter<BlogTypeEntity.Data, BaseViewHolder>(R.layout.main_recycle_item_type_blog, datas), BaseQuickAdapter.OnItemClickListener {

    override fun convert(helper: BaseViewHolder, item: BlogTypeEntity.Data?) {
        item ?: return
        helper.setText(R.id.tv_item_type_blog_first_type, item.name)
        item.children?.let { children ->
            helper.setText(
                    R.id.tv_item_type_blog_second_type,
                    children.joinToString("     ", transform = { child ->
                        child.name
                    })
            )
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (data.size != 0) {
            Intent(context, BlogTypeActivity::class.java).run {
                putExtra(Constant.BLOG_EXTRA_TITLE, getItem(position)?.name)
                putExtra(Constant.BLOG_EXTRA_CONTENT_CHILDREN_DATA, getItem(position))
                context.startActivity(this)
            }
        }
    }
}