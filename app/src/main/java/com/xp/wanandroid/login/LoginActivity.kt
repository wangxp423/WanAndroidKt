package com.xp.wanandroid.login

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.xp.wanandroid.R
import com.xp.wanandroid.base.BaseImmersionBarActivity
import com.xp.wanandroid.login.entity.UserEntity
import com.xp.wanandroid.login.mvp.LoginContract
import com.xp.wanandroid.login.mvp.LoginPresenter
import com.xp.wanandroid.util.Constant
import com.xp.wanandroid.util.Preference
import com.xp.wanandroid.util.ToastUtil
import kotlinx.android.synthetic.main.login_activity_login.*

/**
 * @类描述：登录页面
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/13 0013 11:07
 * @修改人：
 * @修改时间：2018/6/13 0013 11:07
 * @修改备注：
 */
class LoginActivity : BaseImmersionBarActivity(), View.OnClickListener, LoginContract.LoginView {
    private var isLogin: Boolean by Preference(Constant.KEY_LOGIN, false)
    private var username: String by Preference(Constant.KEY_USERNAME, "")
    private var password: String by Preference(Constant.KEY_PASSWORD, "")
    private val loginPresenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    override fun setLayoutId(): Int = R.layout.login_activity_login

    override fun initImmersionBar() {
        super.initImmersionBar()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            immersionBar.statusBarDarkFont(true).init()
        }
    }

    override fun initView() {
        login_activity_login_registe.setOnClickListener(this)
        login_activity_login_login.setOnClickListener(this)
        login_activity_login_exit.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun cancelRequest() {
        loginPresenter.cancelRegister()
        loginPresenter.cancelLogin()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_activity_login_registe -> {
                if (checkEditContent()) {
                    showLoading()
                    loginPresenter.regisiter(login_activity_login_username.text.toString(), login_activity_login_password.text.toString())
                }
            }
            R.id.login_activity_login_login -> {
                if (checkEditContent()) {
                    showLoading()
                    loginPresenter.login(login_activity_login_username.text.toString(), login_activity_login_password.text.toString())
                }
            }
            R.id.login_activity_login_exit -> {
                finish()
            }
        }
    }

    override fun registerSuccess(result: UserEntity) {
        ToastUtil.showShort(this, getString(R.string.login_toast_register_success))
        loginRegisterSuccessAfter(result)
    }

    override fun registerFail(errorMsg: String?) {
        loginRegisterFailAfter(errorMsg)
    }

    override fun loginSuccess(result: UserEntity) {
        ToastUtil.showShort(this, getString(R.string.login_toast_login_success))
        loginRegisterSuccessAfter(result)
    }

    override fun loginFail(errorMsg: String?) {
        loginRegisterFailAfter(errorMsg)
    }

    override fun showLoading() {
        login_activity_login_progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        login_activity_login_progress.visibility = View.GONE
    }

    fun loginRegisterSuccessAfter(entity: UserEntity) {
        hideLoading()
        isLogin = true
        username = entity.data.username
        password = entity.data.password
        setResult(Activity.RESULT_OK,
                Intent().apply { putExtra(Constant.BLOG_EXTRA_TITLE, entity.data.username) })
        finish()
    }

    fun loginRegisterFailAfter(msg: String?) {
        hideLoading()
        msg?.let { ToastUtil.showShort(this, msg) }
        isLogin = false

    }


    private fun checkEditContent(): Boolean {
        login_activity_login_username.error = null
        login_activity_login_password.error = null
        var cancel = false
        var focusView: View? = null
        val username = login_activity_login_username.text.toString()
        val password = login_activity_login_password.text.toString()
        //check username
        if (TextUtils.isEmpty(username)) {
            login_activity_login_username.error = getString(R.string.login_username_not_empty)
            focusView = login_activity_login_username
            cancel = true
        }
        //check password
        if (TextUtils.isEmpty(password)) {
            login_activity_login_password.error = getString(R.string.login_password_not_empty)
            focusView = login_activity_login_password
            cancel = true
        } else if (password.length < 6) {
            login_activity_login_password.error = getString(R.string.login_password_length_short)
            focusView = login_activity_login_password
            cancel = true
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus()
            }
            return false
        } else {
            return true
        }
    }

}