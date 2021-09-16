package com.cqteam.baselibrary.data.protocol

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 10:56 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
class BaseResp<out T>(
    val status: Int,
    val message: String,
    val data: T
)