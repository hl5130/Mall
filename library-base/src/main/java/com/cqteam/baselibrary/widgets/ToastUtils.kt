package com.cqteam.baselibrary.widgets

import android.app.Application
import android.content.Context
import android.widget.Toast

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 5:51 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
object ToastUtils {

    private lateinit var application: Application

    fun init(context: Application) {
        this.application = context
    }

    fun show(message: String) {
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
    }
}