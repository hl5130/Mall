package com.cqteam.user.data.source

import com.cqteam.baselibrary.data.Result
import com.cqteam.user.data.protocol.UserInfo

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:24 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
interface UserDataSource {
    suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String>?
    suspend fun login(mobile: String, pwd: String, pushId: String): Result<UserInfo>?
    suspend fun forgetPwd(mobile: String, verifyCode: String): Result<String>?
    suspend fun resetPwd(mobile: String, pwd: String): Result<String>?
    suspend fun editUser(userIcon: String, userName: String, gender: String, sign: String): Result<UserInfo>?
}