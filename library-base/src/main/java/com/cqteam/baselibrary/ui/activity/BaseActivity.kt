package com.cqteam.baselibrary.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cqteam.baselibrary.common.AppManager

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 6:46 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }
}