package com.cqteam.user.data.source

import com.cqteam.baselibrary.data.Result
import com.cqteam.user.data.protocol.UserInfo

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 5:26 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class UserLocalDataSource: UserDataSource {
    override suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String>? {
        return null
    }

    override suspend fun login(mobile: String, pwd: String, pushId: String): Result<UserInfo>? {
        return null
    }

    override suspend fun forgetPwd(mobile: String, verifyCode: String): Result<String>? {
        return null
    }

    override suspend fun resetPwd(mobile: String, pwd: String): Result<String>? {
        return null
    }

    override suspend fun editUser(
        userIcon: String,
        userName: String,
        gender: String,
        sign: String
    ): Result<UserInfo>? {
        return null
    }
}