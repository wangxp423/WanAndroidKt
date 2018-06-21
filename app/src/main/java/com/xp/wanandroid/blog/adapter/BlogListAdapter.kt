package com.xp.wanandroid.blog.adapter

import android.content.Context
import android.text.Html
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xp.wanandroid.R
import com.xp.wanandroid.blog.entity.Datas
import com.xp.wanandroid.util.ToastUtil


/**
 * @类描述：添加收藏/关注适配器
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/20 0020 15:19
 * @修改人：
 * @修改时间：2018/6/20 0020 15:19
 * @修改备注：
 */
class BlogListAdapter(val context: Context, val datas: MutableList<Datas>) : BaseQuickAdapter<Datas, BaseViewHolder>(R.layout.main_recycle_item_collect) {
    override fun convert(helper: BaseViewHolder?, item: Datas?) {
        item ?: return
        helper?.setText(R.id.main_recycle_item_author, item?.author)
        helper?.setText(R.id.main_recycle_item_date, if (item.originId != 0) context.getString(R.string.main_collect_time, item.niceDate) else item?.niceDate)
        helper?.setText(R.id.main_recycle_item_title, Html.fromHtml(item?.title))
        helper?.setText(R.id.main_recycle_item_type, item?.chapterName)
        helper?.setTextColor(R.id.main_recycle_item_type, context.resources.getColor(R.color.colorPrimary))
        helper?.setImageResource(R.id.main_recycle_item_like, if (item.originId != 0 || item.collect) R.drawable.ic_action_like else R.drawable.ic_action_no_like)
        helper?.setOnClickListener(R.id.main_recycle_item_type, onClickListener)
        helper?.setOnClickListener(R.id.main_recycle_item_like, onClickListener)
        helper?.setOnClickListener(R.id.main_recycle_item_rl, onClickListener)
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.main_recycle_item_rl -> {
                ToastUtil.showShort(context, "onItemClick")
            }

            R.id.main_recycle_item_type -> {
                ToastUtil.showShort(context, "onItemClick-->Type")
            }

            R.id.main_recycle_item_like -> {
                ToastUtil.showShort(context, "onItemClick-->Like")
            }
        }
    }

}
 