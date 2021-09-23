package com.cqteam.user

import android.app.Application
import com.cqteam.baselibrary.utils.AppPrefsUtils
import com.cqteam.baselibrary.widgets.ToastUtils
import dagger.hilt.android.HiltAndroidApp

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 5:25 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
@HiltAndroidApp
class UserCenterApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
        AppPrefsUtils.create(this)
    }

}