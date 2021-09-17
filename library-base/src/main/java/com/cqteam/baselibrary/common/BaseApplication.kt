package com.cqteam.baselibrary.common

import android.app.Application
import com.cqteam.baselibrary.widgets.ToastUtils

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 8:06 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
    }
}