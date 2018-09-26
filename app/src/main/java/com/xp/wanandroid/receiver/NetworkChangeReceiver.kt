package com.xp.wanandroid.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.xp.wanandroid.event.NetworkChangeEvent
import com.xp.wanandroid.util.Constant
import com.xp.wanandroid.util.NetWorkUtil
import com.xp.wanandroid.util.Preference
import org.greenrobot.eventbus.EventBus

/**
 * @类描述：NetworkChangeReceiver
 * @创建人：Wangxiaopan
 * @创建时间：2018/9/25 0011 17:05
 * @修改人：
 * @修改时间：2018/9/25 0011 17:05
 * @修改备注：
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上一次的网络状态
     */
    private var hasNetwork: Boolean by Preference(Constant.KEY_NETWORK_IS_CONNECTION, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}