package com.xp.wanandroid.main

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.AppCompatButton
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseImmersionBarActivity
import com.xp.wanandroid.blog.activity.BlogSearchActivity
import com.xp.wanandroid.blog.activity.MyLikeActivity
import com.xp.wanandroid.login.LoginActivity
import com.xp.wanandroid.main.fragment.HomeFragment
import com.xp.wanandroid.main.fragment.LabelFragment
import com.xp.wanandroid.main.fragment.TypeFragment
import com.xp.wanandroid.util.Constant
import com.xp.wanandroid.util.Preference
import com.xp.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.main_activity_main.*

/**
 * @类描述：首页
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/11 0011 16:50
 * @修改人：
 * @修改时间：2018/6/11 0011 16:50
 * @修改备注：
 */
class MainActivity : BaseImmersionBarActivity() {
    val MAIN_LOGIN_REQUEST_CODE = 100
    val MAIN_LIKE_REQUEST_CODE = 101
    private var lastTime: Long = 0
    private var currentIndex: Int = 0
    private var homeFragment: HomeFragment? = null
    private var typeFragment: TypeFragment? = null
    private var labelFragment: LabelFragment? = null
    private val fragmentManager by lazy { supportFragmentManager }
    private val isLogin: Boolean by Preference(Constant.KEY_LOGIN, false)
    private val username: String by Preference(Constant.KEY_USERNAME, "")

    private lateinit var tvUserName: TextView
    private lateinit var tvLogout: AppCompatButton

    override fun setLayoutId(): Int = R.layout.main_activity_main

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.main_activity_main_toolbar).init()
    }

    override fun initView() {
        main_activity_main_toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        main_activity_main_bottomnav.run {
            setOnNavigationItemSelectedListener {
                bottomNavItemSelected(it.itemId)
            }
            selectedItemId = R.id.main_bottom_nav_item_home
        }
        main_activity_main_dl.run {
            val toggle = ActionBarDrawerToggle(this@MainActivity, this, main_activity_main_toolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
        main_activity_main_nv.run {
            setNavigationItemSelectedListener {
                navItemSelected(it.itemId)
            }
        }
        tvUserName = main_activity_main_nv.getHeaderView(0).findViewById(R.id.main_nav_header_user)
        tvLogout = main_activity_main_nv.getHeaderView(0).findViewById(R.id.main_nav_header_logout)
        tvUserName.run {
            text = if (!isLogin) {
                getString(R.string.main_nav_header_unlogin)
            } else {
                username
            }
        }
        tvLogout.run {
            text = if (!isLogin) {
                getString(R.string.main_nav_header_please_login)
            } else {
                getString(R.string.main_nav_header_logout)
            }
            setOnClickListener {
                if (!isLogin) {
                    Intent(this@MainActivity, LoginActivity::class.java).run {
                        startActivityForResult(this, MAIN_LOGIN_REQUEST_CODE)
                    }
                } else {
                    Preference.clear()
                    tvUserName.text = getString(R.string.main_nav_header_unlogin)
                    text = getString(R.string.main_nav_header_please_login)
                }
            }
        }
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        if (isLogin && tvUserName.text.toString() != username) {
            tvUserName.text = username
            tvLogout.text = getString(R.string.main_nav_header_logout)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.main_toolbar_menu_item_hot -> {
                if (currentIndex == R.id.main_toolbar_menu_item_hot) {
                    labelFragment?.refreshData()
                }
                setFragment(R.id.main_toolbar_menu_item_hot)
                currentIndex = R.id.main_toolbar_menu_item_hot
                return true
            }
            R.id.main_toolbar_menu_item_search -> {
                Intent(this, BlogSearchActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is HomeFragment -> homeFragment ?: let { homeFragment = fragment }
            is TypeFragment -> typeFragment ?: let { typeFragment = fragment }
            is LabelFragment -> labelFragment ?: let { labelFragment = fragment }
        }
    }

    override fun onBackPressed() {
        if (main_activity_main_dl.isDrawerOpen(GravityCompat.START)) {
            main_activity_main_dl.closeDrawer(GravityCompat.START)
            return
        }
        if (currentIndex == R.id.main_toolbar_menu_item_hot) {
            bottomNavItemSelected(main_activity_main_bottomnav.selectedItemId)
            return
        }
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime < 2000) {
            super.onBackPressed()
            finish()
        } else {
            ToastUtil.showLong(this, R.string.main_double_click_exit)
            lastTime = System.currentTimeMillis()
        }
    }

    override fun cancelRequest() {
    }

    private fun bottomNavItemSelected(id: Int): Boolean {
        setFragment(id)
        when (id) {
            R.id.main_bottom_nav_item_home -> {
                if (currentIndex == R.id.main_bottom_nav_item_home) {
                    homeFragment?.smoothScrollToPosition()
                }
                currentIndex = R.id.main_bottom_nav_item_home
                return true
            }

            R.id.main_bottom_nav_item_type -> {
                if (currentIndex == R.id.main_bottom_nav_item_type) {
                    typeFragment?.smoothScrollToPosition()
                }
                currentIndex = R.id.main_bottom_nav_item_type
                return true
            }

            else -> {
                return false
            }
        }
    }

    private fun setFragment(index: Int) {
        if (main_activity_main_dl.isDrawerOpen(GravityCompat.START)) {
            main_activity_main_dl.closeDrawer(GravityCompat.START)
        }
        fragmentManager.beginTransaction().apply {
            homeFragment ?: let {
                HomeFragment().let {
                    homeFragment = it
                    add(R.id.main_activity_main_content, it)
                }
            }
            typeFragment ?: let {
                TypeFragment().let {
                    typeFragment = it
                    add(R.id.main_activity_main_content, it)
                }
            }
            labelFragment ?: let {
                LabelFragment().let {
                    labelFragment = it
                    add(R.id.main_activity_main_content, it)
                }
            }
            hideFragment(this)
            when (index) {
                R.id.main_bottom_nav_item_home -> {
                    main_activity_main_toolbar.title = getString(R.string.app_name)
                    homeFragment?.let {
                        this.show(it)
                    }
                }
                R.id.main_bottom_nav_item_type -> {
                    main_activity_main_toolbar.title = getString(R.string.main_bottom_nav_menu_type)
                    typeFragment?.let {
                        this.show(it)
                    }
                }
                R.id.main_toolbar_menu_item_hot -> {
                    main_activity_main_toolbar.title = getString(R.string.main_toolbar_menu_hot)
                    labelFragment?.let {
                        this.show(it)
                    }
                }
            }
        }.commit()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        typeFragment?.let { transaction.hide(it) }
        labelFragment?.let { transaction.hide(it) }
    }


    private fun navItemSelected(id: Int): Boolean {
        when (id) {
            R.id.main_nav_item_like -> {
                if (!isLogin) {
                    Intent(this, LoginActivity::class.java).run {
                        startActivityForResult(this, MAIN_LOGIN_REQUEST_CODE)
                    }
                    ToastUtil.showShort(this, R.string.login_tip_login_first)
                    return true
                }
                Intent(this, MyLikeActivity::class.java).run {
                    startActivityForResult(this, MAIN_LIKE_REQUEST_CODE)
                }
            }
            R.id.main_nav_item_about -> {
                Intent(this, AboutUsActivity::class.java).run {
                    startActivity(this)
                }
            }

        }
        main_activity_main_dl.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            MAIN_LOGIN_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    tvUserName.text = username
                    tvLogout.text = getString(R.string.main_nav_header_logout)
                }
            }
            MAIN_LIKE_REQUEST_CODE -> {

            }
        }
    }

}
