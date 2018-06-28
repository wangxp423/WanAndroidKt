package com.xp.wanandroid.blog.activity

import android.content.Intent
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseImmersionBarActivity
import com.xp.wanandroid.blog.adapter.BlogTypePagerAdapter
import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.util.Constant
import kotlinx.android.synthetic.main.blog_activity_blog_type.*

/**
 * @类描述：博客分类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/22 0022 11:27
 * @修改人：
 * @修改时间：2018/6/22 0022 11:27
 * @修改备注：
 */
class BlogTypeActivity : BaseImmersionBarActivity() {
    private lateinit var firstTitle: String
    private val list = mutableListOf<BlogTypeEntity.Data.Children>()
    private var target: Boolean = false
    private val blogTypePagerAdapter: BlogTypePagerAdapter by lazy { BlogTypePagerAdapter(list, supportFragmentManager) }


    override fun setLayoutId(): Int = R.layout.blog_activity_blog_type

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.blog_activity_blog_type_toolbar).init()
    }

    override fun initView() {
        blog_activity_blog_type_toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        intent.extras?.let { extras ->
            target = extras.getBoolean(Constant.BLOG_EXTRA_TARGET, false)
            extras.getString(Constant.BLOG_EXTRA_TITLE)?.let {
                firstTitle = it
                blog_activity_blog_type_toolbar.title = it
            }
            if (target) {
                list.add(BlogTypeEntity.Data.Children(extras.getInt(Constant.BLOG_EXTRA_CID, 0), firstTitle,
                        0, 0, 0, 0, null))
            } else {
                extras.getSerializable(Constant.BLOG_EXTRA_CONTENT_CHILDREN_DATA)?.let {
                    val data = it as BlogTypeEntity.Data
                    data.children?.let {
                        list.addAll(it)
                    }
                }
            }
        }
        blog_activity_blog_type_tbl.run {
            setupWithViewPager(blog_activity_blog_type_vp)
        }
        blog_activity_blog_type_tbl.addOnTabSelectedListener(
                TabLayout.ViewPagerOnTabSelectedListener(blog_activity_blog_type_vp)
        )
        blog_activity_blog_type_vp.run {
            adapter = blogTypePagerAdapter
        }
        blog_activity_blog_type_vp.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(blog_activity_blog_type_tbl)
        )

    }

    override fun initData() {
    }

    override fun cancelRequest() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.blog_content_type_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.blog_content_type_item_search -> {
                Intent(this, BlogSearchActivity::class.java).run { startActivity(this) }
            }
            R.id.blog_content_type_item_share -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.blog_share_type_url, getString(R.string.app_name), list[blog_activity_blog_type_tbl.selectedTabPosition].name, list[blog_activity_blog_type_tbl.selectedTabPosition].id))
                    type = Constant.BLOG_SHARE_CONTENT_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.blog_share_title)))
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
 