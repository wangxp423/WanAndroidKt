package com.xp.wanandroid.login

import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.login.entity.UserEntity
import com.xp.wanandroid.mvpbase.IBaseModel
import com.xp.wanandroid.mvpbase.IBasePresenter
import com.xp.wanandroid.mvpbase.IBaseView

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/14 0014 16:03
 * @修改人：
 * @修改时间：2018/6/14 0014 16:03
 * @修改备注：
 */
interface LoginContract {
    interface LoginView : IBaseView {
        fun registerSuccess(result: UserEntity)
        fun registerFail(errorMsg: String?)
        fun loginSuccess(result: UserEntity)
        fun loginFail(errorMsg: String?)
    }

    interface ILoginModel : IBaseModel {
        fun registerClient(userName: String, password: String, listener: RequestBackListener<UserEntity>)
        fun loginClient(userName: String, password: String, listener: RequestBackListener<UserEntity>)
        fun cancelRegister()
        fun cancelLogin()
    }

    interface ILoginPresenter : IBasePresenter {
        fun regisiter(userName: String, password: String)
        fun login(userName: String, password: String)
        fun cancelRegister()
        fun cancelLogin()
    }

    interface LoginResponseListener {
        fun loginSuccess(entity: UserEntity)
        fun loginFail(msg: String?)
    }
}