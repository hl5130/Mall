package com.cqteam.user.data.repository

import com.cqteam.baselibrary.data.Result
import com.cqteam.user.data.protocol.UserInfo

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:04 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
interface UserRepository {
    suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String>
    suspend fun login(mobile: String, pwd: String, pushId: String): Result<UserInfo>
    suspend fun forgetPwd(mobile: String, verifyCode: String): Result<String>
    suspend fun resetPwd(mobile: String, pwd: String): Result<String>
}