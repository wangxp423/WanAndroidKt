package com.xp.wanandroid.login.mvp

import RetrofitHelper
import cancelByActive
import com.xp.wanandroid.listener.RequestBackListener
import com.xp.wanandroid.login.entity.UserEntity
import com.xp.wanandroid.util.Constant
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import tryCatch

/**
 * @类描述：应用常量类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/14 0014 16:27
 * @修改人：
 * @修改时间：2018/6/14 0014 16:27
 * @修改备注：
 */
class LoginModel : LoginContract.ILoginModel {
    private var loginAsync: Deferred<UserEntity>? = null
    private var registerAsync: Deferred<UserEntity>? = null
    override fun registerClient(userName: String, password: String, listener: RequestBackListener<UserEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.message)
            }) {
                registerAsync?.cancelByActive()
                registerAsync = RetrofitHelper.retrofitService.registerWanAndroid(userName, password, password)
                val result = registerAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun loginClient(userName: String, password: String, listener: RequestBackListener<UserEntity>) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                listener.onRequestFail(it.message)
            }) {
                registerAsync?.cancelByActive()
                registerAsync = RetrofitHelper.retrofitService.loginWanAndroid(userName, password)
                val result = registerAsync?.await()
                result ?: let {
                    listener.onRequestFail(Constant.REQUEST_NULL)
                    return@async
                }
                listener.onRequestSuccess(result)
            }
        }
    }

    override fun cancelRegister() {
        registerAsync?.cancelByActive()
    }

    override fun cancelLogin() {
        loginAsync?.cancelByActive()
    }


}