package com.cqteam.baselibrary.utils

import android.content.Context

/**
 * Author： 洪亮
 * Time： 2021/9/28 - 5:20 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
object ScreenUtils {
    fun px2dp(context: Context, pxValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxValue / scale + 0.5f
    }

    fun dp2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }
}