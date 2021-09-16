package com.cqteam.user.data.protocol

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:01 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
data class RegisterReq(
    val mobile: String,
    val pwd: String,
    val verifyCode: String
)