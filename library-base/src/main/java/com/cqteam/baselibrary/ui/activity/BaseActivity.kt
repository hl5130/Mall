package com.cqteam.baselibrary.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cqteam.baselibrary.common.AppManager
import com.cqteam.baselibrary.utils.NetWorkUtils
import com.cqteam.baselibrary.vm.BaseViewModel
import com.cqteam.baselibrary.widgets.ProgressLoading
import com.cqteam.baselibrary.widgets.ToastUtils

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 6:46 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
abstract class BaseActivity<out VM: BaseViewModel>: AppCompatActivity() {

    protected abstract val viewModel: VM

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        mLoadingDialog = ProgressLoading.create(this)


        // 开启加载动画
        viewModel.showLoading.observe(this, {
            if (NetWorkUtils.isNetWorkAvailable(this)){
                // 网络可用状态时，才弹出加载框
                mLoadingDialog.showLoading()
            }
        })

        // 隐藏加载动画
        viewModel.hideLoading.observe(this, {
            mLoadingDialog.hideLoading()
        })

        // 弹出错误信息
        viewModel.error.observe(this, {
            ToastUtils.show(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }
}