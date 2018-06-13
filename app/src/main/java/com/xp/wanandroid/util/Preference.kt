package com.xp.wanandroid.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.xp.wanandroid.base.BaseApplication
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @类描述：SharePreference工具类
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/12 0012 10:42
 * @修改人：
 * @修改时间：2018/6/12 0012 10:42
 * @修改备注：
 */
class Preference<T>(private val key: String, private val default: T) : ReadWriteProperty<Any?, T> {
    companion object {
        val preference: SharedPreferences by lazy { BaseApplication.INSTANCE.applicationContext.getSharedPreferences(Constant.SHARED_NAME, Context.MODE_PRIVATE) }
        fun clear() {
            preference.edit().clear().apply()
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharePreference(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getSharePreference(key: String, default: T): T = with(preference) {
        val value: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException("This type of data can not be saved! ")
        }
        value as T
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putSharePreference(key: String, default: T) = with(preference.edit()) {
        when (default) {
            is Long -> putLong(key, default)
            is String -> putString(key, default)
            is Int -> putInt(key, default)
            is Boolean -> putBoolean(key, default)
            is Float -> putFloat(key, default)
            else -> throw IllegalArgumentException("This type of data can not be saved! ")
        }.apply()
    }
}

//class Delegate {
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        return "$thisRef, thank you for delegating '${property.name}' to me!"
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//        println("$value has been assigned to '${property.name} in $thisRef.'")
//    }
//}


 