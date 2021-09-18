package com.cqteam.user.data.protocol

/**
 * Author： 洪亮
 * Time： 2021/9/18 - 3:34 PM
 * Email：281332545@qq.com
 * <p>
 * 描述： 用户实体类
 */
data class UserInfo(
        val id: String,
        val userIcon: String,
        val userName: String,
        val userGender: String,
        val userMobile: String,
        val userSign: String
)