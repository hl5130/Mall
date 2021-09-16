package com.cqteam.user.data.repository

import com.cqteam.baselibrary.data.Result

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:04 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
interface UserRepository {
    suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String>
}