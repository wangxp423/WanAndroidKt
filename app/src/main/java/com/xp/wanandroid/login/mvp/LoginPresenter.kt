package com.xp.wanandroid.login.mvp

import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.login.entity.UserEntity
import com.xp.wanandroid.util.LogUtil

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/14 0014 16:27
 * @修改人：
 * @修改时间：2018/6/14 0014 16:27
 * @修改备注：
 */
class LoginPresenter(var loginView: LoginContract.LoginView) : LoginContract.ILoginPresenter {


    private val loginModel: LoginContract.ILoginModel = LoginModel()
    override fun regisiter(userName: String, password: String) {
        loginModel.registerClient(userName, password, object : RequestBackListener<UserEntity> {
            override fun onRequestSuccess(data: UserEntity) {
                LogUtil.d("Test", "registerSuccess = " + data)
                if (data.errorCode == 0) {
                    loginView.registerSuccess(data)
                } else {
                    loginView.registerFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "registerFail = " + errorMsg)
                loginView.registerFail(errorMsg)
            }

        })
    }

    override fun login(userName: String, password: String) {
        loginModel.loginClient(userName, password, object : RequestBackListener<UserEntity> {
            override fun onRequestSuccess(data: UserEntity) {
                LogUtil.d("Test", "loginSuccess = " + data)
                if (data.errorCode == 0) {
                    loginView.loginSuccess(data)
                } else {
                    loginView.loginFail(data.errorMsg)
                }
            }

            override fun onRequestFail(errorMsg: String?) {
                LogUtil.d("Test", "loginFail = " + errorMsg)
                loginView.loginFail(errorMsg)
            }

        })
    }

    override fun cancelRegister() {
        loginModel.cancelRegister()
    }

    override fun cancelLogin() {
        loginModel.cancelLogin()
    }

}