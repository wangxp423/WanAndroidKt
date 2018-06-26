package com.xp.wanandroid.main.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xp.wanandroid.R
import com.xp.wanandroid.blog.activity.BlogActivity
import com.xp.wanandroid.main.entity.BannerEntity
import com.xp.wanandroid.util.Constant

class HomeBannerAdapter(val context: Context, val datas: MutableList<BannerEntity.Data>) :
        BaseQuickAdapter<BannerEntity.Data, BaseViewHolder>(R.layout.main_recycle_item_home_banner, datas), BaseQuickAdapter.OnItemClickListener {
    override fun convert(helper: BaseViewHolder, item: BannerEntity.Data?) {
        item ?: return
        helper.setText(R.id.main_home_banner_title, item.title.trim())
        val imageView = helper.getView<ImageView>(R.id.main_home_banner_image)
        Glide.with(context).load(item.imagePath).placeholder(R.drawable.logo).into(imageView)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (datas.size != 0) {
            Intent(context, BlogActivity::class.java).run {
                putExtra(Constant.BLOG_EXTRA_URL, datas[position].url)
                putExtra(Constant.BLOG_EXTRA_TITLE, datas[position].title)
                context.startActivity(this)
            }
        }
    }
}