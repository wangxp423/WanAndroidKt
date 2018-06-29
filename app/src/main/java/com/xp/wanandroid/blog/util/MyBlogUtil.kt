package com.xp.wanandroid.blog.util

import android.content.Context
import com.google.gson.Gson
import com.xp.wanandroid.base.BaseApplication
import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.util.AssetsUtil

/**
 * @类描述：我的博客 数据相关操作 类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/29 0029 16:22
 * @修改人：
 * @修改时间：2018/6/29 0029 16:22
 * @修改备注：
 */
object MyBlogUtil {
    //获取我的博客分类
    fun getMyBlogData(context: Context): String? {
        return AssetsUtil.getFormAssets(context, "my_csdn_blog.json")
    }

    //根据分类ID获取下面的数据
    fun getMyBlogType(id: Int): BlogEntity? {
        val context = BaseApplication.INSTANCE
        var entity: BlogEntity? = null
        var jsonString: String? = null
        when (id) {
            1 -> {
                jsonString = AssetsUtil.getFormAssets(context, "my_blog_1_designmodel.json")
            }
            2 -> {
                jsonString = AssetsUtil.getFormAssets(context, "my_blog_2_http.json")
            }
            3 -> {
                jsonString = AssetsUtil.getFormAssets(context, "my_blog_3_kotlin.json")
            }
            4 -> {
                jsonString = AssetsUtil.getFormAssets(context, "my_blog_4_algorithms.json")
            }
            5 -> {
                jsonString = AssetsUtil.getFormAssets(context, "my_blog_5_android.json")
            }
            6 -> {
                jsonString = AssetsUtil.getFormAssets(context, "my_blog_6_gradle.json")
            }
            7 -> {
                jsonString = AssetsUtil.getFormAssets(context, "my_blog_7_github.json")
            }
        }
        jsonString?.let {
            entity = Gson().fromJson(jsonString, BlogEntity::class.java)
        }
        return entity ?: null
    }
}