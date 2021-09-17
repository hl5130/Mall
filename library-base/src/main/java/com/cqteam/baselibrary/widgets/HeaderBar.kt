package com.cqteam.baselibrary.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.cqteam.baselibrary.R

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 6:58 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class HeaderBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isShowBack = true
    private var titleText: String? = null
    private var rightText: String? = null

    private lateinit var mLeftIv: ImageView
    private lateinit var mTitleTv: TextView
    private lateinit var mRightTv: TextView

    init {
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.HeaderBar)
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack,true)
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)
        initView()
        typedArray.recycle()
    }

    private fun initView() {
        View.inflate(context,R.layout.layout_header_bar,this)
        mLeftIv = findViewById(R.id.mLeftIv)
        mTitleTv = findViewById(R.id.mTitleTv)
        mRightTv = findViewById(R.id.mRightTv)

        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE
        titleText?.let { mTitleTv.text = it }
        rightText?.let {
            mRightTv.visibility = View.VISIBLE
            mRightTv.text = it
        }
    }
}