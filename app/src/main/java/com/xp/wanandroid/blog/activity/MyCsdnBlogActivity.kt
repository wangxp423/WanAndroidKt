package com.xp.wanandroid.blog.activity

import android.content.Intent
import android.view.MenuItem
import android.widget.ImageView
import com.google.gson.Gson
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseToolBarActivity
import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.blog.util.MyBlogUtil
import com.xp.wanandroid.util.Constant
import kotlinx.android.synthetic.main.blog_activity_my_csdn.*

/**
 * @类描述：我的CSND博客
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/29 0029 10:29
 * @修改人：
 * @修改时间：2018/6/29 0029 10:29
 * @修改备注：
 */
class MyCsdnBlogActivity : BaseToolBarActivity() {
    private var myBlogData: BlogTypeEntity? = null
    override fun setLayoutId(): Int = R.layout.blog_activity_my_csdn

    private val images = mutableListOf<ImageView>()
    override fun initView() {
        blog_csdn_toolbar.run {
            title = getString(R.string.main_nav_menu_csdn)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tv_blog_csdn_content.run {
            setOnClickListener {
                myBlogData?.let {
                    Intent(context, BlogTypeActivity::class.java).run {
                        val data = myBlogData?.data!![0]
                        putExtra(Constant.BLOG_EXTRA_TITLE, data.name)
                        putExtra(Constant.BLOG_EXTRA_CONTENT_CHILDREN_DATA, data)
                        putExtra(Constant.BLOG_EXTRA_MY_BLOG, true)
                        context.startActivity(this)
                    }
                }
            }
        }
        tv_blog_csdn_csdn.run {
            setOnClickListener {
                //                Intent(context, BlogActivity::class.java).run {
//                    putExtra(Constant.BLOG_EXTRA_URL, getString(R.string.blog_my_csdn_link))
//                    putExtra(Constant.BLOG_EXTRA_TITLE, getString(R.string.blog_my_csdn))
//                    context.startActivity(this)
//                }
                for (i in 1..10000) {
                    images.add(ImageView(this@MyCsdnBlogActivity))
                }
            }
        }
        tv_blog_csdn_github.run {
            setOnClickListener {
                Intent(context, BlogActivity::class.java).run {
                    putExtra(Constant.BLOG_EXTRA_URL, getString(R.string.blog_my_github_link))
                    putExtra(Constant.BLOG_EXTRA_TITLE, getString(R.string.blog_my_github))
                    context.startActivity(this)
                }
            }
        }
    }

    override fun initData() {
        val testString = MyBlogUtil.getMyBlogData(this)
        testString?.let {
            myBlogData = Gson().fromJson(testString, BlogTypeEntity::class.java)
            myBlogData?.let {
                val stringBuilder = StringBuilder()
                val data = myBlogData?.data!![0] //假数据 只有一条
                stringBuilder.append(data.name).append(": -> {").append("\n")
                data.children?.forEach {
                    stringBuilder.append(it.name).append("\n")
                }
                stringBuilder.append("}")
                tv_blog_csdn_content.text = stringBuilder.toString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}