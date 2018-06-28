package com.xp.wanandroid.main.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.xp.wanandroid.R
import com.xp.wanandroid.blog.activity.BlogActivity
import com.xp.wanandroid.blog.activity.BlogSearchActivity
import com.xp.wanandroid.main.entity.HotLableEntity
import com.xp.wanandroid.util.Constant
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import getRandomColor

//标签适配器
class HotLableAdapter(val context: Context, val datas: List<HotLableEntity.Data>) :
        TagAdapter<HotLableEntity.Data>(datas), TagFlowLayout.OnTagClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var isSearch: Boolean = false

    override fun getView(parent: FlowLayout, position: Int, data: HotLableEntity.Data): View {
        return (inflater.inflate(R.layout.main_tag_item_hot_lable, parent, false) as TextView).apply {
            text = data.name
            val parseColor = try {
                Color.parseColor(getRandomColor())
            } catch (_: Exception) {
                @Suppress("DEPRECATION")
                context.resources.getColor(R.color.colorAccent)
            }
            setTextColor(parseColor)
        }
    }

    override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
        if (datas.size > 0) {
            val item = datas.get(position)
            if (isSearch) {
                Intent(context, BlogSearchActivity::class.java).run {
                    putExtra(Constant.BLOG_EXTRA_SEARCH_KEY, item.name)
                    context.startActivity(this)
                }
            } else {
                Intent(context, BlogActivity::class.java).run {
                    putExtra(Constant.BLOG_EXTRA_URL, item.link)
                    putExtra(Constant.BLOG_EXTRA_ID, item.id)
                    putExtra(Constant.BLOG_EXTRA_TITLE, item.name)
                    context.startActivity(this)
                }
            }
        }
        return true
    }
}