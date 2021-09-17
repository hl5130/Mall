package com.cqteam.baselibrary.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.cqteam.baselibrary.R

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 7:33 PM
 * Email：281332545@qq.com
 * <p>
 * 描述： 加载动画
 */
class ProgressLoading private constructor(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    companion object {
        private lateinit var mDialog: ProgressLoading
        private var animDrawable: AnimationDrawable? = null

        fun create(context: Context): ProgressLoading{
            mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            mDialog.setContentView(R.layout.progress_dialog)
            mDialog.setCancelable(true) // 是否可以取消
            mDialog.setCanceledOnTouchOutside(false) // 点击外部不可取消
            mDialog.window?.let {
                val lp = it.attributes
                lp.gravity = Gravity.CENTER
                lp.dimAmount = 0.2f // 灰暗程度
                // 重新赋值给 Window
                it.attributes = lp
            }
            val loadingView = mDialog.findViewById<ImageView>(R.id.iv_loading)
            // 获取 ImageView 的动画背景
            animDrawable = loadingView.background as AnimationDrawable
            return mDialog
        }
    }

    fun showLoading() {
        // 继承自 Dialog，所以直接 super.show()
        super.show()
        animDrawable?.start() // 动画背景开始
    }

    fun hideLoading() {
        super.dismiss()
        animDrawable?.stop() // 动画背景结束
    }

}