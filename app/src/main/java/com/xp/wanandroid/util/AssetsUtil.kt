package com.xp.wanandroid.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @类描述：获取Assets文件
 * @创建人：Wangxiaopan
 * @创建时间：2018/6/29 0029 10:07
 * @修改人：
 * @修改时间：2018/6/29 0029 10:07
 * @修改备注：
 */
object AssetsUtil {
    fun getFormAssets(context: Context, fileName: String): String? {
        val stringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(context.assets.open(fileName), "UTF-8")).run {
            var line: String? = ""
            do {
                line = readLine()
                if (line != null) {
                    stringBuilder.append(line)
                } else {
                    break
                    close()
                }
            } while (true)
            return stringBuilder.toString()
        }
        return null
    }
}