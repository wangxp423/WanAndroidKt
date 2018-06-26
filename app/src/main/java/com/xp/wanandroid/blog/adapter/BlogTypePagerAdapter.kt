package com.xp.wanandroid.blog.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.blog.fragment.BlogContentTypeFragment

/**
 * @类描述：文章分类pageradapter
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/25 0025 17:38
 * @修改人：
 * @修改时间：2018/6/25 0025 17:38
 * @修改备注：
 */
class BlogTypePagerAdapter(val list: List<BlogTypeEntity.Data.Children>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val listFragment = mutableListOf<Fragment>()

    init {
        list.forEach { listFragment.add(BlogContentTypeFragment.newInstance(it.id)) }
    }

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence = list[position].name

    override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE

}