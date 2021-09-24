package com.cqteam.baselibrary.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.cqteam.baselibrary.R

/**
 * Author： 洪亮
 * Time： 2021/9/24 - 10:36 AM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class BottomNavBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    private val mCartBadgeItem: TextBadgeItem // 购物车消息，文本类型
    private val mMsgBadgeItem: ShapeBadgeItem // 消息，图形类型

    init {
        // 首页
        val homeItem = BottomNavigationItem(R.drawable.btn_nav_home_press,resources.getString(R.string.nav_bar_home)) // 选中状态的图标，标题
            .setInactiveIconResource(R.drawable.btn_nav_home_normal) // 未选中状态的图标
            .setActiveColorResource(R.color.common_blue) // 选中状态的字体颜色
            .setInActiveColorResource(R.color.text_normal) // 未选中状态的字体颜色

        // 分类
        val categoryItem = BottomNavigationItem(R.drawable.btn_nav_category_press,resources.getString(R.string.nav_bar_category)) // 选中状态的图标，标题
            .setInactiveIconResource(R.drawable.btn_nav_category_normal) // 未选中状态的图标
            .setActiveColorResource(R.color.common_blue) // 选中状态的字体颜色
            .setInActiveColorResource(R.color.text_normal) // 未选中状态的字体颜色

        // 购物车
        val cartItem = BottomNavigationItem(R.drawable.btn_nav_cart_press,resources.getString(R.string.nav_bar_cart)) // 选中状态的图标，标题
            .setInactiveIconResource(R.drawable.btn_nav_cart_normal) // 未选中状态的图标
            .setActiveColorResource(R.color.common_blue) // 选中状态的字体颜色
            .setInActiveColorResource(R.color.text_normal) // 未选中状态的字体颜色
        mCartBadgeItem = TextBadgeItem()
        cartItem.setBadgeItem(mCartBadgeItem)

        // 消息
        val msgItem = BottomNavigationItem(R.drawable.btn_nav_msg_press,resources.getString(R.string.nav_bar_msg)) // 选中状态的图标，标题
            .setInactiveIconResource(R.drawable.btn_nav_msg_normal) // 未选中状态的图标
            .setActiveColorResource(R.color.common_blue) // 选中状态的字体颜色
            .setInActiveColorResource(R.color.text_normal) // 未选中状态的字体颜色
        mMsgBadgeItem = ShapeBadgeItem()
        msgItem.setBadgeItem(mMsgBadgeItem)
        mMsgBadgeItem.setShape(ShapeBadgeItem.SHAPE_OVAL)

        // 我的
        val userItem = BottomNavigationItem(R.drawable.btn_nav_user_press,resources.getString(R.string.nav_bar_user)) // 选中状态的图标，标题
            .setInactiveIconResource(R.drawable.btn_nav_user_normal) // 未选中状态的图标
            .setActiveColorResource(R.color.common_blue) // 选中状态的字体颜色
            .setInActiveColorResource(R.color.text_normal) // 未选中状态的字体颜色

        // 设置模式
        setMode(BottomNavigationBar.MODE_FIXED)
        // 设置背景样式
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)

        addItem(homeItem).addItem(categoryItem).addItem(cartItem).addItem(msgItem).addItem(userItem)
            .setFirstSelectedPosition(0)
            .initialise()
    }

    fun checkCartBadge(count: Int) {
        if (count == 0) {
            mCartBadgeItem.hide()
        } else {
            mCartBadgeItem.show()
            mCartBadgeItem.setText("$count")
        }
    }

    fun checkMsgBadge(isVisible: Boolean) {
        if (isVisible) {
            mMsgBadgeItem.show()
        } else {
            mMsgBadgeItem.hide()
        }
    }
}