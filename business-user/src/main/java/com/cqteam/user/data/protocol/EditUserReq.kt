package com.cqteam.user.data.protocol

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 8:12 PM
 * Email：281332545@qq.com
 * <p>
 * 描述： 修改用户资料请求体
 */
data class EditUserReq(val userIcon: String, val userName: String, val gender: String, val sign: String)
