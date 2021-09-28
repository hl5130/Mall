package com.cqteam.message.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ViewFlipper
import com.cqteam.baselibrary.utils.ScreenUtils
import com.cqteam.message.R

/**
 * Author： 洪亮
 * Time： 2021/9/28 - 5:15 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：新闻公告
 */
class NewsFlipperView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mFlipperView: ViewFlipper

    init {
        val rootView = View.inflate(context, R.layout.layout_news_flipper,null)
        mFlipperView = rootView.findViewById(R.id.mFlipperView)
        mFlipperView.setInAnimation(context,R.anim.news_bottom_in)
        mFlipperView.setOutAnimation(context,R.anim.news_bottom_out)
        addView(rootView)
    }

    private fun buildView(msg: String): TextView {
        val textView = TextView(context)
        textView.text = msg
        textView.textSize = ScreenUtils.px2dp(context,context.resources.getDimension(R.dimen.text_small_size))
        textView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        return textView
    }

    fun setData(data: Array<String>) {
        for (text in data) {
            mFlipperView.addView(buildView(text))
        }
        mFlipperView.startFlipping()
    }
}