package com.cqteam.baselibrary.ext

import android.widget.Button
import android.widget.EditText
import com.cqteam.baselibrary.widgets.DefaultTextWatcher

/**
 * Author： 洪亮
 * Time： 2021/9/18 - 2:59 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */


/**
 *  Button扩展方法，监听 EditText 的变化来判断Button是否可用
 *  @param et 需要监听的 EditText
 *  @param method 传入的是一个方法，此方法执行判断条件并返回 Boolean 类型的值
 */
fun Button.enable(et: EditText, method: () -> Boolean){
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}
